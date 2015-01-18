package StrgWar.map;

import StrgWar.core.IUpdateable;

public class Unit implements IUpdateable
{
	public Unit(int movementSpeed, int roadLength)
	{
		_movementSpeed = movementSpeed;
		_roadLength = roadLength;
	}

	@Override
	public void Update(float time)
	{
		// TODO Auto-generated method stub
	}

	private int _movementSpeed;
	private int _roadLength;
}
