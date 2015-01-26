package StrgWar.map.readonly;

import StrgWar.gui.IDrawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
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

	public void PrintNode(GraphicsContext gc, Pane root, Color color, int x, int y, int r) {
		if(color == null)
			color = Color.BLACK;// Color.rgb(205, 192, 176); //neutralny
		
		gc.setLineWidth(5);
		gc.setStroke(color);
		
		Button btn = new Button();
		//btn.setId(_mapElementName);
		btn.setText(Integer.toString(_occupantSize));
		btn.setShape(createNodeShape(r));
		btn.setLayoutX(x);
		btn.setLayoutX(y);
		root.getChildren().add(btn);

		/*gc.strokeOval(x, y, 2 * r, 2 * r);
			
		gc.setFont(Font.font("Calibri", 20));
		gc.setFill(color);
		gc.setTextAlign(TextAlignment.CENTER);

		gc.fillText(Integer.toString(_occupantSize), x + r, y + r);*/
	}
	
	public void RedrawNode(GraphicsContext gc, Pane root, Color color, int x, int y, int r) {
		gc.clearRect(x, y, 2 *r, 2 * r);
		PrintNode(gc, root, color, x, y, r);
	}
	
	public Shape createNodeShape(double r) {
		Circle circle = new Circle(r);
		
		return circle;
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

		gc.strokeOval(
				_position.getX() - _radius / 2, 
				_position.getY() - _radius / 2,
				_radius,
				_radius);
			
		gc.setFont(Font.font("Calibri", 20));
		gc.setFill(color);
		gc.setTextAlign(TextAlignment.CENTER);

		gc.fillText(Integer.toString(_occupantSize), _position.getX(), _position.getY());	
	}

	protected int _occupantSize;
	protected String _occupantName;
	protected int _income;
	protected String _mapElementName;
	protected int _radius;
	protected Point2D _position;

	

}
