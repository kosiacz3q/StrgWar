package StrgWar.gui;

import StrgWar.core.IPlayerColorProvider;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public interface IDrawable
{
	void Draw(GraphicsContext gc, Pane root, IPlayerColorProvider playerColorProvider);
}
