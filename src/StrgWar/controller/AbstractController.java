package StrgWar.controller;

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

	protected static IStageSetter _stageSetter;
	protected static ISharedDataHandler _sharedDataHandler;
}
