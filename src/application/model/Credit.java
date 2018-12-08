package application.model;

public class Credit {
	private Integer ID;
	private Integer TLTimeLast;
	private Integer	TLCnt03;
	private Integer	TLSatCnt;
	private Integer TLDel60Cnt;
	private Integer TL75UtilCnt;
	private Number TLBalHCPct;
	private Number TLSatPct;
	private Integer	TLDel3060Cn;
	private Number TLOpen24Pct;
	
	public Credit(Integer iD, Integer tLTimeLast, Integer tLCnt03, Integer tLSatCnt, Integer tLDel60Cnt,
			Integer tL75UtilCnt, Number tLBalHCPct, Number tLSatPct, Integer tLDel3060Cn, Number tLOpen24Pct) {
		super();
		ID = iD;
		TLTimeLast = tLTimeLast;
		TLCnt03 = tLCnt03;
		TLSatCnt = tLSatCnt;
		TLDel60Cnt = tLDel60Cnt;
		TL75UtilCnt = tL75UtilCnt;
		TLBalHCPct = tLBalHCPct;
		TLSatPct = tLSatPct;
		TLDel3060Cn = tLDel3060Cn;
		TLOpen24Pct = tLOpen24Pct;
	}

	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public Integer getTLTimeLast() {
		return TLTimeLast;
	}
	
	public void setTLTimeLast(Integer tLTimeLast) {
		TLTimeLast = tLTimeLast;
	}
	
	public Integer getTLCnt03() {
		return TLCnt03;
	}
	
	public void setTLCnt03(Integer tLCnt03) {
		TLCnt03 = tLCnt03;
	}
	
	public Integer getTLSatCnt() {
		return TLSatCnt;
	}
	
	public void setTLSatCnt(Integer tLSatCnt) {
		TLSatCnt = tLSatCnt;
	}
	
	public Integer getTLDel60Cnt() {
		return TLDel60Cnt;
	}
	
	public void setTLDel60Cnt(Integer tLDel60Cnt) {
		TLDel60Cnt = tLDel60Cnt;
	}
	
	public Integer getTL75UtilCnt() {
		return TL75UtilCnt;
	}
	
	public void setTL75UtilCnt(Integer tL75UtilCnt) {
		TL75UtilCnt = tL75UtilCnt;
	}
	
	public Number getTLBalHCPct() {
		return TLBalHCPct;
	}
	
	public void setTLBalHCPct(Number tLBalHCPct) {
		TLBalHCPct = tLBalHCPct;
	}
	
	public Number getTLSatPct() {
		return TLSatPct;
	}
	
	public void setTLSatPct(Number tLSatPct) {
		TLSatPct = tLSatPct;
	}
	
	public Integer getTLDel3060Cn() {
		return TLDel3060Cn;
	}
	
	public void setTLDel3060Cn(Integer tLDel3060Cn) {
		TLDel3060Cn = tLDel3060Cn;
	}
	
	public Number getTLOpen24Pct() {
		return TLOpen24Pct;
	}
	
	public void setTLOpen24Pct(Number tLOpen24Pct) {
		TLOpen24Pct = tLOpen24Pct;
	}
	
}
