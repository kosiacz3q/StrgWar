package StrgWar.map.changeable;

import java.util.HashMap;

import StrgWar.map.readonly.ReadonlyNode;

public class ChangeableNode extends ReadonlyNode
{
	public ChangeableNode()
	{
		_edgeHashMap = new HashMap<String, ChangeableEdge>();
	}
	
	public void StartSendingUnitsTo(String nodeName)
	{
		
	}
	
	public void AddEdge(String nodeName , ChangeableEdge edge)
	{
		_edgeHashMap.put(nodeName, edge);
	}
	
	private HashMap<String, ChangeableEdge> _edgeHashMap;
}
