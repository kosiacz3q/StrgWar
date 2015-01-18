package StrgWar;

import javafx.application.Application;
import javafx.stage.Stage;
import StrgWar.stage.GameStage;
import StrgWar.stage.MenuStage;
import StrgWar.stage.StageManager;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			_stageManager = new StageManager();

			_stageManager.RegisterStage(new MenuStage(primaryStage));
			_stageManager.RegisterStage(new GameStage(primaryStage));

			_stageManager.SetStage("GAME");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	StageManager _stageManager;
}
