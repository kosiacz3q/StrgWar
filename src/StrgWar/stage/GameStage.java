package StrgWar.stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import StrgWar.ai.AbstractActor;
import StrgWar.ai.GameLogicExecutor;
import StrgWar.ai.implementations.LKosiakAI;
import StrgWar.map.loader.MapFromXmlLoader;
import StrgWar.map.loader.RawNode;
import StrgWar.map.providers.MapFromFileProvider;
import StrgWar.map.readonly.ReadonlyNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GameStage extends StrgWar.stage.Stage {
	public GameStage(javafx.stage.Stage primaryStage) throws IOException,
			SAXException, ParserConfigurationException {
		_primaryStage = primaryStage;
		Pane root = (Pane) FXMLLoader.load(getClass().getResource(
				"../gui/GameView.fxml"));

		_canvas = (Canvas) root.getChildren().get(0);

		_gc = _canvas.getGraphicsContext2D();

		_gameScene = new Scene(root, 900, 600);
		_gameScene.getStylesheets().add(
				getClass().getResource("../gui/application.css")
						.toExternalForm());

		String rootDir = new File(".").getCanonicalPath();
		File subDir = new File(rootDir, "/resources/maps");
		File fXmlFile = new File(subDir, "map0.xml");

		_mffp = new MapFromFileProvider(new MapFromXmlLoader(
				fXmlFile.getAbsolutePath()));

		_gameLogicExecutor = new GameLogicExecutor(_mffp);

		_players = new ArrayList<AbstractActor>();
	}

	@Override
	public void OnStart() {
		_primaryStage.setScene(_gameScene);
		_primaryStage.show();

		_players.add(new LKosiakAI(_gameLogicExecutor, _mffp, "kosiacz3q_1"));

		_players.add(new LKosiakAI(_gameLogicExecutor, _mffp, "kosiacz3q_2"));

		for (ReadonlyNode node : _mffp.GetReadOnlyMap().nodes) {
			node.PrintNode(_gc, null);
		}
	}

	@Override
	public void OnExit() {

	}

	public String GetName() {
		return "GAME";
	}

	private Canvas _canvas;
	private GraphicsContext _gc;
	private javafx.stage.Stage _primaryStage;
	private Scene _gameScene;

	private GameLogicExecutor _gameLogicExecutor;
	private MapFromFileProvider _mffp;

	private ArrayList<AbstractActor> _players;
}
