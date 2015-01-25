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

public class GameStage extends StrgWar.stage.Stage
{
	public GameStage(javafx.stage.Stage primaryStage) throws IOException, SAXException, ParserConfigurationException
	{
		_primaryStage = primaryStage;
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("../gui/GameView.fxml"));

		_canvas = (Canvas) root.getChildren().get(0);

		_gc = _canvas.getGraphicsContext2D();

		_gameScene = new Scene(root, 900, 600);
		_gameScene.getStylesheets().add(getClass().getResource("../gui/application.css").toExternalForm());
		
		String rootDir = new File(".").getCanonicalPath();
		File subDir = new File(rootDir, "/resources/maps");
		File fXmlFile = new File(subDir, "map0.xml");
		
		 _mffp = new MapFromFileProvider(new MapFromXmlLoader(fXmlFile.getAbsolutePath()));
		 
		 _gameLogicExecutor = new GameLogicExecutor(_mffp);
		 
		 _players = new ArrayList<AbstractActor>();
	}

	@Override
	public void OnStart()
	{
		_primaryStage.setScene(_gameScene);
		_primaryStage.show();
		
		_players.add(new LKosiakAI(_gameLogicExecutor, _mffp, "kosiacz3q_1"));
		
		_players.add(new LKosiakAI(_gameLogicExecutor, _mffp, "kosiacz3q_2"));


		for(ReadonlyNode node : _mffp.GetReadOnlyMap().nodes)
		{
			node.PrintNode(_gc, null); 
		}
		
		
		/*
		_gc.setFill(Color.GREEN);
		_gc.setStroke(Color.BLUE);
		_gc.setLineWidth(5);
		_gc.strokeLine(40, 10, 10, 40);
		_gc.fillOval(10, 60, 30, 30);
		_gc.strokeOval(60, 60, 30, 30);
		_gc.fillRoundRect(110, 60, 30, 30, 10, 10);
		_gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
		_gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
		_gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
		_gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
		_gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
		_gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
		_gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
		_gc.fillPolygon(new double[] { 10, 40, 10, 40 }, new double[] { 210, 210, 240, 240 }, 4);
		_gc.strokePolygon(new double[] { 60, 90, 60, 90 }, new double[] { 210, 210, 240, 240 }, 4);
		_gc.strokePolyline(new double[] { 110, 140, 110, 140 }, new double[] { 210, 210, 240, 240 }, 4);*/
	}

	@Override
	public void OnExit()
	{

	}

	public String GetName()
	{
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
