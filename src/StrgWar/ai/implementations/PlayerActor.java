package StrgWar.ai.implementations;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.input.*;
import javafx.scene.Cursor;
import javafx.event.EventHandler;
import StrgWar.ai.AbstractActor;
import StrgWar.ai.GameLogicExecutor;
import StrgWar.ai.ICommandExecutor;
import StrgWar.ai.StartSendingUnits;
import StrgWar.ai.StopSendingCommand;
import StrgWar.gui.effects.ILineDrawer;
import StrgWar.map.readonly.IReadonlyMapProvider;
import StrgWar.map.readonly.ReadonlyMap;
import StrgWar.map.readonly.ReadonlyNode;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;

public class PlayerActor extends AbstractActor
{
	public PlayerActor(ICommandExecutor commandExecutor, IReadonlyMapProvider readonlyMapProvider, Pane root, ILineDrawer lineDrawer)
	{
		super(commandExecutor, readonlyMapProvider);
	
		_playerWrapper = this;

		_map = readonlyMapProvider.GetReadOnlyMap();
		
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
				  
				  Point2D dest =  new Point2D(mouseEvent.getX(), mouseEvent.getY());
				  
				  ReadonlyNode originNode = _map.FindByPoint(origin);
				  ReadonlyNode destNode = _map.FindByPoint(dest);
				  
				  if (originNode != null)
				  {
					  if (destNode != null)
					  {
						  _commandExecutor.ExecuteCommand(_playerWrapper, new StartSendingUnits(originNode.GetMapElementName(), destNode.GetMapElementName()));
						  _logger.log(Level.FINE, "Human Player sent units from " + originNode.GetMapElementName() + "(" + originNode.GetOccupantArmySize()  +")" + " to " + destNode.GetMapElementName() + "(" + destNode.GetOccupantArmySize() + ")");
					  }
					  else
					  {
						  _commandExecutor.ExecuteCommand(_playerWrapper, new StopSendingCommand(originNode.GetMapElementName()));
						  
					  }
				  }
				  
				  _lineDrawer.DrawLine(origin,dest);  
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
		return  "player2";
	}

	private ReadonlyMap _map;
	private Pane _root;
	private ILineDrawer _lineDrawer;
	private Point2D origin;
	private static AbstractActor _playerWrapper;
	private static final Logger _logger = Logger.getLogger( PlayerActor.class.getName() );
}
