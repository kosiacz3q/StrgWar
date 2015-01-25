package StrgWar.ai;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.map.changeable.ChangeableMap;
import StrgWar.map.changeable.ChangeableNode;
import StrgWar.map.changeable.IChangeableMapProvider;

public class GameLogicExecutor implements ICommandExecutor
{
	public GameLogicExecutor(IChangeableMapProvider changeableMapProvider)
	{
		_commandExecuteLock = new ReentrantLock();
		
		_map = changeableMapProvider.GetChangeableMap();
	}
	
	@Override
	public void ExecuteCommand(AbstractActor abstractActor, ActorCommand actorCommand)
	{
		System.out.println("wysylam");
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
		finally
		{
			_commandExecuteLock.unlock();
		}
	}
	
	private final ReentrantLock _commandExecuteLock;
	private final ChangeableMap _map;
	private static final Logger _logger = Logger.getLogger( GameLogicExecutor.class.getName() );
}
