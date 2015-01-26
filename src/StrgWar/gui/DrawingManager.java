package StrgWar.gui;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class DrawingManager
{
	public DrawingManager(GraphicsContext gc)
	{
		_gc = gc;
		_drawable = new ArrayList<IDrawable>();
	}
	
	public void Register(IDrawable drawwable)
	{
		_drawable.add(drawwable);
	}
	
	public void draw()
	{
		_gc.clearRect(0, 0, 900, 600);
		
		for (IDrawable drawable : _drawable)
			drawable.Draw(_gc);
	}
	
	private final GraphicsContext _gc;
	private final ArrayList<IDrawable> _drawable;
	
}
