package StrgWar.map.readonly;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.geometry.Point2D;

public class ReadonlyNodeArcs
{
	public enum Direction
	{
		LEFT, RIGHT
	}

	private Point2D _position;
	public double _radius;
	private double _angle;
	private double _arcExtent;
	private double _lineWidth = 2;
	private double _movement = 2;
	private Direction _direction = Direction.RIGHT;

	public ReadonlyNodeArcs(Point2D position, double radius, double angle, double arcExtent, double lineWidth, Direction direction)
	{
		_position = position;
		_radius = radius;
		_angle = angle;
		_arcExtent = arcExtent;
		_lineWidth = lineWidth;
		_direction = direction;
	}

	private long _startTime = 0;
	private long _displayTime = 60 * 1000000; // nano

	public void update(long now)
	{
		if (_startTime == 0) _startTime = now;

		long elapsedTime = now - _startTime;
		if (elapsedTime > _displayTime)
		{
			switch (_direction)
			{
				case LEFT:
					_angle = (_angle + _movement > 360) ? 0 : _angle + _movement;
					break;
				case RIGHT:
					_angle = (_angle - _movement < -360) ? 0 : _angle - _movement;
					break;
			}
			_startTime = 0;
		}
	}

	public void draw(GraphicsContext gc, Color color)
	{
		gc.setStroke(color);
		gc.setLineWidth(_lineWidth);
		gc.strokeArc(_position.getX(), _position.getY(), _radius * 2, _radius * 2, _angle, _arcExtent, ArcType.OPEN);
	}
}