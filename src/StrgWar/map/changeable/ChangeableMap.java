package StrgWar.map.changeable;

import java.util.ArrayList;

public class ChangeableMap
{
	public ArrayList<ChangeableNode> nodes;
	
	
	public ChangeableNode Find(String name)
	{
		for (ChangeableNode node : nodes)
			if (node.GetMapElementName().compareTo(name) == 0)
				return node;
		
		return null;
	}
}
