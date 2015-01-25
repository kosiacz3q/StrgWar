package StrgWar.map;

import javafx.geometry.Point2D;
import StrgWar.core.IUpdateable;

public class GameUnit implements IUpdateable
{
	public GameUnit(int movementSpeed, Point2D origin, Point2D target)
	{
		_movementSpeed = movementSpeed;
		_roadLength = (float) origin.distance(target);
		_traveled = 0;
	}

	@Override
	public void Update(float time)
	{
		_traveled += _movementSpeed * time;
	}

	public boolean IsTravelComplete()
	{
		return _traveled >= _roadLength;
	}
	
	private int _movementSpeed;
	private float _roadLength;
	private float _traveled;
}
