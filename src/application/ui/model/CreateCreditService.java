package application.ui.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import application.model.Credit;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CreateCreditService extends Service<String> {
	
	private Credit credit;
	
	public CreateCreditService(Credit credit) {
		this.setCredit(credit);
	}

	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception {
				StringBuffer response = new StringBuffer();
				
				byte[] encodedData =
						transformToJson().getBytes(
								StandardCharsets.UTF_8);
				
				URL url = new URL("http://localhost:3000/credit");
				HttpURLConnection connection = 
						(HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
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
				
				return response.toString();
			}
		};
	}

	public Credit getCredit() {
		return credit;
	}

	public void setCredit(Credit credit) {
		this.credit = credit;
	}
	
	private String transformToJson() {
		return "{"
				+ "\"TLTimeFirst\":" + String.valueOf(
						this.credit.getTlTimeFirst()) + ","
				+ "\"TLCnt03\":" + String.valueOf(
						this.credit.getTlCnt03()) + ","
				+ "\"TLSatCnt\":" + String.valueOf(
						this.credit.getTlSatCnt()) + ","
				+ "\"TLDel60Cnt\":" + String.valueOf(
						this.credit.getTlDel60Cnt()) + ","
				+ "\"TL75UtilCnt\":" + String.valueOf(
						this.credit.getTl75UtilCnt()) + ","
				+ "\"TLBalHCPct\":" + String.valueOf(
						this.credit.getTlBalHCPct()) + ","
				+ "\"TLSatPct\":" + String.valueOf(
						this.credit.getTlSatPct()) + ","
				+ "\"TLDel3060Cnt24\":" + String.valueOf(
						this.credit.getTlDel3060Cnt24()) + ","
				+ "\"TLOpen24Pct\":" + String.valueOf(
						this.credit.getTlOpen24Pct()) + 
				"}";
	}

}
