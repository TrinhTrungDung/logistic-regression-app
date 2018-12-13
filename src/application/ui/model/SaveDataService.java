package application.ui.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import application.model.Credit;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SaveDataService extends Service<String> {
	
	private List<Credit> credits;
	
	public SaveDataService(List<Credit> credits) {
		setCredits(credits);
	}

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				StringBuffer response = new StringBuffer();
				
				for (Credit credit : credits) {
					byte[] encodedData =
							transformToJson(credit).getBytes(
									StandardCharsets.UTF_8);
					
					URL url = new URL("http://localhost:3000/credit/" + String.valueOf(credit.getID()));
					HttpURLConnection connection = 
							(HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setRequestMethod("PUT");
					connection.setRequestProperty("Content-Type",
							"application/json");
					
					OutputStream stream = connection.getOutputStream();
					stream.write(encodedData);
					
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
				}
				
				return response.toString();
			}
		};
	}

	public List<Credit> getCredits() {
		return credits;
	}

	public void setCredits(List<Credit> credits) {
		this.credits = credits;
	}
	
	private String transformToJson(Credit credit) {
		return "{"
				+ "\"TLTimeFirst\":" + String.valueOf(
						credit.getTlTimeFirst()) + ","
				+ "\"TLCnt03\":" + String.valueOf(
						credit.getTlCnt03()) + ","
				+ "\"TLSatCnt\":" + String.valueOf(
						credit.getTlSatCnt()) + ","
				+ "\"TLDel60Cnt\":" + String.valueOf(
						credit.getTlDel60Cnt()) + ","
				+ "\"TL75UtilCnt\":" + String.valueOf(
						credit.getTl75UtilCnt()) + ","
				+ "\"TLBalHCPct\":" + String.valueOf(
						credit.getTlBalHCPct()) + ","
				+ "\"TLSatPct\":" + String.valueOf(
						credit.getTlSatPct()) + ","
				+ "\"TLDel3060Cnt24\":" + String.valueOf(
						credit.getTlDel3060Cnt24()) + ","
				+ "\"TLOpen24Pct\":" + String.valueOf(
						credit.getTlOpen24Pct()) + 
				"}";
	}

}
