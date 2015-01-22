package StrgWar.map;

import StrgWar.map.changeable.ChangeableMap;
import StrgWar.map.changeable.IChangeableMapProvider;
import StrgWar.map.readonly.IReadonlyMapProvider;
import StrgWar.map.readonly.ReadonlyMap;

public class MapProvider implements IChangeableMapProvider, IReadonlyMapProvider
{

	@Override
	public ReadonlyMap GetReadOnlyMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeableMap GetChangeableMap()
	{
		// TODO Auto-generated method stub
		return null;
	}



}
