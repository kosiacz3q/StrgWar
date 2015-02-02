package StrgWar.map;

import StrgWar.core.IUpdateable;
import StrgWar.map.changeable.ChangeableNode;

public class GameUnit implements IUpdateable
{
	public GameUnit(ChangeableNode origin, ChangeableNode target,String ownerName, int count)
	{
		_roadLength = (float) origin.GetPosition().distance(target.GetPosition());
		_traveled = 0;
		_movementSpeed = 1;
		_target = target;
		
		Owner = ownerName;
		Count = count;
	}

	@Override
	public void Update(float time)
	{
		if (!IsTravelComplete())
		{
			_traveled += _movementSpeed * time;	
			PrintUnit(_traveled);
			
			if (IsTravelComplete())
				EndTravel();
		}
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
	
	private void EndTravel()
	{
		_target.AddUnits(this);
	}
	
	private void PrintUnit(float traveled)
	{
		System.out.println("przesuwam sie: " + traveled);
	}
	
	private ChangeableNode _target;
	private ChangeableNode _origin;
	
	
	public final int Count;
	public final String Owner;
	private final float _movementSpeed;
	private float _roadLength;
	private float _traveled;
}
