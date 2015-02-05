package StrgWar.ai;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.gui.DrawingManager;
import StrgWar.map.GameUnit;
import StrgWar.map.ISentUnitsManager;
import StrgWar.map.changeable.ChangeableMap;
import StrgWar.map.changeable.ChangeableNode;
import StrgWar.map.changeable.IChangeableMapProvider;

public class GameLogicExecutor implements ICommandExecutor, ISentUnitsManager, Runnable
{
	public GameLogicExecutor(IChangeableMapProvider changeableMapProvider, DrawingManager drawingManager)
	{
		_drawingManager = drawingManager;
		
		_commandExecuteLock = new ReentrantLock();
		
		_map = changeableMapProvider.GetChangeableMap();
		
		_map.Nodes.forEach(node -> node.SetUnitsReceiver(this));
		
		_isGameStarted = false;
		
		_pendingUnits = new ArrayList<GameUnit>();
	}
	
	public void StartGame()
	{
		_isGameStarted = true;
	}
	
	public void StopGame()
	{
		_isGameStarted = false;
	}
	
	@Override
	public void ExecuteCommand(AbstractActor abstractActor, ActorCommand actorCommand)
	{
		if (!_isGameStarted)
			return;
		
		_commandExecuteLock.lock();
		
		try
		{
			switch (actorCommand.GetCommandType())
			{
				case START_SENDING :
					
					ChangeableNode origin = _map.Find(actorCommand.GetOrigin());
					ChangeableNode destination = _map.Find(actorCommand.GetDestination());
					
					if (origin != null && destination != null)
					{
						
						if (origin.GetOccupantName().compareTo(abstractActor.GetName()) == 0)
						{
							origin.StartSendingUnitsTo(destination);
							_logger.log(Level.FINE, "Player (" + abstractActor.GetName() + " starts sending units from " +  actorCommand.GetOrigin() + " to " + destination.GetMapElementName());
						}
						else
						{
							_logger.log( Level.INFO, "Command to non owned city");
						}
					}
					else
					{
						_logger.log(Level.FINE, "Wrong city name (" + actorCommand.GetOrigin() + " or " + actorCommand.GetDestination() + ") from player " + abstractActor.GetName());
					}
					
					break;
					
				case STOP_SENDING :
						
						ChangeableNode nd = _map.Find(actorCommand.GetOrigin());
						
						if (nd != null)
						{
							if (nd.GetOccupantName().compareTo(abstractActor.GetName()) == 0)
							{		
								nd.StopSendingUnits();
								_logger.log(Level.FINE, "Player (" + abstractActor.GetName() + " stops sending units from " +  actorCommand.GetOrigin());
							}
							else
							{
								_logger.log( Level.INFO, "Command to non owned city");
							}
						}
						else
						{
							_logger.log(Level.FINE, "Wrong city name (" + actorCommand.GetOrigin() + ") from player " + abstractActor.GetName());
						}
	
						break;
						
				default :
						_logger.log( Level.WARNING, "Unexpected command type");
						break;
			}
		}
		finally
		{
			_commandExecuteLock.unlock();
		}
	}
	
	@Override
	public void ReceiveUnits(GameUnit gu)
	{
		_pendingUnits.add(gu);
		_drawingManager.Register(gu);
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			//_pendingUnits.forEach(g -> g.Update(_sleepTime));

			for (ChangeableNode node : _map.Nodes)
			{
				node.Update(_sleepTime);
			}
		
			//removing complete units
			ArrayList<GameUnit> toRemove = new ArrayList<GameUnit>();
			
			for( GameUnit unit : _pendingUnits)
				if( unit.IsTravelComplete() ) { 
					toRemove.add(unit);
					_drawingManager.RemoveElement(unit);
				}
			
			for( GameUnit unit : toRemove)
				_pendingUnits.remove(unit);
			
			try
			{
				Thread.sleep(_sleepTime);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
		
	}
	
	private final ReentrantLock _commandExecuteLock;
	private final ChangeableMap _map;
	private boolean _isGameStarted;
	private ArrayList<GameUnit> _pendingUnits;
	private DrawingManager _drawingManager;
	private int _sleepTime = 1000;
	
	private static final Logger _logger = Logger.getLogger( GameLogicExecutor.class.getName() );
}
