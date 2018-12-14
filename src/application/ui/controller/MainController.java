package application.ui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ui.model.GenerateDataService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
	
	private Stage stage;
	private File file;
	private Service<String> service;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private TextField generateText;
	
	@FXML
	private Label requiredIntLabel;
	
	@FXML
	private Button generateButton;
	
	@FXML
	private Button importButton;
	
	@FXML
	private Button submitButton;
	
	@FXML
	void onImportMenuClicked(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		
		configureFileChooser(fileChooser);
		file = fileChooser.showOpenDialog(this.stage);
		
		if (file != null) {
			System.out.println(file.getName());
		}
	}
	
	@FXML
	void onExportMenuClicked(ActionEvent event) {
		System.out.println("Future implementation");
	}
	
	@FXML
	void onExitMenuClicked(ActionEvent event) {
		Platform.exit();
	}
	
	@FXML
	void onCreditViewClicked(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("../view/CreditView.fxml"));
		Parent creditViewParent = loader.load();
		
		Scene creditViewScene = new Scene(creditViewParent);
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(creditViewScene);
		stage.setTitle("Credits");
		stage.show();
	}
	
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
					requiredIntLabel.getStyleClass().remove("error");
					requiredIntLabel.setVisible(false);
					generateButton.setDisable(false);
				} catch (NumberFormatException nfe) {
					requiredIntLabel.getStyleClass().add("error");
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
		updatePane.getChildren().addAll(performRequestLabel, 
				progressBar);
		
		Stage updateTaskStage = new Stage(StageStyle.UTILITY);
		updateTaskStage.setScene(new Scene(updatePane));
		updateTaskStage.show();
		
		GenerateDataService generateDataService = 
				new GenerateDataService(numOfGenData);
		generateDataService.setOnSucceeded(event -> {
			updateTaskStage.hide();
		});
		
		updateTaskStage.show();
		
		generateDataService.start();
	}
	
	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Choose csv file");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("CSV", "*.csv"));
	}

}
