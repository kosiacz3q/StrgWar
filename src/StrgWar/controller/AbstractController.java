package StrgWar.controller;

import javafx.stage.Stage;
import StrgWar.core.ISharedDataHandler;
import StrgWar.stage.IStageSetter;

public abstract class AbstractController
{
	public final static void SetStageSetter(IStageSetter stageSetter)
	{
		_stageSetter = stageSetter;
	}
	
	public final static void SetSharedDataHandler(ISharedDataHandler sharedDataHandler)
	{
		_sharedDataHandler = sharedDataHandler;
	}
	
	public final static void SetStage(Stage primaryStage)
	{
		_primaryStage = primaryStage;
	}
	
	public final static Stage GetStage()
	{
		return _primaryStage;
	}

	protected static IStageSetter _stageSetter;
	protected static ISharedDataHandler _sharedDataHandler;
	
	protected static Stage _primaryStage;
}
