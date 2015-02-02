package StrgWar.stage;

import java.io.IOException;
import java.util.HashMap;

public class StageManager implements IStageSetter, IAlgorithmSetter
{
	public StageManager() throws IOException
	{
		_stages = new HashMap<String, Stage>();
		_actualStageName = "";
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
		if (_actualStageName.length() != 0)
		{
			_stages.get(_actualStageName).OnExit();
		}
		
		_stages.get(name).OnStart(_algorithm1, _algorithm2);
		_actualStageName = name;
	}
	
	public void SetAlgorithms(String algorithm1, String algorithm2)
	{
		_algorithm1 = algorithm1;
		_algorithm2 = algorithm2;
	}

	private HashMap<String, Stage> _stages;
	private String _actualStageName;
	private String _algorithm1;
	private String _algorithm2;
}
