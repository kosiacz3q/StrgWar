package StrgWar.core;

import javafx.scene.paint.Color;

public interface IPlayerColorProvider
{
	Color GetPlayerColor(String name);
	void SetPlayerColor(String name, Color color);
}
