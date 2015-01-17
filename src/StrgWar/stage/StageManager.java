package StrgWar.stage;

import java.io.IOException;
import java.util.HashMap;

public class StageManager{
	
	public StageManager() throws IOException
	{
		_stages = new HashMap<String, Stage>();
	}
	
	public void RegisterStage(Stage stage)
	{
		if (!_stages.containsKey(stage.GetName()))
		{
			stage.GameStageManager = this;
			_stages.put(stage.GetName(), stage);
		}
	}
	
	public void SetStage(String name)
	{
		_stages.get(name).OnStart();
	}
	
	private HashMap<String, Stage> _stages;
}
