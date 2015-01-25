package StrgWar.ai;

public class StartSendingUnits extends ActorCommand
{
	public StartSendingUnits(String origin, String destination)
	{
		super(origin, destination, ActorCommandType.START_SENDING);
	}

}
