package StrgWar.controller;

import StrgWar.stage.IStageSetter;

public abstract class AbstractController {
	public final static void SetStageSetter(IStageSetter stageSetter) {
	{
		_stageSetter = stageSetter;
	}
	
	protected static IStageSetter _stageSetter;
}
