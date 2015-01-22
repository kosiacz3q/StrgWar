package StrgWar.map.readonly;

import StrgWar.map.MapElement;
import StrgWar.map.MapElementType;

public class ReadonlyEdge extends MapElement
{
	@Override
	public MapElementType GetMapElementType()
	{
		return MapElementType.EDGE;
	}

	@Override
	public void Update(float time)
	{
		// TODO update pending units
	}

}
