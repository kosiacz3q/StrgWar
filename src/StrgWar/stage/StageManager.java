package StrgWar.stage;

import java.io.IOException;
import java.util.HashMap;

public class StageManager implements IStageSetter
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
		try
		{
			if (_actualStageName.length() != 0)
			{
				_stages.get(_actualStageName).OnExit();
			}

			_stages.get(name).OnStart();

			_actualStageName = name;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private HashMap<String, Stage> _stages;
	private String _actualStageName;
}
