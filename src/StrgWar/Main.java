package StrgWar;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.stage.Stage;
import StrgWar.controller.AbstractController;
import StrgWar.core.ISharedDataHandler;
import StrgWar.core.SharedDataHandler;
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
			
			ISharedDataHandler sharedDataHandler = new SharedDataHandler();
			
			AbstractController.SetStageSetter(_stageManager);
			AbstractController.SetSharedDataHandler(sharedDataHandler);
			
			_stageManager.RegisterStage(new MenuStage(primaryStage) );
			_stageManager.RegisterStage(new GameStage(_stageManager, primaryStage, sharedDataHandler));

			_stageManager.SetStage("MENU");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)  throws SecurityException, IOException
	{
		FileHandler fh = new FileHandler("logstrgwar.log", false);
		
		fh.setFormatter(new SimpleFormatter());
		
		_logger.addHandler(fh);
		
		_logger.setLevel(Level.ALL);
		
		_logger.log(Level.ALL , "[GAME STARTS]");
		
		launch(args);
		
		_logger.log(Level.ALL , "[GAME ENDS]");
	}

	private StageManager _stageManager;
	private final static Logger _logger = Logger.getLogger(Main.class.getName()); 
}
