package StrgWar.map.readonly;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class ReadonlyMap
{
	public ReadonlyMap()
	{
		Nodes = new ArrayList<ReadonlyNode>();
	}
	
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
