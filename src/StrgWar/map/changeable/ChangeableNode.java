package StrgWar.map.changeable;

import StrgWar.core.IUpdateable;
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
	}
	
	public void SetUnitsReceiver(ISentUnitsManager sentUnitsManager)
	{
		_sentUnitsManager = sentUnitsManager;
	}
	
	public void StartSendingUnitsTo(String nodeName)
	{
		sendingTargetTarget = nodeName;
	}
	
	public void StopSendingUnits()
	{
		sendingTargetTarget = "";
	}
	
	@Override
	public void Update(float time)
	{
				
	}
	
	private ISentUnitsManager _sentUnitsManager;
	private String sendingTargetTarget;
}
