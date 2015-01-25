package StrgWar.ai.implementations;

import StrgWar.ai.AbstractActor;
import StrgWar.ai.ICommandExecutor;
import StrgWar.map.readonly.IReadonlyMapProvider;

public class PlayerActor extends AbstractActor
{
	public PlayerActor(ICommandExecutor commandExecutor, IReadonlyMapProvider readonlyMapProvider, Pane root)
	{
		super(commandExecutor, readonlyMapProvider);

		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			   x1=mouseEvent.getX();
			   y1=mouseEvent.getY();
			   line.setStartX(x1);
			   line.setStartY(y1);
			   System.out.println("prr "+x1+" "+y1);
			   root.setCursor(Cursor.MOVE);
			  }
			});
		
		root.setOnMouseReleased(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				  root.setCursor(Cursor.HAND);
			  }
			});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				  x2=mouseEvent.getX();
				   y2=mouseEvent.getY();
				   
				   line.setEndX(x1);
				   line.setEndY(y1);
				   System.out.println("dff"+x2+" "+y2); }
			});
		 
		root.setOnMouseEntered(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				 root.setCursor(Cursor.HAND);
			  }
			});
		root.getChildren().add(line);
		
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String GetName()
	{
		return  "Player";
	}

}
