package StrgWar.map.readonly;

import java.util.Map;

import StrgWar.map.MapElement;
import StrgWar.map.MapElementType;

public class ReadonlyNode extends MapElement
{
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

	protected Map<String, ReadonlyEdge> cityNameToRouteMap;

}
