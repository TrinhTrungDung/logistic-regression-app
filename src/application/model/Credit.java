package application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"ID", "TLTimeFirst", "TLCnt03", "TLSatCnt",
		"TLDel60Cnt", "TL75UtilCnt", "TLBalHCPct", "TLSatPct", 
		"TLDel3060Cnt24", "TLOpen24Pct", "TARGET"})
public class Credit {
	
	@SerializedName("ID")
	@JsonProperty("ID")
	private Integer ID;
	@SerializedName("TLTimeFirst")
	@JsonProperty("TLTimeFirst")
	private Integer tlTimeFirst;
	@SerializedName("TLCnt03")
	@JsonProperty("TLCnt03")
	private Integer	tlCnt03;
	@SerializedName("TLSatCnt")
	@JsonProperty("TLSatCnt")
	private Double tlSatCnt;
	@SerializedName("TLDel60Cnt")
	@JsonProperty("TLDel60Cnt")
	private Integer tlDel60Cnt;
	@SerializedName("TL75UtilCnt")
	@JsonProperty("TL75UtilCnt")
	private Double tl75UtilCnt;
	@SerializedName("TLBalHCPct")
	@JsonProperty("TLBalHCPct")
	private Double tlBalHCPct;
	@SerializedName("TLSatPct")
	@JsonProperty("TLSatPct")
	private Double tlSatPct;
	@SerializedName("TLDel3060Cnt24")
	@JsonProperty("TLDel3060Cnt24")
	private Integer	tlDel3060Cnt24;
	@SerializedName("TLOpen24Pct")
	@JsonProperty("TLOpen24Pct")
	private Double tlOpen24Pct;
	@SerializedName("TARGET")
	@JsonProperty("TARGET")
	private Integer target;
	
	public Credit() {}

	public Credit(Integer tlTimeFirst, Integer tlCnt03, 
			Double tlSatCnt, Integer tlDel60Cnt, Double tl75UtilCnt,
			Double tlBalHCPct, Double tlSatPct,
			Integer tlDel3060Cnt24, Double tlOpen24Pct) {
		this.tlTimeFirst = tlTimeFirst;
		this.tlCnt03 = tlCnt03;
		this.tlSatCnt = tlSatCnt;
		this.tlDel60Cnt = tlDel60Cnt;
		this.tl75UtilCnt = tl75UtilCnt;
		this.tlBalHCPct = tlBalHCPct;
		this.tlSatPct = tlSatPct;
		this.tlDel3060Cnt24 = tlDel3060Cnt24;
		this.tlOpen24Pct = tlOpen24Pct;
	}
	
	public Credit(Integer ID, Integer tlTimeFirst, Integer tlCnt03, 
			Double tlSatCnt, Integer tlDel60Cnt, Double tl75UtilCnt,
			Double tlBalHCPct, Double tlSatPct, 
			Integer tlDel3060Cnt24, Double tlOpen24Pct) {
		this.ID = ID;
		this.tlTimeFirst = tlTimeFirst;
		this.tlCnt03 = tlCnt03;
		this.tlSatCnt = tlSatCnt;
		this.tlDel60Cnt = tlDel60Cnt;
		this.tl75UtilCnt = tl75UtilCnt;
		this.tlBalHCPct = tlBalHCPct;
		this.tlSatPct = tlSatPct;
		this.tlDel3060Cnt24 = tlDel3060Cnt24;
		this.tlOpen24Pct = tlOpen24Pct;
	}
	
	public Credit(Integer ID, Integer tlTimeFirst, Integer tlCnt03, 
			Double tlSatCnt, Integer tlDel60Cnt, Double tl75UtilCnt,
			Double tlBalHCPct, Double tlSatPct, 
			Integer tlDel3060Cnt24, Double tlOpen24Pct, 
			Integer target) {
		this.ID = ID;
		this.tlTimeFirst = tlTimeFirst;
		this.tlCnt03 = tlCnt03;
		this.tlSatCnt = tlSatCnt;
		this.tlDel60Cnt = tlDel60Cnt;
		this.tl75UtilCnt = tl75UtilCnt;
		this.tlBalHCPct = tlBalHCPct;
		this.tlSatPct = tlSatPct;
		this.tlDel3060Cnt24 = tlDel3060Cnt24;
		this.tlOpen24Pct = tlOpen24Pct;
		this.target = target;
	}

	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer ID) {
		this.ID = ID;
	}
	
	public Integer getTlTimeFirst() {
		return tlTimeFirst;
	}
	
	public void setTlTimeFirst(Integer tlTimeFirst) {
		this.tlTimeFirst = tlTimeFirst;
	}
	
	public Integer getTlCnt03() {
		return tlCnt03;
	}
	
	public void setTlCnt03(Integer tlCnt03) {
		this.tlCnt03 = tlCnt03;
	}
	
	public Double getTlSatCnt() {
		return tlSatCnt;
	}
	
	public void setTlSatCnt(Double tlSatCnt) {
		this.tlSatCnt = tlSatCnt;
	}
	
	public Integer getTlDel60Cnt() {
		return tlDel60Cnt;
	}
	
	public void setTlDel60Cnt(Integer tlDel60Cnt) {
		this.tlDel60Cnt = tlDel60Cnt;
	}
	
	public Double getTl75UtilCnt() {
		return tl75UtilCnt;
	}
	
	public void setTl75UtilCnt(Double tl75UtilCnt) {
		this.tl75UtilCnt = tl75UtilCnt;
	}
	
	public Double getTlBalHCPct() {
		return tlBalHCPct;
	}
	
	public void setTlBalHCPct(Double tlBalHCPct) {
		this.tlBalHCPct = tlBalHCPct;
	}
	
	public Double getTlSatPct() {
		return tlSatPct;
	}
	
	public void setTlSatPct(Double tlSatPct) {
		this.tlSatPct = tlSatPct;
	}
	
	public Integer getTlDel3060Cnt24() {
		return tlDel3060Cnt24;
	}
	
	public void setTlDel3060Cnt24(Integer tlDel3060Cnt24) {
		this.tlDel3060Cnt24 = tlDel3060Cnt24;
	}
	
	public Double getTlOpen24Pct() {
		return tlOpen24Pct;
	}
	
	public void setTlOpen24Pct(Double tlOpen24Pct) {
		this.tlOpen24Pct = tlOpen24Pct;
	}
	
	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}
	
}
