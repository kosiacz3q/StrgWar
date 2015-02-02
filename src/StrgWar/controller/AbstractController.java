package StrgWar.controller;

import StrgWar.stage.IAlgorithmSetter;
import StrgWar.stage.IStageSetter;

public abstract class AbstractController {
	public final static void SetStageSetter(IStageSetter stageSetter)
	{
		_stageSetter = stageSetter;
	}
	
	public final static void SetAlgorithmSetter(IAlgorithmSetter algorithmSetter)
	{
		_algorithmSetter = algorithmSetter;
	}
	
	protected static IStageSetter _stageSetter;
	protected static IAlgorithmSetter _algorithmSetter;
}
