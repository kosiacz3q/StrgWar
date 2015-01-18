package StrgWar.map;

import java.util.Map;

public class Node extends MapElement
{

	public void StartSendingUnitsTo(String nodeName)
	{

	}

	@Override
	public MapElementType GetMapElementType()
	{
		return MapElementType.NODE;
	}

	@Override
	public void Update(float time)
	{
		// TODO send units to destination
	}

	protected Map<String, Edge> cityNameToRouteMap;

}
