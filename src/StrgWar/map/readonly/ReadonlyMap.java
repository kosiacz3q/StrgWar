package StrgWar.map.readonly;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class ReadonlyMap
{
	public ReadonlyMap()
	{
		Nodes = new ArrayList<ReadonlyNode>();
	}

	public void DrawMap(GraphicsContext gc, Pane root) {
		for(ReadonlyNode node : nodes)
		{
			int x = (int)node.GetPosition().getX();
			int y = (int)node.GetPosition().getY();
			
			node.PrintNode(gc, root, null, x, y, 50); 

	public ReadonlyNode Find(String name)
	{
		for (ReadonlyNode node : Nodes)
			if (node.GetMapElementName().compareTo(name) == 0)
				return node;
		
		return null;
	}
	
	public ReadonlyNode FindByPoint(Point2D point)
	{
		for (ReadonlyNode node : Nodes)
		{
			if (new Rectangle(
					node.GetPosition().getX() - node.GetRadius() / 2,
					node.GetPosition().getY() - node.GetRadius() / 2,
					node.GetRadius() ,
					node.GetRadius()
					).contains(point))
			{
				return node;
			}
		}
		
		return null;
	}
	
	public final ArrayList<ReadonlyNode> Nodes;
}
