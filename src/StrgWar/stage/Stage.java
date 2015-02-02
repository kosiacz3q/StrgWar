package StrgWar.stage;

public abstract class Stage
{
	public abstract void OnStart(String algorithm1, String algorithm2);

	public abstract void OnExit();

	public abstract String GetName();

	public StageManager GameStageManager;
}
