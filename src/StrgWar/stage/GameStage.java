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
	public GameStage(StageManager sm, javafx.stage.Stage primaryStage , String mapSource) throws IOException, SAXException, ParserConfigurationException
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
		 
		_drawingManager = new DrawingManager(_gc, _root);
		 
		_sm = sm;
	}

	@Override
	public void OnStart(String algorithm1, String algorithm2)
	{
		_primaryStage.setScene(_gameScene);
		_primaryStage.show();
		
		_mffp.GetReadOnlyMap().Nodes.forEach(node -> _drawingManager.Register( node));
		
		_threads.add(new Thread(_gameLogicExecutor));
		
		_threads.add(CreateAIThread(algorithm1, "player1")); //player1
		_threads.add(CreateAIThread(algorithm2, "player2")); //player2
		
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
		
		_gameLogicExecutor.StartGame();
		_animationTimer.start();
	}
	
	private Thread CreateAIThread(String algorithm, String playerName) 
	{	
		System.out.println("algorithm: " + algorithm);
		
		switch(algorithm) { //TODO: dodaæ case'y dla pozostalych sztucznych inteligencji
		case "player":
			return new Thread(new PlayerActor(_gameLogicExecutor, _mffp, _root, new SimpleLineDrawer(_root)));
		case "Kosiak":
			return new Thread(new LKosiakAI(_gameLogicExecutor, _mffp, playerName));
		default:
			return null;
		}		
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
	
	private StageManager _sm;
}
