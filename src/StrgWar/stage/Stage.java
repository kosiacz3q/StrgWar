package StrgWar.stage;

public abstract class Stage
{
	public abstract void OnStart();

	public abstract void OnExit();

	public abstract String GetName();

	public StageManager GameStageManager;
}
