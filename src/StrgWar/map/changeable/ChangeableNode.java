package StrgWar.map.changeable;

import StrgWar.core.IUpdateable;
import StrgWar.map.GameUnit;
import StrgWar.map.ISentUnitsManager;
import StrgWar.map.readonly.ReadonlyNode;

public class ChangeableNode extends ReadonlyNode implements IUpdateable
{
	public ChangeableNode(String mapElementName, String occupant, int startSize, int income)
	{
		super(mapElementName);
		
		_occupantName = occupant;
		_income = income;
		_occupantSize = startSize;
		
		sendingTarget = null;
		
		_accumulatedTime = 0;
	}
	
	public void SetUnitsReceiver(ISentUnitsManager sentUnitsManager)
	{
		_sentUnitsManager = sentUnitsManager;
	}
	
	public void StartSendingUnitsTo(ChangeableNode cn)
	{
		sendingTarget = cn;
	}
	
	public void StopSendingUnits()
	{
		sendingTarget = null;
	}
	
	public void AddUnits(GameUnit gameUnit)
	{
		if (gameUnit.Owner.compareTo(_occupantName) == 0)
		{
			_occupantSize += gameUnit.Count;
		}
		else
		{
			_occupantSize -= gameUnit.Count;
			
			if (_occupantSize <= 0)
			{
				_occupantSize = -_occupantSize;
				_occupantName = gameUnit.Owner;
			}
		}
	}
	
	@Override
	public void Update(float time)
	{
		_accumulatedTime += time;
		
		if (_accumulatedTime > 100)
		{
			if (sendingTarget != null)
			{
					if (_occupantSize > 1)
					{
						int unitsToSend = _unitsPerSend % (_occupantSize);

						if (unitsToSend > 0)
						{
							_sentUnitsManager.ReceiveUnits(new GameUnit(this, sendingTarget, _occupantName, unitsToSend ));
							_occupantSize -= unitsToSend;
						}
					}
			}
			
			_accumulatedTime -= 100;
		}
	}
	
	private final int _unitsPerSend = 1;
	private float _accumulatedTime;
	private ISentUnitsManager _sentUnitsManager;
	private ChangeableNode sendingTarget;
}
