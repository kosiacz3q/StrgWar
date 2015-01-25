package StrgWar.map.changeable;

import StrgWar.core.IUpdateable;
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
	
	public void StartSendingUnitsTo(String nodeName)
	{
		
	}
	
	@Override
	public void Update(float time)
	{
				
	}
	
	
}
