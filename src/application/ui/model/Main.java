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
//		try {
//			final TextField test = new TextField();
//			test.setOnAction(new EventHandler<ActionEvent>() {
//				@Override
//				public void handle(ActionEvent event) {
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								URL url = new URL("http://localhost:3000/logistic");
//								HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//								connection.setRequestMethod("GET");
//								connection.setRequestProperty("Content-Type", "application/json");
//								BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//								String inputLine;
//								StringBuffer response = new StringBuffer();
//								
//								while ((inputLine = reader.readLine()) != null) {
//									response.append(inputLine);
//								}
//								
//								reader.close();
//								
//								System.out.println(response.toString());
//							} catch (MalformedURLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							
//						}
//					}).start();
//				}
//			});
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
//			Scene scene = new Scene(new VBox(test),400,400);
//			
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
