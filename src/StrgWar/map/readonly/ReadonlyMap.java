package StrgWar.map.readonly;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class ReadonlyMap
{
	public ReadonlyMap()
	{
		nodes = new ArrayList<ReadonlyNode>();
	}
	
	public void DrawMap(GraphicsContext gc, Pane root) {
		for(ReadonlyNode node : nodes)
		{
			int x = (int)node.GetPosition().getX();
			int y = (int)node.GetPosition().getY();
			
			node.PrintNode(gc, root, null, x, y, 50); 
		}
	}
	
	public ArrayList<ReadonlyNode> nodes;
}
