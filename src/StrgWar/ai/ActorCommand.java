package StrgWar.ai;

public class ActorCommand 
{
	public ActorCommand(String origin, String destination, ActorCommandType actorCommandType )
	{
		_origin = origin;
		_destination = destination;
		_actorCommandType = actorCommandType;
	}
	
	
	public String GetOrigin()
	{
		return _origin;
	}

	public String GetDestination()
	{
		return _destination;
	}
	
	public ActorCommandType GetCommandType()
	{
		return _actorCommandType;
	}

	private String _origin;
	private String _destination;
	private ActorCommandType _actorCommandType;
	
}
