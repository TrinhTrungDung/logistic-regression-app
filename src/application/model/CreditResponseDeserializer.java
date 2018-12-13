package application.model;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class CreditResponseDeserializer implements JsonDeserializer<CreditResponse> {

	@Override
	public CreditResponse deserialize(JsonElement json, Type arg1, 
			JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject rootJson = json.getAsJsonObject();
		CreditResponse response = new CreditResponse();
		
		if (rootJson.has("data") 
				&& rootJson.get("data").isJsonArray()) {
			JsonArray dataArray = rootJson.get("data")
					.getAsJsonArray();
			for (int i = 0; i < dataArray.size(); i++) {
				JsonObject jsonObject = dataArray.get(i)
						.getAsJsonObject();
				response.credits.add(parseCredit(jsonObject));
			}
		} else if (rootJson.has("data") 
				&& rootJson.get("data").isJsonObject()) {
			JsonObject dataObject = rootJson.get("data")
					.getAsJsonObject();
			response.credits.add(parseCredit(dataObject));
		}
		
		return response;
	}
	
	private Credit parseCredit(JsonObject object) {
		Integer ID = object.get("ID").getAsInt();
		Integer tlTimeFirst = object.get("TLTimeFirst").getAsInt();
		Integer tlCnt03 = object.get("TLCnt03").getAsInt();
		Double tlSatCnt = object.get("TLSatCnt").getAsDouble();
		Integer tlDel60Cnt = object.get("TLDel60Cnt").getAsInt();
		Double tl75UtilCnt = object.get("TL75UtilCnt").getAsDouble();
		Double tlBalHCPct = object.get("TLBalHCPct").getAsDouble();
		Double tlSatPct = object.get("TLSatPct").getAsDouble();
		Integer tlDel3060Cnt24 = object.get("TLDel3060Cnt24").getAsInt();
		Double tlOpen24Pct = object.get("TLOpen24Pct").getAsDouble();
		Integer target = null;
		if (object.has("TARGET")) {
			target = object.get("TARGET").getAsInt();
		}
		
		Credit credit = new Credit(ID, tlTimeFirst, tlCnt03, tlSatCnt,
				tlDel60Cnt, tl75UtilCnt, tlBalHCPct, tlSatPct, 
				tlDel3060Cnt24, tlOpen24Pct);
		credit.setTarget(target);
		
		return credit;
	}

}
