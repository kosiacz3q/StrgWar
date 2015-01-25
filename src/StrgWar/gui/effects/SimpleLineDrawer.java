package StrgWar.gui.effects;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class SimpleLineDrawer extends ILineDrawer
{
	
	public SimpleLineDrawer(Pane root)
	{
		_line = new Line();
		
		_line.setStartX(0);
		_line.setStartY(0);
		
		_line.setEndX(0);
		_line.setEndY(0);
		
		root.getChildren().add(_line);
	}

	@Override
	public void DrawLine(Point2D origin, Point2D dest)
	{
		_line.setStartX(origin.getX());
		_line.setStartY(origin.getY());
		
		_line.setEndX(dest.getX());
		_line.setEndY(dest.getY());
	}
	
	private Line _line;
}
