package application.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressController implements Initializable {
	
	@FXML
	private Label performRequestLabel;
	
	@FXML
	private ProgressBar progressBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public Label getPerformRequestLabel() {
		return performRequestLabel;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

}
