package StrgWar.bestresults;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
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
		Scene scene = new Scene(root, 300, 300);
		
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
					pane.addRow(pane.getChildren().size(), resetResults);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		pane.addRow(counter, resetResults);
        root.setTop(pane);

		dialogStage.setScene(scene);

		dialogStage.showAndWait();
	}
	
	private int SetPane(GridPane pane) {
		pane.getChildren().clear();
		
		Label label = new Label ("Best results");
		label.setFont(new Font("Cambria", 32));
		pane.addRow(0, label);
		
		List<Result> results = _management.GetResults();
		int counter = 1;
		for (Result result : results)
		{
			label = new Label(result.name + " " + result.gamesPlayed + " " + result.gamesWon);
			pane.addRow(counter, label);
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
