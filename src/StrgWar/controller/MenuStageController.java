package StrgWar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import StrgWar.bestresults.BestResults;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

public class MenuStageController extends AbstractController
{
	@FXML
	private ComboBox<String> algorithm1;

	private ObservableList<String> algorithm1Data = FXCollections.observableArrayList();

	private ObservableList<String> algorithm2Data = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> algorithm2;
	
	@FXML
	private ComboBox<String> colorsCombo1;

	@FXML
	private ComboBox<String> colorsCombo2;
	
	@FXML
	private ComboBox<String> map;
	
	private ObservableList<String> maps = FXCollections.observableArrayList();

	@FXML
	private Button btnStart;
	
	@FXML
	private Button btnResults;
	
	private ObservableList<String> colorsObservableList = FXCollections.observableArrayList();

	public MenuStageController() throws IOException
	{
		algorithm1Data.add("[AI] Kosiak");
		algorithm1Data.add("[AI] Dmowska");
		algorithm1Data.add("[AI] Szczepa雟ki");
		algorithm1Data.add("[AI] Trawi雟ki");
		algorithm1Data.add("Cz這wiek");
		
		algorithm2Data.add("[AI] Kosiak");
		algorithm2Data.add("[AI] Dmowska");
		algorithm2Data.add("[AI] Szczepa雟ki");
		algorithm2Data.add("[AI] Trawi雟ki");
		algorithm2Data.add("Cz這wiek");
		
		colorsObservableList.add("chocolate");
		colorsObservableList.add("blue");
		colorsObservableList.add("blueviolet");
		colorsObservableList.add("gold");

		String rootDir = new File(".").getCanonicalPath();
		File subDir = new File(rootDir, "/resources/maps");

		Files.walk(Paths.get(subDir.toString())).forEach(filePath ->
		{
			if (Files.isRegularFile(filePath))
			{
				maps.add(filePath.getFileName().toString());
			}
		});
	}

	@FXML
	private void initialize()
	{
		algorithm1.setItems(algorithm1Data);
		algorithm2.setItems(algorithm2Data);
		
		map.setItems(maps);
		map.setValue(maps.get(0));
		
		algorithm1.setValue(algorithm1Data.get(0));
		algorithm2.setValue(algorithm2Data.get(1));
		
		colorsCombo1.setItems(colorsObservableList);
		colorsCombo2.setItems(colorsObservableList);
		
		colorsCombo1.setValue(colorsObservableList.get(0));
		colorsCombo2.setValue(colorsObservableList.get(1));
		
		colorsCombo1.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	        @Override
	        public ListCell<String> call(ListView<String> list) {
	            return new ColorRectCell();
	        }
	    });
		
		colorsCombo2.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	        @Override
	        public ListCell<String> call(ListView<String> list) {
	            return new ColorRectCell();
	        }
	    });
		
		colorsCombo1.setButtonCell(new ColorRectTooltipCell());
		colorsCombo2.setButtonCell(new ColorRectTooltipCell());

		
		algorithm1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				if (oldValue != null && ("Cz這wiek").compareTo(oldValue) == 0)
				{
					algorithm2.getItems().add("Cz這wiek");
				}
				else if ( newValue != null && ("Cz這wiek").compareTo(newValue) == 0)
				{
					algorithm2.getItems().remove("Cz這wiek");
				}
			}
		});

		algorithm2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				if (oldValue != null && ("Cz這wiek").compareTo(oldValue) == 0)
				{
					algorithm1.getItems().add("Cz這wiek");
				}
				else if (newValue != null && ("Cz這wiek").compareTo(newValue) == 0)
				{
					algorithm1.getItems().remove("Cz這wiek");
				}
			}
		});

		btnStart.setOnAction((event) ->
		{
			String algorithm1Choice = algorithm1.getSelectionModel().getSelectedItem();
			String algorithm2Choice = algorithm2.getSelectionModel().getSelectedItem();
			String mapChoice = map.getSelectionModel().getSelectedItem();
			
			String player1Color = colorsCombo1.getSelectionModel().getSelectedItem();
			String player2Color = colorsCombo2.getSelectionModel().getSelectedItem();
			
			if (algorithm1Choice == null || algorithm2Choice == null || mapChoice == null)
				System.out.println("brak danych");
			else
			{
				_sharedDataHandler.SetPlayer1Name(algorithm1Choice);
				_sharedDataHandler.SetPlayer2Name(algorithm2Choice);
				
				_sharedDataHandler.SetPlayer1Color(player1Color);
				_sharedDataHandler.SetPlayer2Color(player2Color);
				
				File fXmlFile = new File("ERROR");
				try
				{
					String rootDir = new File(".").getCanonicalPath();
					File subDir = new File(rootDir, "/resources/maps");
					fXmlFile = new File(subDir, map.getSelectionModel().getSelectedItem());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				_sharedDataHandler.SetMapSource( fXmlFile.getAbsolutePath() );
				
				_stageSetter.SetStage("GAME");
			}
		});
		
		btnResults.setOnAction((event) ->
		{
			BestResults bestResults = new BestResults(GetStage());
			bestResults.DisplayPopup();
		});
	}
	
	static class ColorRectCell extends ListCell<String>{
		@Override
		public void updateItem(String item, boolean empty){
			super.updateItem(item, empty);
				Rectangle rect = new Rectangle(50,18);
				if(item != null){
				rect.setFill(Color.web(item));
				setGraphic(rect);
			}
		}
	}   
	
	static class ColorRectTooltipCell extends ColorRectCell {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			
			if (item != null) {
				Tooltip.install(this.getParent(), new Tooltip(item));
			}
		}
	}
}