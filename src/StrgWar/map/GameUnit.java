package StrgWar.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import StrgWar.core.IPlayerColorProvider;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.core.IUpdateable;
import StrgWar.gui.IDrawable;
import StrgWar.map.changeable.ChangeableNode;

public class GameUnit implements IUpdateable, IDrawable {
	public GameUnit(ChangeableNode origin, ChangeableNode target,
			String ownerName, int count) {
		_origin = origin;
		_roadLength = (float) origin.GetPosition().distance(
				target.GetPosition());
		_traveled = 0;
		_movementSpeed = 0.5f;
		_target = target;
		_currentTime = System.nanoTime();
		
		_particles = new int[4][2];
		for(int i = 1; i < 4; i++) {
			_particles[i][0] = RandomInt(0, 20);
			_particles[i][1] = RandomInt(0, 20);
		}

		Owner = ownerName;
		Count = count;
	}

	@Override
	public void Update(float time) {
		if (!IsTravelComplete())
			_traveled += _movementSpeed * time;

		if (IsTravelComplete())
			EndTravel();
	}

	@Override
	public void Draw(GraphicsContext gc, Pane root,
			IPlayerColorProvider playerColorProvider, long now) {
		float timeInMili = (now - _currentTime) / 1000000;
		Update(timeInMili);
		
		if (!IsTravelComplete()) {
			double x = (_origin.GetPosition().getX() + _origin.GetRadius())
					+ (_traveled / _roadLength)
					* (_target.GetPosition().getX() - _origin.GetPosition()
							.getX());
			double y = (_origin.GetPosition().getY() + _origin.GetRadius())
					+ (_traveled / _roadLength)
					* (_target.GetPosition().getY() - _origin.GetPosition()
							.getY());

			gc.setStroke(playerColorProvider.GetPlayerColor(Owner));
			
			for(int i = 0; i < _particles.length; i++)
				gc.strokeOval(x + _particles[i][0], y + + _particles[i][1], _width, _width);
		}

		_currentTime = now;
	}

	public boolean IsTravelComplete() {
		return _traveled >= _roadLength;
	}

	public ChangeableNode GetTarget() {
		return _target;
	}

	public ChangeableNode GetOrigin() {
		return _origin;
	}

	private void EndTravel() {
		_logger.log(Level.FINE, "units from " + _origin.GetMapElementName()
				+ " to " + _target.GetMapElementName());
		_target.AddUnits(this);
	}
	
	public int RandomInt(int min, int max) {
		Random rand = new Random();
		int range = max - min + 1;

		return rand.nextInt(range) + min;
	}

	private ChangeableNode _target;
	private ChangeableNode _origin;

	public final int Count;
	public final String Owner;
	private final float _movementSpeed;
	private float _roadLength;
	private long _traveled;
	private int _width = 10;
	private long _currentTime;
	private int[][] _particles;
	private static final Logger _logger = Logger.getLogger(GameUnit.class
			.getName());
}
