package StrgWar.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import StrgWar.core.IUpdateable;
import javafx.util.Pair;

public abstract class MapElement implements IMapElementStateGetter, IUpdateable
{

	public MapElement()
	{
		_playerUnitsCount = new HashMap<String, Integer>();
	}

	public ArrayList<Pair<String, Integer>> GetUnits()
	{
		ArrayList<Pair<String, Integer>> result = new ArrayList<Pair<String, Integer>>();

		for (Entry<String, Integer> pair : _playerUnitsCount.entrySet())
		{
			result.add(new Pair<String, Integer>(new String(pair.getKey()), new Integer(pair.getValue())));
		}

		return result;

	}

	protected HashMap<String, Integer> _playerUnitsCount;
}
