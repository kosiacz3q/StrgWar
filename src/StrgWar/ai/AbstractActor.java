package StrgWar.ai;

import StrgWar.map.readonly.IReadonlyMapProvider;

public abstract class AbstractActor implements Runnable
{
	public AbstractActor(ICommandExecutor commandExecutor, IReadonlyMapProvider readonlyMapProvider)
	{
		_commandExecutor = commandExecutor;
		_readonlyMapProvider = readonlyMapProvider;
	}
		
	public abstract String GetName();

	protected final ICommandExecutor _commandExecutor;
	protected final IReadonlyMapProvider _readonlyMapProvider;
}
