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
	
	@Override
	public void Update(float time)
	{
		_accumulatedTime += time;
		
		if (_accumulatedTime > 100)
		{
			if (sendingTarget != null)
			{
				for (int i = 0; i < unitPer100Milisecond; ++i)
				{
					_sentUnitsManager.ReceiveUnits(new GameUnit(this, sendingTarget) );
				}
			}
			
			_accumulatedTime -= 100;
		}
	}
	
	private float _accumulatedTime;
	private final int unitPer100Milisecond = 5;
	private ISentUnitsManager _sentUnitsManager;
	private ChangeableNode sendingTarget;
}
