package StrgWar.map.readonly;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ReadonlyNode
{
	public ReadonlyNode(String mapElementName)
	{
		_mapElementName = mapElementName;

		_occupantName = "neutral";
		_occupantSize = 0;
		_income = 0;
	}
	
	public void PrintNode(GraphicsContext gc, Color color) {
		if(color == null )
			color = Color.rgb(205, 192, 176); //neutral
		
		gc.setStroke(color);
		gc.strokeOval(this._position.getX(), this._position.getY(), 2 * _radius, 2 * _radius);
	}
	
	public void SetPosition(Point2D position)
	{
		_position = position;
	}
	
	public void SetRadius(int radius)
	{
		_radius = radius;
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
	protected int _radius;

}
