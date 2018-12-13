package application.ui.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GenerateDataService extends Service<String> {
	
	private Integer numOfGenData;

	public GenerateDataService(Integer numOfGenData) {
		super();
		this.setNumOfGenData(numOfGenData);
	}

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				StringBuffer response = new StringBuffer();
				
				byte[] encodedData =
						transformToJson().getBytes(StandardCharsets.UTF_8);
				
				URL url = new URL("http://localhost:3000/credit/random");
				HttpURLConnection connection = 
						(HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
				
				OutputStream stream = connection.getOutputStream();
				stream.write(encodedData);
				
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStream responseStream = new BufferedInputStream(
							connection.getInputStream());
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(responseStream));
					String inputLine;
					while ((inputLine = reader.readLine()) != null) {
						response.append(inputLine);
					}
					
					reader.close();
					
					Thread.sleep(1000);
				}
				
				connection.disconnect();
				
				return response.toString();
			}
		};
	}

	public Integer getNumOfGenData() {
		return numOfGenData;
	}

	public void setNumOfGenData(Integer numOfGenData) {
		this.numOfGenData = numOfGenData;
	}
	
	private String transformToJson() {
		return "{"
				+ "\"data\":{"
				+ "\"numOfData\":" + String.valueOf(this.numOfGenData)
				+ "}"
				+ "}";
	}

}
