package StrgWar.ai;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import StrgWar.map.changeable.ChangeableMap;
import StrgWar.map.changeable.IChangeableMapProvider;

public class GameLogicExecutor implements ICommandExecutor
{
	public GameLogicExecutor(IChangeableMapProvider changeableMapProvider)
	{
		_commandExecuteLock = new ReentrantLock();
		_AbstractControllers = new ArrayList<AbstractActor>();
		_changeableMapProvider = changeableMapProvider;
		_map = changeableMapProvider.GetChangeableMap();
	}
	
	@Override
	public void ExecuteCommand(AbstractActor abstractActor, ActorCommand actorCommand)
	{
		_commandExecuteLock.lock();
		
		try
		{
			//TODO maybe some move validation
			
			
			
			//abstractActor.GetName();
			
			//_changeableMapProvider.GetChangeableMap().edges;
			//_changeableMapProvider.GetChangeableMap().nodes;
			
			throw new UnsupportedOperationException("command execute");
		}
		finally
		{
			_commandExecuteLock.unlock();
		}
	}
	
	private final ReentrantLock _commandExecuteLock;
	private final ArrayList<AbstractActor> _AbstractControllers;
	private final IChangeableMapProvider _changeableMapProvider;
	private final ChangeableMap _map;
}
