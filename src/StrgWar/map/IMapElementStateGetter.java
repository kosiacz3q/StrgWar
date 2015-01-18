package StrgWar.map;

import java.util.ArrayList;
import javafx.util.Pair;

public interface IMapElementStateGetter
{
	ArrayList<Pair<String, Integer>> GetUnits();

	MapElementType GetMapElementType();
}
