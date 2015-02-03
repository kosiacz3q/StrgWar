package StrgWar.core;

public interface ISharedDataHandler
{
	String GetPlayer1Name();
	String GetPlayer2Name();
	void SetPlayer1Name(String name);
	void SetPlayer2Name(String name);
	
	String GetPlayer1Color();
	String GetPlayer2Color();
	void SetPlayer1Color(String name);
	void SetPlayer2Color(String name);
}
