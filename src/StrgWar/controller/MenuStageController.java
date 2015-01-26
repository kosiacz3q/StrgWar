package StrgWar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class MenuStageController extends AbstractController {
	@FXML
	private ComboBox<String> algorithm1;
	private ObservableList<String> algorithm1Data = FXCollections
			.observableArrayList();

	@FXML
	private ComboBox<String> algorithm2;

	@FXML
	private ComboBox<String> map;
	private ObservableList<String> maps = FXCollections.observableArrayList();

	@FXML
	private Button btnStart;

	public MenuStageController() throws IOException {
		algorithm1Data.add("Kosiak");
		algorithm1Data.add("Szczepa�ski");
		algorithm1Data.add("Trawi�ski");
		algorithm1Data.add("Dmowska");

		String rootDir = new File(".").getCanonicalPath();
		File subDir = new File(rootDir, "/resources/maps");

		Files.walk(Paths.get(subDir.toString())).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				maps.add(filePath.getFileName().toString());
			}
		});
	}

	@FXML
	private void initialize() {		
		algorithm1.setItems(algorithm1Data);
		algorithm2.setItems(algorithm1Data);
		map.setItems(maps);

		btnStart.setOnAction((event) -> {
			String algorithm1Choice = algorithm1.getSelectionModel()
					.getSelectedItem();
			String algorithm2Choice = algorithm2.getSelectionModel()
					.getSelectedItem();
			String mapChoice = map.getSelectionModel().getSelectedItem();

			if (algorithm1Choice == null || algorithm2Choice == null
					|| mapChoice == null)
				System.out.println("brak danych");
			else {
				_stageSetter.SetStage("GAME");
			}
		});
	}
}