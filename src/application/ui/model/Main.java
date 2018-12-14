package application.ui.model;
	
import java.io.IOException;

import application.ui.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Stage primaryStage;
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainView.fxml"));
		Parent root = loader.load();
		MainController mainController = loader.getController();
		mainController.setStage(primaryStage);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass()
				.getResource("../view/application.css").toExternalForm());
		
		this.primaryStage.setTitle("Main");
		this.primaryStage.setScene(scene);
		
		this.primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
