package StrgWar.stage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuStage extends StrgWar.stage.Stage
{
	public MenuStage(Stage primaryStage) throws IOException
	{
		_primaryStage = primaryStage;
		_primaryStage.setResizable(false);
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../gui/MenuView.fxml"));
		_menuScene = new Scene(root, 900, 600);
		_menuScene.getStylesheets().add(getClass().getResource("../gui/application.css").toExternalForm());

		menuStage = this;
	}

	@Override
	public void OnStart()
	{
		_primaryStage.setScene(_menuScene);
		_primaryStage.show();
	}

	@Override
	public void OnExit()
	{

	}

	public String GetName()
	{
		return "MENU";
	}

	public static MenuStage GetController()
	{
		return menuStage;
	}

	private static MenuStage menuStage;

	private Stage _primaryStage;
	private Scene _menuScene;
}
