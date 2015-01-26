package StrgWar.stage;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import StrgWar.ai.GameLogicExecutor;
import StrgWar.ai.implementations.LKosiakAI;
import StrgWar.ai.implementations.PlayerActor;
import StrgWar.gui.DrawingManager;
import StrgWar.gui.effects.SimpleLineDrawer;
import StrgWar.map.loader.MapFromXmlLoader;
import StrgWar.map.providers.MapFromFileProvider;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameStage extends StrgWar.stage.Stage
{
	public GameStage(javafx.stage.Stage primaryStage , String mapSource) throws IOException, SAXException, ParserConfigurationException
	{
		_primaryStage = primaryStage;
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("../gui/GameView.fxml"));

		_canvas = (Canvas) root.getChildren().get(0);

		_gc = _canvas.getGraphicsContext2D();

		_gameScene = new Scene(root, 900, 600);
		_gameScene.getStylesheets().add(getClass().getResource("../gui/application.css").toExternalForm());
		
		_mffp = new MapFromFileProvider(new MapFromXmlLoader(mapSource));
		 
		 _gameLogicExecutor = new GameLogicExecutor(_mffp);
		 
		 _threads = new ArrayList<Thread>();
		 
		 _root = root;
		 
		 _drawingManager = new DrawingManager(_gc);
	}

	@Override
	public void OnStart()
	{
		_primaryStage.setScene(_gameScene);
		_primaryStage.show();
		
		_mffp.GetReadOnlyMap().nodes.forEach(node -> _drawingManager.Register( node));
		
		//_threads.add(new Thread(_drawingManager));
		
		_threads.add(new Thread(_gameLogicExecutor));
		
		_threads.add(new Thread(new  LKosiakAI(_gameLogicExecutor, _mffp, "kosiacz3q_1")));
		
		//_players.add(new Thread(new  LKosiakAI(_gameLogicExecutor, _mffp, "kosiacz3q_2")));
		
		_threads.add(new Thread(new PlayerActor(_gameLogicExecutor, _mffp, _root, new SimpleLineDrawer(_root))));
		
		for ( Thread thread : _threads)
			thread.start();
		
		_gc.setFill(Color.GREEN);
	
		
		_animationTimer = new AnimationTimer() {
			
			@Override
			public void handle(long now)
			{
				_drawingManager.draw();
				
			}
		};
		
		/*
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
		
		_gameLogicExecutor.StartGame();
		_animationTimer.start();
	}

	@Override
	public void OnExit()
	{
		_gameLogicExecutor.StopGame();
		
		_animationTimer.stop();
		
		for ( Thread thread : _threads)
			thread.interrupt();
		
		
	}

	public String GetName()
	{
		return "GAME";
	}

	private Canvas _canvas;
	private GraphicsContext _gc;
	private javafx.stage.Stage _primaryStage;
	private Scene _gameScene;
	
	private DrawingManager _drawingManager;
	
	private GameLogicExecutor _gameLogicExecutor;
	private MapFromFileProvider _mffp;
	
	private ArrayList<Thread> _threads;
	private Pane _root;
	
	private AnimationTimer _animationTimer;
}
