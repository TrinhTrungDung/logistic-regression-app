package application.ui.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.model.Credit;
import application.model.CreditResponse;
import application.model.CreditResponseDeserializer;
import application.model.IntegerConverter;
import application.model.DoubleConverter;
import application.ui.model.GetAllDataService;
import application.ui.model.SaveDataService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CreditController implements Initializable {
	
	private Service<String> service;
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private TableView<Credit> creditTable;
	
	@FXML
	private TableColumn<Credit, Integer> idColumn;
	@FXML
	private TableColumn<Credit, Integer> tlTimeFirstColumn;
	@FXML
	private TableColumn<Credit, Integer> tlCnt03Column;
	@FXML
	private TableColumn<Credit, Double> tlSatCntColumn;
	@FXML
	private TableColumn<Credit, Integer> tlDel60CntColumn;
	@FXML
	private TableColumn<Credit, Double> tl75UtilCntColumn;
	@FXML
	private TableColumn<Credit, Double> tlBalHCPctColumn;
	@FXML
	private TableColumn<Credit, Double> tlSatPctColumn;
	@FXML
	private TableColumn<Credit, Integer> tlDel3060Cnt24Column;
	@FXML
	private TableColumn<Credit, Double> tlOpen24PctColumn;
	@FXML
	private TableColumn<Credit, Integer> targetColumn;
	
	@FXML
	private Button addButton;
	@FXML
	private Button refreshButton;
	@FXML
	private Button validateButton;
	
	@FXML
	void onImportMenuClicked(ActionEvent event) throws IOException {
//		FileChooser fileChooser = new FileChooser();
//		
//		configureFileChooser(fileChooser);
//		file = fileChooser.showOpenDialog(this.stage);
//		
//		if (file != null) {
//			System.out.println(file.getName());
//		}
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
	void onAddButtonClicked(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("../view/AddCreditView.fxml"));
		Parent addCreditParent = loader.load();
		
		Scene addCreditScene = new Scene(addCreditParent);
		addCreditScene.getStylesheets().add(getClass()
				.getResource("../view/application.css")
				.toExternalForm());
		Stage addCreditStage = new Stage();
		addCreditStage.initModality(Modality.APPLICATION_MODAL);
		addCreditStage.setTitle("Add new credit");
		addCreditStage.setScene(addCreditScene);
		
		addCreditStage.showAndWait();
	}
	
	@FXML
	void onSaveButtonClicked(ActionEvent event) {
		SaveDataService saveDataService =
				new SaveDataService(creditTable.getItems());
		saveDataService.start();
	}
	
	@FXML
	void onDeleteButtonClicked(ActionEvent event) {
		
	}
	
	@FXML
	void onRefreshButtonClicked(ActionEvent event) {
		refreshTable();
	}
	
	@FXML
	void onValidateButtonClicked(ActionEvent event) 
			throws IOException {
		File file = new File("scripts/credit_DB.csv");
		
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(Credit.class).withUseHeader(true);
		ObjectReader objectReader = mapper.readerFor(Credit.class).with(schema);
		
		try (Reader reader = new FileReader(file)) {
			MappingIterator iterator = objectReader.readValues(reader);
			
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		}
		
		file = new File("scripts/model.csv");
		
		List<Double> coefficients = readLogisticModel(file);
		
		creditTable.getItems().forEach(credit -> {
			Double result = coefficients.get(0) +
					credit.getTlTimeFirst() * coefficients.get(1) +
					credit.getTlCnt03() * coefficients.get(2) +
					credit.getTlSatCnt() * coefficients.get(3) +
					credit.getTlDel60Cnt() * coefficients.get(4) +
					credit.getTl75UtilCnt() * coefficients.get(5) +
					credit.getTlBalHCPct() * coefficients.get(6) +
					credit.getTlSatPct() * coefficients.get(7) +
					credit.getTlDel3060Cnt24() * coefficients.get(8) +
					credit.getTlOpen24Pct() * coefficients.get(9);
			result = Math.exp(result) / (1 + Math.exp(result));
			result = Double.valueOf((result > 0.5) ? 1 : 0);
			Integer target = (int) Math.round(result);
			credit.setTarget(target);
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initController();
	}
	
	private List<Double> readLogisticModel(File file)
			throws FileNotFoundException {
		List<Double> coefficients = new ArrayList<>();
		
		if (file.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			reader.lines().forEach(line -> {
				String[] splittedLine = line.split(",");
				try {
					Double coef = Double.parseDouble(splittedLine[2]);
					coefficients.add(coef);
				} catch (NumberFormatException e) {
					System.out.println("Skipped line");
				}
			});
		}
		
		return coefficients;
	}
	
	private void initController() {
		refreshTable();
		
		setupIntColumnProperties(creditTable, tlTimeFirstColumn, 
				"TLTimeFirst");
		setupIntColumnProperties(creditTable, tlCnt03Column, 
				"TLCnt03");
		setupPctColumnProperties(creditTable, tlSatCntColumn,
				"TLSatCnt");
		setupIntColumnProperties(creditTable, tlDel60CntColumn,
				"TLDel60Cnt");
		setupPctColumnProperties(creditTable, tl75UtilCntColumn,
				"TL75UtilCnt");
		setupPctColumnProperties(creditTable, tlBalHCPctColumn,
				"TLBalHCPct");
		setupPctColumnProperties(creditTable, tlSatPctColumn,
				"TLSatPct");
		setupIntColumnProperties(creditTable, tlDel3060Cnt24Column,
				"TLDel3060Cnt");
		setupPctColumnProperties(creditTable, tlOpen24PctColumn,
				"TLOpen24Pct");
	}
	
	private void refreshTable() {
		service = new GetAllDataService();
		
		try {
			runService(service, creditTable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runService(Service<String> service, 
			TableView<Credit> creditTable) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("../view/ProgressView.fxml"));
		Parent progressViewParent = loader.load();
		ProgressController controller = loader.getController();
		
		Scene progressViewScene = new Scene(progressViewParent);
		Stage updateTaskStage = new Stage(StageStyle.UTILITY);
		updateTaskStage.setScene(progressViewScene);
		updateTaskStage.show();
		
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				GsonBuilder builder = new GsonBuilder();
				builder.registerTypeAdapter(CreditResponse.class, 
						new CreditResponseDeserializer());
				Gson gson = builder.create();
				
				CreditResponse creditResponse = gson.fromJson(
						event.getSource().getValue().toString(),
						CreditResponse.class);
				creditTable.getItems().setAll(
						FXCollections.observableArrayList(
								creditResponse.credits));
				updateTaskStage.hide();
			}
		});
		
		controller.getPerformRequestLabel().textProperty().bind(
				service.messageProperty());
		controller.getProgressBar().progressProperty().bind(
				service.progressProperty());
		
		updateTaskStage.show();
		
		service.restart();
	}
	
	private void setupIntColumnProperties(TableView<Credit> table, 
			TableColumn<Credit, Integer> column, String methodName) {
		column.setCellFactory(TextFieldTableCell
				.<Credit, Integer>forTableColumn(
						new IntegerConverter()));
		column.setOnEditCommit(event -> {
			Credit credit = table.getSelectionModel()
					.getSelectedItem();
			selectIntColumn(credit, event.getOldValue(), 
					event.getNewValue(), methodName);
		});
	}
	
	private void setupPctColumnProperties(TableView<Credit> table, 
			TableColumn<Credit, Double> column, String methodName) {
		column.setCellFactory(TextFieldTableCell
				.<Credit, Double>forTableColumn(
						new DoubleConverter()));
		column.setOnEditCommit(event -> {
			Credit credit = table.getSelectionModel()
					.getSelectedItem();
			selectPctColumn(credit, event.getOldValue(),
					event.getNewValue(), methodName);
		});
	}
	
	private void selectIntColumn(Credit credit, Integer oldInt, 
			Integer newInt, String methodName) {
		switch (methodName) {
			case "TLTimeFirst":
				changeTlTimeFirst(credit, oldInt, newInt);
				break;
			case "TLCnt03":
				changeTlCnt03(credit, oldInt, newInt);
				break;
			case "TLDel60Cnt":
				changeTlDel60Cnt(credit, oldInt, newInt);
				break;
			case "TLDel3060Cnt":
				changeTlDel3060Cnt(credit, oldInt, newInt);
				break;
			default:
				break;
		}
	}
	
	private void selectPctColumn(Credit credit, Double oldPct, 
			Double newPct, String methodName) {
		switch (methodName) {
			case "TLSatCnt":
				changeTlSatCnt(credit, oldPct, newPct);
				break;
			case "TL75UtilCnt":
				changeTl75UtilCnt(credit, oldPct, newPct);
				break;
			case "TLBalHCPct":
				changeTlBalHCPct(credit, oldPct, newPct);
				break;
			case "TLSatPct":
				changeTlSatPct(credit, oldPct, newPct);
				break;
			case "TLOpen24Pct":
				changeTlOpen24Pct(credit, oldPct, newPct);
				break;
			default:
				break;
		}
	}
	
	private void changeTlTimeFirst(Credit credit, 
			Integer oldInt, Integer newInt) {
		if (newInt < 0) {
			credit.setTlTimeFirst(oldInt);
		} else {
			credit.setTlTimeFirst(newInt);
		}
	}
	
	private void changeTlCnt03(Credit credit, 
			Integer oldInt, Integer newInt) {
		if (newInt < 0) {
			credit.setTlCnt03(oldInt);
		} else {
			credit.setTlCnt03(newInt);
		}
	}
	
	private void changeTlSatCnt(Credit credit, 
			Double oldInt, Double newInt) {
		if (newInt < 0) {
			credit.setTlSatCnt(oldInt);
		} else {
			credit.setTlSatCnt(newInt);
		}
	}
	
	private void changeTlDel60Cnt(Credit credit, 
			Integer oldInt, Integer newInt) {
		if (newInt < 0) {
			credit.setTlDel60Cnt(oldInt);
		} else {
			credit.setTlDel60Cnt(newInt);
		}
	}
	
	private void changeTl75UtilCnt(Credit credit, 
			Double oldInt, Double newInt) {
		if (newInt < 0) {
			credit.setTl75UtilCnt(oldInt);
		} else {
			credit.setTl75UtilCnt(newInt);
		}
	}
	
	private void changeTlDel3060Cnt(Credit credit, 
			Integer oldInt, Integer newInt) {
		if (newInt < 0) {
			credit.setTlDel3060Cnt24(oldInt);
		} else {
			credit.setTlDel3060Cnt24(newInt);
		}
	}
	
	private void changeTlBalHCPct(Credit credit, 
			Double oldPct, Double newPct) {
		if (newPct < 0) {
			credit.setTlBalHCPct(oldPct);
		} else {
			credit.setTlBalHCPct(newPct);
		}
	}
	
	private void changeTlSatPct(Credit credit, 
			Double oldPct, Double newPct) {
		if (newPct < 0) {
			credit.setTlSatPct(oldPct);
		} else {
			credit.setTlSatPct(newPct);
		}
	}
	
	private void changeTlOpen24Pct(Credit credit, 
			Double oldPct, Double newPct) {
		if (newPct < 0) {
			credit.setTlOpen24Pct(oldPct);
		} else {
			credit.setTlOpen24Pct(newPct);
		}
	}

}
