package StrgWar.stage;

public abstract class Stage
{
	public abstract void OnStart() throws Exception;

	public abstract void OnExit() throws Exception;

	public abstract String GetName();

	public StageManager GameStageManager;
}
