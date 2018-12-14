package application.ui.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GetAllDataService extends Service<String> {

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				StringBuffer response = new StringBuffer();
				
				try {
					URL url = new URL("http://localhost:3000/credit");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setRequestProperty("Content-Type", "application/json");
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String inputLine;
					response = new StringBuffer();
					
					while ((inputLine = reader.readLine()) != null) {
						response.append(inputLine);
					}
					
					reader.close();
					
					Thread.sleep(1000);
					
					connection.disconnect();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				return response.toString();
			}
		};
	}

}
