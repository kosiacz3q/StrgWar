package StrgWar.ai.implementations;

import StrgWar.ai.AbstractActor;
import StrgWar.ai.ActorCommand;
import StrgWar.ai.ActorCommandType;
import StrgWar.ai.ICommandExecutor;
import StrgWar.map.readonly.IReadonlyMapProvider;

public class LKosiakAI extends AbstractActor
{
	public LKosiakAI(ICommandExecutor commandExecutor, IReadonlyMapProvider readonlyMapProvider, String name)
	{
		super(commandExecutor , readonlyMapProvider);
		
		_name = name;
	}
	
	
	@Override
	public void run()
	{
		boolean isInterrupted = false;
		
		//AI LOGIC
		while (isInterrupted)
		{
			_commandExecutor.ExecuteCommand(this, new ActorCommand("someOrigin", "someDestination" , ActorCommandType.START_SENDING));
			
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				isInterrupted = true;
			}
		}
	}


	@Override
	public final String GetName()
	{
		return _name;
	}
	
	private final String _name;
}
