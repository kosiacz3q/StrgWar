package StrgWar.ai;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.map.GameUnit;
import StrgWar.map.ISentUnitsManager;
import StrgWar.map.changeable.ChangeableMap;
import StrgWar.map.changeable.ChangeableNode;
import StrgWar.map.changeable.IChangeableMapProvider;

public class GameLogicExecutor implements ICommandExecutor, ISentUnitsManager, Runnable
{
	public GameLogicExecutor(IChangeableMapProvider changeableMapProvider)
	{
		_commandExecuteLock = new ReentrantLock();
		
		_map = changeableMapProvider.GetChangeableMap();
		
		_map.nodes.forEach(node -> node.SetUnitsReceiver(this));
		
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
			if (actorCommand.GetOrigin().compareTo(abstractActor.GetName()) == 0)
			{
				switch (actorCommand.GetCommandType())
				{
					case START_SENDING :
						
						ChangeableNode origin = _map.Find(actorCommand.GetOrigin());
						ChangeableNode destination = _map.Find(actorCommand.GetDestination());
						
						if (origin != null && destination != null)
						{
							origin.StartSendingUnitsTo(destination);
							_logger.log(Level.FINE, "Player (" + abstractActor.GetName() + " starts sending units from " +  actorCommand.GetOrigin() + " to " + destination.GetMapElementName());
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
								nd.StopSendingUnits();
								_logger.log(Level.FINE, "Player (" + abstractActor.GetName() + " stops sending units from " +  actorCommand.GetOrigin());
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
			else
			{
				_logger.log( Level.INFO, "Command to non owned city");
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
		
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			_pendingUnits.forEach(g -> g.Update(100));

			for (ChangeableNode node : _map.nodes)
			{
				node.Update(100);
			}
			
			try
			{
				Thread.sleep(100);
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
	
	private static final Logger _logger = Logger.getLogger( GameLogicExecutor.class.getName() );
}
