package StrgWar.map;

import StrgWar.core.IUpdateable;
import StrgWar.map.changeable.ChangeableNode;

public class GameUnit implements IUpdateable
{
	public GameUnit(ChangeableNode origin, ChangeableNode target)
	{
		_roadLength = (float) origin.GetPosition().distance(target.GetPosition());
		_traveled = 0;
		_movementSpeed = 1;
		_target = target;
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
	
	public ChangeableNode GetTarget()
	{
		return _target;
	}
	
	public ChangeableNode GetOrigin()
	{
		return _origin;
	}
	
	private ChangeableNode _target;
	private ChangeableNode _origin;
	
	private final float _movementSpeed;
	private float _roadLength;
	private float _traveled;
}
