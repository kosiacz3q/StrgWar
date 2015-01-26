package StrgWar.map.readonly;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class ReadonlyMap
{
	public ReadonlyMap()
	{
		nodes = new ArrayList<ReadonlyNode>();
	}
	
	public void DrawMap(GraphicsContext gc) {
		for(ReadonlyNode node : nodes)
		{
			int x = (int)node.GetPosition().getX();
			int y = (int)node.GetPosition().getY();
			
			node.PrintNode(gc, null, x, y, 50); 
		}
	}
	
	public ArrayList<ReadonlyNode> nodes;
}
