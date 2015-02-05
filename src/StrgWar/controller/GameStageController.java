package StrgWar.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameStageController extends AbstractController
{

		@FXML
		private Button exitButton;
		
		
		@FXML
		private void initialize()
		{
			exitButton.setOnAction(event ->
			{
				_stageSetter.SetStage("MENU");
			});
		}
}
