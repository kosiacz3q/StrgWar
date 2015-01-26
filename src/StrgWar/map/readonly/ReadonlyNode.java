package StrgWar.map.readonly;

import StrgWar.gui.IDrawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ReadonlyNode implements IDrawable
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
	
	public float GetRadius()
	{
		return _radius;
	}
	
	@Override
	public void Draw(GraphicsContext gc)
	{
		Color color = Color.BLACK;// Color.rgb(205, 192, 176); //neutralny
		
		gc.setLineWidth(5);
		gc.setStroke(color);

		gc.strokeOval(_position.getX(), _position.getY(), 2 * _radius, 2 * _radius);
			
		gc.setFont(Font.font("Calibri", 20));
		gc.setFill(color);
		gc.setTextAlign(TextAlignment.CENTER);

		gc.fillText(Integer.toString(_occupantSize), _position.getX() + _radius, _position.getY() + _radius);
		
	}

	protected int _occupantSize;
	protected String _occupantName;
	protected int _income;
	protected String _mapElementName;
	protected int _radius;
	protected Point2D _position;

	

}
