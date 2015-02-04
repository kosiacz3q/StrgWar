package StrgWar.map.readonly;

import java.util.Random;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import StrgWar.core.IPlayerColorProvider;
import StrgWar.gui.IDrawable;

public class ReadonlyNode implements IDrawable {
	public ReadonlyNode(String mapElementName, Point2D position, int radius) {
		_mapElementName = mapElementName;

		_occupantName = "neutral";
		_occupantSize = 0;
		_income = 0;

		_position = position;
		_radius = radius;

		_arcs = CreateArcs(_radius / 3);
	}

	//TODO remove?
	public Shape createNodeShape(double r) {
		Circle circle = new Circle(r);

		return circle;
	}

	public void SetPosition(Point2D position) {
		_position = position;
	}

	public String GetOccupantName() {
		return _occupantName;
	}

	public int GetOccupantArmySize() {
		return _occupantSize;
	}

	public String GetMapElementName() {
		return _mapElementName;

	}

	public int GetIncome() {
		return _income;
	}

	public Point2D GetPosition() {
		return _position;
	}

	public float GetRadius() {
		return _radius;
	}

	public void update(long now) {
		for (ReadonlyNodeArcs arc : _arcs) {
			arc.update(now);
		}
	}

	@Override
	public void Draw(GraphicsContext gc, Pane root,
			IPlayerColorProvider playerColorProvider, long now) {
		update(now);

		for (ReadonlyNodeArcs arc : _arcs) {
			arc.draw(gc, playerColorProvider.GetPlayerColor(_occupantName));
		}
		gc.setFont(Font.font("Calibri", 30));
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);

		gc.fillText(Integer.toString(_occupantSize),
				_position.getX() + _radius, _position.getY() + _radius + 12);
	}

	public int RandomInt(int min, int max) {
		Random rand = new Random();
		int range = max - min + 1;

		return rand.nextInt(range) + min;
	}

	private ReadonlyNodeArcs[] CreateArcs(int numberOfLines) {
		final ReadonlyNodeArcs[] arcs = new ReadonlyNodeArcs[numberOfLines];
		for (int i = 0; i < numberOfLines; i++) {
			arcs[i] = CreateSingleArc();
		}
		
		return arcs;
	}

	private ReadonlyNodeArcs CreateSingleArc() {
		int r = RandomInt(20, (int) _radius);
		int lineWidth = RandomInt(2, 5);
		int angle = RandomInt(1, 270);
		int arcExtent = RandomInt(10, 360 - angle);
		ReadonlyNodeArcs.Direction direction  = (RandomInt(0, 1) == 0) ? ReadonlyNodeArcs.Direction.LEFT : ReadonlyNodeArcs.Direction.RIGHT;

		final ReadonlyNodeArcs arc = new ReadonlyNodeArcs(new Point2D(
				_position.getX() + (_radius - r), _position.getY()
						+ (_radius - r)), r, angle, arcExtent, lineWidth, direction);

		return arc;
	}

	protected int _occupantSize;
	protected String _occupantName;
	protected int _income;
	protected String _mapElementName;
	protected int _radius;
	protected Point2D _position;
	protected ReadonlyNodeArcs[] _arcs;
}
