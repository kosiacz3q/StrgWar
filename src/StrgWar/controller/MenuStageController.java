package StrgWar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class MenuStageController extends AbstractController
{
	@FXML
	private ComboBox<String> algorithm1;

	private ObservableList<String> algorithm1Data = FXCollections.observableArrayList();

	private ObservableList<String> algorithm2Data = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> algorithm2;

	@FXML
	private ComboBox<String> map;
	private ObservableList<String> maps = FXCollections.observableArrayList();

	@FXML
	private Button btnStart;

	public MenuStageController() throws IOException
	{
		algorithm1Data.add("[AI] Kosiak");
		algorithm1Data.add("[AI] Szczepa雟ki");
		algorithm1Data.add("[AI] Trawi雟ki");
		algorithm1Data.add("[AI] Dmowska");
		algorithm1Data.add("Cz這wiek");

		algorithm2Data.add("[AI] Kosiak");
		algorithm2Data.add("[AI] Szczepa雟ki");
		algorithm2Data.add("[AI] Trawi雟ki");
		algorithm2Data.add("[AI] Dmowska");
		algorithm2Data.add("Cz這wiek");

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

			if (algorithm1Choice == null || algorithm2Choice == null || mapChoice == null)
				System.out.println("brak danych");
			else
			{
				_algorithmSetter.SetAlgorithms(algorithm1Choice, algorithm2Choice);
				_stageSetter.SetStage("GAME");
			}
		});
	}
}