package application.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.gluonhq.charm.glisten.control.AutoCompleteTextField;

import application.model.Credit;
import application.ui.model.CreateCreditService;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddCreditController implements Initializable {
	
	@FXML
	private Label requiredTlTimeFirstIntLabel;
	@FXML
	private Label requiredTlCnt03IntLabel;
	@FXML
	private Label requiredTlSatCntIntLabel;
	@FXML
	private Label requiredTlDel60CntIntLabel;
	@FXML
	private Label requiredTl75UtilCntIntLabel;
	@FXML
	private Label requiredTlBalHCPctNumLabel;
	@FXML
	private Label requiredTlSatPctNumLabel;
	@FXML
	private Label requiredTlDel3060CntIntLabel;
	@FXML
	private Label requiredTlOpen24PctNumLabel;
	
	@FXML
	private AutoCompleteTextField<Integer> tlTimeFirstTextField;
	@FXML
	private AutoCompleteTextField<Integer> tlCnt03TextField;
	@FXML
	private AutoCompleteTextField<Double> tlSatCntTextField;
	@FXML
	private AutoCompleteTextField<Integer> tlDel60CntTextField;
	@FXML
	private AutoCompleteTextField<Double> tl75UtilCntTextField;
	@FXML
	private AutoCompleteTextField<Double> tlBalHCPctTextField;
	@FXML
	private AutoCompleteTextField<Double> tlSatPctTextField;
	@FXML
	private AutoCompleteTextField<Integer> tlDel3060CntTextField;
	@FXML
	private AutoCompleteTextField<Double> tlOpen24PctTextField;
	
	@FXML
	private Button submitButton;
	
	@FXML
	void onSubmitButtonClicked(ActionEvent event) throws IOException {
		Integer tlTimeFirst = Integer.parseInt(tlTimeFirstTextField.getText());
		Integer tlCnt03 = Integer.parseInt(tlCnt03TextField.getText());
		Double tlSatCnt = Double.parseDouble(tlSatCntTextField.getText());
		Integer tlDel60Cnt = Integer.parseInt(tlDel60CntTextField.getText());
		Double tl75UtilCnt = Double.parseDouble(tl75UtilCntTextField.getText());
		Double tlBalHCPct = Double.parseDouble(tlBalHCPctTextField.getText());
		Double tlSatPct = Double.parseDouble(tlSatPctTextField.getText());
		Integer tlDel3060Cnt = Integer.parseInt(tlDel3060CntTextField.getText());
		Double tlOpen24Pct = Double.parseDouble(tlOpen24PctTextField.getText());
		
		Credit credit = new Credit(tlTimeFirst, tlCnt03, tlSatCnt,
				tlDel60Cnt, tl75UtilCnt, tlBalHCPct, tlSatPct,
				tlDel3060Cnt, tlOpen24Pct);
		runCreateCreditService(new CreateCreditService(credit));
		
		Stage stage = (Stage) submitButton.getScene().getWindow();
		
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tlTimeFirstTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlTimeFirstIntLabel, newValue, Integer.class);
		});
		tlCnt03TextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlCnt03IntLabel, newValue, Integer.class);
		});
		tlSatCntTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlSatCntIntLabel, newValue, Double.class);
		});
		tlDel60CntTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlDel60CntIntLabel, newValue, Integer.class);
		});
		tl75UtilCntTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTl75UtilCntIntLabel, newValue, Double.class);
		});
		tlBalHCPctTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlBalHCPctNumLabel, newValue, Double.class);
		});
		tlSatPctTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlSatPctNumLabel, newValue, Double.class);
		});
		tlDel3060CntTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlDel3060CntIntLabel, newValue, Integer.class);
		});
		tlOpen24PctTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			handleRequiredLabel(requiredTlOpen24PctNumLabel, newValue, Double.class);
		});
	}
	
	private void handleRequiredLabel(Label requiredLabel, 
			String value, Class clazz) {
		try {
			if (!checkPositiveInteger(value)) {
				throw new IllegalArgumentException();
			}
			if (clazz.getSimpleName().equals("Integer")) {
				Integer.parseInt(value);
			} else {
				Double.parseDouble(value);
			}
			requiredLabel.getStyleClass().remove("error");
			requiredLabel.setVisible(false);
			submitButton.setDisable(false);
		} catch (NumberFormatException nfe) {
			if (clazz.getSimpleName().equals("Integer")) {
				requiredLabel.setText("Integer required");
			} else {
				requiredLabel.setText("Required decimal number");
			}
			requiredLabel.getStyleClass().add("error");
			requiredLabel.setVisible(true);
			submitButton.setDisable(true);
		} catch (IllegalArgumentException iae) {
			requiredLabel.setText("Required positive number");
			requiredLabel.getStyleClass().add("error");
			requiredLabel.setVisible(true);
			submitButton.setDisable(true);
		}
	}
	
	private boolean checkPositiveInteger(String value) {
		if (Double.valueOf(value) >= 0) {
			return true;
		}
		
		return false;
	}
	
	private void runCreateCreditService(Service<String> service) 
			throws IOException {
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

}
