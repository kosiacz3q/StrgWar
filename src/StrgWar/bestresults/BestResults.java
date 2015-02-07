package StrgWar.bestresults;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BestResults {
	Stage _primaryStage;

	public BestResults(Stage primaryStage) {
		_primaryStage = primaryStage;

		try {
			_management = new XmlResultsManagement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DisplayPopup() {
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Best results");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(_primaryStage);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 450, 300);
		
		GridPane pane = new GridPane();
		pane.setHgap(5);
		pane.setVgap(5);
		pane.setPadding(new Insets(10));

		int counter = SetPane(pane); 

		Button resetResults = new Button("Reset results");
		resetResults.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					_management.ResetResults();
					SetPane(pane);
					pane.add(resetResults, 0, pane.getChildren().size(), 1, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		pane.add(resetResults, 0, pane.getChildren().size(), 1, 1);
        root.setTop(pane);

		dialogStage.setScene(scene);

		dialogStage.showAndWait();
	}
	
	private int SetPane(GridPane pane) {
		pane.getChildren().clear();
		pane.getColumnConstraints().add(new ColumnConstraints(200)); 
		pane.getColumnConstraints().add(new ColumnConstraints(100));
		pane.getColumnConstraints().add(new ColumnConstraints(100));
		
		Label label = new Label("Best results");
		label.setFont(new Font("Cambria", 32));
		pane.add(label, 0, 0, 3, 1);
		pane.add(new Label(), 0, 1);
		
		label = new Label("Player");
		label.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
		pane.add(label, 0, 2);
		
		label = new Label("Games\nplayed");
		label.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
		pane.add(label, 1, 2);
		
		label = new Label("Games\nwon");
		label.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
		pane.add(label, 2, 2);
		
		List<Result> results = _management.GetResults();
		int counter = 3;
		for (Result result : results)
		{
			pane.add(new Label(result.name), 0, counter);
			pane.add(new Label(result.gamesPlayed), 1, counter);
			pane.add(new Label(result.gamesWon), 2, counter);
			counter++;
		}
		
		return counter;
	}
	
	public void SetGameResults(String winner, String loser)
	{
		try {
			_management.SetGameResults(winner, loser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private XmlResultsManagement _management;
}
