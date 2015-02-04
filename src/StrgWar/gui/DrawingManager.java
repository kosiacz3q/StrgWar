package StrgWar.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import StrgWar.core.IPlayerColorProvider;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class DrawingManager
{
	public DrawingManager(GraphicsContext gc, Pane root, IPlayerColorProvider playerColorProvider)
	{
		_root = root;
		_gc = gc;
		_drawable = Collections.synchronizedList(new ArrayList<IDrawable>());
		_playerColorProvider = playerColorProvider;
	}
	
	public void Register(IDrawable drawwable)
	{
		_drawable.add(drawwable);
	}
	
	public void RemoveElement(IDrawable drawwable) {
		_drawable.remove(drawwable);
	}
	
	public void draw(long now)
	{
		_gc.clearRect(0, 0, 900, 600);
		
		synchronized(_drawable) {
		for (IDrawable drawable : _drawable)
			drawable.Draw(_gc, _root, _playerColorProvider, now);
		}
	}
	
	private final Pane _root;
	private final GraphicsContext _gc;
	private final List<IDrawable> _drawable;
	private final IPlayerColorProvider _playerColorProvider;	
}
