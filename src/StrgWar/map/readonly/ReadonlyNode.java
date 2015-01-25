package StrgWar.map.readonly;

public class ReadonlyNode
{
	public ReadonlyNode(String mapElementName)
	{
		_mapElementName = mapElementName;
		
		_occupantName = "neutral";
		_occupantSize = 0;
		_income = 0;
	}
	
	public String GetOccupantName()
	{
		return _occupantName;
	}
	
	public int GetOccupantArmySize()
	{
		return _occupantSize;
	}
	
	public String GetMapElementName()
	{
		return _mapElementName;

	}
	
	public int GetIncome()
	{
		return _income;
	}

	protected int _occupantSize;
	protected String _occupantName;
	protected int _income;
	protected String _mapElementName;
	
}
