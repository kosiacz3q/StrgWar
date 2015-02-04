package StrgWar.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import StrgWar.core.IPlayerColorProvider;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.core.IUpdateable;
import StrgWar.gui.DrawingManager;
import StrgWar.gui.IDrawable;
import StrgWar.map.changeable.ChangeableNode;

public class GameUnit implements IUpdateable, IDrawable
{
	public GameUnit(ChangeableNode origin, ChangeableNode target, String ownerName, int count)
	{
		_origin = origin;
		_roadLength = (float) origin.GetPosition().distance(target.GetPosition());
		_traveled = 0;
		_movementSpeed = 1;
		_target = target;
		
		Owner = ownerName;
		Count = count;
	}

	@Override
	public void Update(float time)
	{
		if (!IsTravelComplete())
		{
			_traveled += _movementSpeed * time;	
		}
		
		if (IsTravelComplete())
			EndTravel();
	}
	
	@Override
	public void Draw(GraphicsContext gc, Pane root, IPlayerColorProvider playerColorProvider, long now)
	{
		gc.setStroke(playerColorProvider.GetPlayerColor(Owner));
		gc.strokeOval(_currentPosition.getX(), _currentPosition.getY(), 10, 10);
	}

	public boolean IsTravelComplete()
	{
		return _traveled >= _roadLength;
	}
	
	public ChangeableNode GetTarget()
	{
		return _target;
	}
	
	public ChangeableNode GetOrigin()
	{
		return _origin;
	}
	
	private void EndTravel()
	{
		_logger.log(Level.FINE, "units from " + _origin.GetMapElementName() + " to " + _target.GetMapElementName());
		_target.AddUnits(this);
		
	}
	
	private ChangeableNode _target;
	private ChangeableNode _origin;
	
	
	public final int Count;
	public final String Owner;
	private final float _movementSpeed;
	private float _roadLength;
	private float _traveled;
	private static final Logger _logger = Logger.getLogger( GameUnit.class.getName() );
}
