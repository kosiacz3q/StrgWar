package StrgWar.core;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class PlayerColorProvider implements IPlayerColorProvider
{
	public PlayerColorProvider()
	{
		_playerColor = new HashMap<String, Color>();
	}

	@Override
	public Color GetPlayerColor(String name)
	{
		return _playerColor.get(name);
	}

	@Override
	public void SetPlayerColor(String name, Color color)
	{
		_playerColor.put(name, color);
	}

	private HashMap<String, Color> _playerColor;
}
