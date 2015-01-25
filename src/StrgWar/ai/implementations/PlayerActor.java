package StrgWar.ai.implementations;
import javafx.scene.input.*;
import javafx.scene.Cursor;
import javafx.event.EventHandler;
import StrgWar.ai.AbstractActor;
import StrgWar.ai.ICommandExecutor;
import StrgWar.gui.effects.ILineDrawer;
import StrgWar.map.readonly.IReadonlyMapProvider;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;

public class PlayerActor extends AbstractActor
{
	
	public PlayerActor(ICommandExecutor commandExecutor, IReadonlyMapProvider readonlyMapProvider, Pane root, ILineDrawer lineDrawer)
	{
		super(commandExecutor, readonlyMapProvider);
	
		_lineDrawer = lineDrawer;
		_root = root;
		
		_root.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				  
				  origin = new Point2D(mouseEvent.getX(), mouseEvent.getY());

			   root.setCursor(Cursor.MOVE);
			  }
			});
		
		_root.setOnMouseReleased(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				  root.setCursor(Cursor.HAND);
				  
				  _lineDrawer.DrawLine(origin, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
				  
			  }
			});
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(500);
			}
			catch (Exception e)
			{
				break;
			}
		}
		
		_root.setOnMousePressed(null);
		_root.setOnMouseReleased(null);
	}

	@Override
	public String GetName()
	{
		return  "Player";
	}

	private Pane _root;
	private ILineDrawer _lineDrawer;
	private Point2D origin;
}
