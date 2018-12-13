package application.ui.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.model.Credit;
import application.model.CreditResponse;
import application.model.CreditResponseDeserializer;
import application.ui.model.GenerateDataService;
import application.ui.model.GetAllDataService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
//		runTask(numOfGenData);
		service = new GetAllDataService();
		runService(service);
	}
	
	@FXML
	void onSubmitButtonClicked(ActionEvent event) throws IOException {
//		List<Credit> credits = new ArrayList<>();
		
//		MappingIterator<Credit> creditIterator = new CsvMapper()
//				.readerWithTypedSchemaFor(Credit.class).readValues(file);
//		System.out.println(creditIterator.readAll());
		
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("../view/CreditView.fxml"));
		Parent creditViewParent = loader.load();
		
		Scene creditViewScene = new Scene(creditViewParent);
		Stage stage = (Stage) menuBar.getScene().getWindow();
		stage.setScene(creditViewScene);
		stage.setTitle("Credits");
		stage.show();
		
//		if (file.exists()) {
//			Pattern pattern = Pattern.compile(",");
//			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//				credits = reader.lines().skip(1).map(line -> {
//					String[] attributes = pattern.split(line);
//					System.out.println(attributes.length);
//					Integer id = parseIntAttribute(attributes[1]);
//					Integer tlTimeLast = parseIntAttribute(attributes[9]);
//					Integer tlCnt03 = parseIntAttribute(attributes[10]);
//					Integer tlSatCnt = parseIntAttribute(attributes[16]);
//					Integer tlDel60Cnt = parseIntAttribute(attributes[17]);
//					Integer tl75UtilCnt = parseIntAttribute(attributes[19]);
//					Number tlBalHCPct = parseNumAttribute(attributes[21]);
//					Number tlSatPct = parseNumAttribute(attributes[22]);
//					Integer tlDel3060Cnt = parseIntAttribute(attributes[23]);
//					Number tlOpen24Pct = parseNumAttribute(attributes[29]);
//					
//					return new Credit(id, tlTimeLast, tlCnt03, tlSatCnt,
//							tlDel60Cnt,tl75UtilCnt, tlBalHCPct,tlSatPct,
//							tlDel3060Cnt, tlOpen24Pct);
//				}).collect(Collectors.toList());
//			}
//		}
//		
////		System.out.println(credits);
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
	
//	private Stage createProgressStage() {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProgressView.fxml"));
//		Parent progressViewParent = loader.load();
//		
//		Scene progressViewScene = new Scene(progressViewParent);
//		Stage updateTaskStage = new Stage(StageStyle.UTILITY);
//		updateTaskStage.setScene(progressViewScene);
//		
//		return updateTaskStage;
//	}
	
	private void runService(Service<String> service) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("../view/ProgressVIew.fxml"));
		Parent progressViewParent = loader.load();
		ProgressController controller = loader.getController();
		
		loader = new FXMLLoader(
				getClass().getResource("../view/ProgressVIew.fxml"));
		Parent creditViewParent = loader.load();
		CreditController creditController = loader.getController();
		
		Scene progressViewScene = new Scene(progressViewParent);
		Stage updateTaskStage = new Stage(StageStyle.UTILITY);
		updateTaskStage.setScene(progressViewScene);
		updateTaskStage.show();
		
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				updateTaskStage.hide();
			}
		});
		
		controller.getPerformRequestLabel().textProperty().bind(
				service.messageProperty());
		controller.getProgressBar().progressProperty().bind(
				service.progressProperty());
		
		updateTaskStage.show();
		
		service.start();
	}
	
//	private void runTask(Integer numOfGenData) {
//		final double width = 300;
//		Label performRequestLabel = new Label("Running tasks....");
//		performRequestLabel.setPrefWidth(width);
//		ProgressBar progressBar = new ProgressBar();
//		progressBar.setPrefWidth(width);
//		
//		VBox updatePane = new VBox();
//		updatePane.setPadding(new Insets(10));
//		updatePane.setSpacing(5);
//		updatePane.getChildren().addAll(performRequestLabel, progressBar);
//		
//		Stage updateTaskStage = new Stage(StageStyle.UTILITY);
//		updateTaskStage.setScene(new Scene(updatePane));
//		updateTaskStage.show();
//		
//		GetAllDataService service = new GetAllDataService();
//		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//			@Override
//			public void handle(WorkerStateEvent event) {
//				System.out.println(event.getSource().getValue());
//				GsonBuilder builder = new GsonBuilder();
//				builder.registerTypeAdapter(CreditResponse.class, 
//						new CreditResponseDeserializer());
//				Gson gson = builder.create();
//				
//				CreditResponse creditResponse = gson.fromJson(
//						event.getSource().getValue().toString(),
//						CreditResponse.class);
//				System.out.println(creditResponse.credits);
//				updateTaskStage.hide();
//			}
//		});
//		
//		progressBar.progressProperty().bind(service.progressProperty());
//		performRequestLabel.textProperty().bind(service.messageProperty());
//		
//		updateTaskStage.show();
//		
//		GenerateDataService generateDataService = new GenerateDataService(numOfGenData);
//		generateDataService.setOnSucceeded(event -> {
//			updateTaskStage.hide();
//			System.out.println(event.getSource().getValue());
//		});
//		generateDataService.start();
//		
//		service.start();
//	}
	
	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Choose csv file");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("CSV", "*.csv"));
	}
	
	private Integer parseIntAttribute(String attr) {
		Integer intAttr;
		
		try {
			intAttr = Integer.parseInt(attr);
		} catch (NumberFormatException nfe) {
			intAttr = 0;
		}
		
		return intAttr;
	}
	
	private Number parseNumAttribute(String attr) {
		Number numAttr;
		
		try {
			numAttr = Double.parseDouble(attr);
		} catch (NumberFormatException nef) {
			numAttr = 0;
		}
		
		return numAttr;
	}

}
