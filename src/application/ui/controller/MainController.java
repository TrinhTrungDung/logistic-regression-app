package application.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ui.model.GenerateDataService;
import application.ui.model.GetAllDataService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private TextField generateText;
	
	@FXML
	private Label requiredIntLabel;
	
	@FXML
	private Button generateButton;
	
//	@FXML
//	private Label performRequestLabel;
	
	@FXML
	private Button submitButton;
	
	@FXML
	void onGenerateButtonClicked(ActionEvent event) throws IOException {
		Integer numOfGenData = Integer.parseInt(generateText.getText().toString());
		runTask(numOfGenData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		generateText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					Integer.parseInt(newValue);
					generateText.getStyleClass().remove("error");
					requiredIntLabel.setVisible(false);
					generateButton.setDisable(false);
				} catch (NumberFormatException nfe) {
					generateText.getStyleClass().add("error");
					requiredIntLabel.setVisible(true);
					generateButton.setDisable(true);
				}
			}
		});
	}
	
	private void runTask(Integer numOfGenData) {
		final double width = 300;
		Label performRequestLabel = new Label("Running tasks....");
		performRequestLabel.setPrefWidth(width);
		ProgressBar progressBar = new ProgressBar();
		progressBar.setPrefWidth(width);
		
		VBox updatePane = new VBox();
		updatePane.setPadding(new Insets(10));
		updatePane.setSpacing(5);
		updatePane.getChildren().addAll(performRequestLabel, progressBar);
		
		Stage updateTaskStage = new Stage(StageStyle.UTILITY);
		updateTaskStage.setScene(new Scene(updatePane));
		updateTaskStage.show();
		
		GetAllDataService service = new GetAllDataService();
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				updateTaskStage.hide();
			}
		});
		
		progressBar.progressProperty().bind(service.progressProperty());
		performRequestLabel.textProperty().bind(service.messageProperty());
		
		updateTaskStage.show();
		
		GenerateDataService generateDataService = new GenerateDataService(numOfGenData);
		generateDataService.setOnSucceeded(event -> {
			updateTaskStage.hide();
			System.out.println(event.getSource().getValue());
		});
		generateDataService.start();
		
		service.start();
	}

}
