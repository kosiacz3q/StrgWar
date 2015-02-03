package StrgWar.core;

public class SharedDataHandler implements ISharedDataHandler
{
	@Override
	public String GetPlayer1Name()
	{
		return _player1Name;
	}

	@Override
	public String GetPlayer2Name()
	{
		return _player2Name;
	}

	@Override
	public void SetPlayer1Name(String name)
	{
		_player1Name = name;
	}

	@Override
	public void SetPlayer2Name(String name)
	{
		_player2Name = name;
	}
	
	@Override
	public String GetPlayer1Color()
	{
		return _player1Color;
	}

	@Override
	public String GetPlayer2Color()
	{
		return _player2Color;
	}

	@Override
	public void SetPlayer1Color(String color)
	{
		_player1Color = color;
	}

	@Override
	public void SetPlayer2Color(String color)
	{
		_player2Color = color;
	}
	
	private String _player1Name;
	private String _player2Name;
	private String _player1Color;
	private String _player2Color;
}
