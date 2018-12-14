package application.ui.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DeleteCreditService extends Service<String> {

	private Integer creditID;
	
	public DeleteCreditService(Integer creditID) {
		setCreditID(creditID);
	}

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				StringBuffer response = new StringBuffer();
				
				URL url = new URL("http://localhost:3000/credit/"
								+ String.valueOf(creditID));
				HttpURLConnection connection = 
						(HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("DELETE");
				connection.setRequestProperty("Content-Type",
						"application/json");
				
				if (connection.getResponseCode() ==
						HttpURLConnection.HTTP_OK) {
					InputStream responseStream = 
							new BufferedInputStream(
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

	public Integer getCreditID() {
		return creditID;
	}

	public void setCreditID(Integer creditID) {
		this.creditID = creditID;
	}

}
