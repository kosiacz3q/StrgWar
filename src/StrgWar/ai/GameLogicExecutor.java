package StrgWar.ai;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import StrgWar.bestresults.BestResults;
import StrgWar.core.ISharedDataHandler;
import StrgWar.gui.DrawingManager;
import StrgWar.map.GameUnit;
import StrgWar.map.ISentUnitsManager;
import StrgWar.map.changeable.ChangeableMap;
import StrgWar.map.changeable.ChangeableNode;
import StrgWar.map.changeable.IChangeableMapProvider;
import StrgWar.map.readonly.ReadonlyNode;

public class GameLogicExecutor implements ICommandExecutor, ISentUnitsManager, Runnable
{
	public GameLogicExecutor(IChangeableMapProvider changeableMapProvider, DrawingManager drawingManager, ISharedDataHandler sharedDataHandler, BestResults bestResults)
	{
		_drawingManager = drawingManager;
		
		_commandExecuteLock = new ReentrantLock();
		
		_map = changeableMapProvider.GetChangeableMap();
		
		_map.Nodes.forEach(node -> node.SetUnitsReceiver(this));
		
		_isGameStarted = false;
		
		_pendingUnits = new ArrayList<GameUnit>();
		
		_sharedDataHandler = sharedDataHandler;
		
		_bestResults = bestResults;
	}
	
	public void StartGame()
	{
		_isGameStarted = true;
	}
	
	public void StopGame()
	{
		_isGameStarted = false;
	}
	
	private boolean IsGameEnded()
	{
		ReadonlyNode n = _map.Nodes.get(0);
		
		for (ReadonlyNode node : _map.Nodes)
		{
			if(n.GetOccupantName().compareTo(node.GetOccupantName()) != 0)
				return false;
		}
		
		return true;
	}
	
	private String GetWinnersName()
	{
		if(_map.Nodes.get(0).GetOccupantName().compareTo("player1") == 0)
			return _sharedDataHandler.GetPlayer1Name();
		else
			return _sharedDataHandler.GetPlayer2Name();
	}
	
	private String GetLosersName()
	{
		if(_map.Nodes.get(0).GetOccupantName().compareTo("player1") == 0)
			return _sharedDataHandler.GetPlayer2Name();
		else
			return _sharedDataHandler.GetPlayer1Name();
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
		while(!IsGameEnded())
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
		StopGame();
		
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	_bestResults.SetGameResults(GetWinnersName(), GetLosersName());
	    		_bestResults.DisplayPopup();
	        }
	   });
	}
	
	private final ReentrantLock _commandExecuteLock;
	private final ChangeableMap _map;
	private boolean _isGameStarted;
	private ArrayList<GameUnit> _pendingUnits;
	private DrawingManager _drawingManager;
	private ISharedDataHandler _sharedDataHandler;
	private BestResults _bestResults;
	private int _sleepTime = 1000;
	
	private static final Logger _logger = Logger.getLogger( GameLogicExecutor.class.getName() );
}
