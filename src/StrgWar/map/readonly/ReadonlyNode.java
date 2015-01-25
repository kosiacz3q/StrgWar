package StrgWar.map.readonly;

import javafx.geometry.Point2D;

public class ReadonlyNode
{
	public ReadonlyNode(String mapElementName)
	{
		_mapElementName = mapElementName;

		_occupantName = "neutral";
		_occupantSize = 0;
		_income = 0;
	}
	
	public void SetPosition(Point2D position)
	{
		_position = position;
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

	public Point2D GetPosition()
	{
		return _position;
	}

	protected int _occupantSize;
	protected String _occupantName;
	protected int _income;
	protected String _mapElementName;

	protected Point2D _position;

}
