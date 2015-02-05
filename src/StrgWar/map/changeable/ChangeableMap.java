package StrgWar.map.changeable;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class ChangeableMap
{
	public ChangeableMap()
	{
		Nodes = new ArrayList<ChangeableNode>();
	}

	public final ArrayList<ChangeableNode> Nodes;

	public ChangeableNode Find(String name)
	{
		for (ChangeableNode node : Nodes)
			if (node.GetMapElementName().compareTo(name) == 0) 
				return node;

		return null;
	}

	public ChangeableNode FindByPoint(Point2D point)
	{
		for (ChangeableNode node : Nodes)
		{
			if (new Rectangle(node.GetPosition().getX() - node.GetRadius() / 2, node.GetPosition().getY() - node.GetRadius() / 2,
					node.GetRadius(), node.GetRadius()).contains(point))
			{
				return node;
			}
		}

		return null;
	}
}
