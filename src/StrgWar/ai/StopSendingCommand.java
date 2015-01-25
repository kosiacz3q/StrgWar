package StrgWar.ai;

public class StopSendingCommand extends ActorCommand
{
	public StopSendingCommand(String origin)
	{
		super(origin, null, ActorCommandType.STOP_SENDING);
	}
}
