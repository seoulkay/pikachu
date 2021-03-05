package aaa.bbb.ccc.entity;

import java.util.Date;

public class LoginLog {

	private Integer idLoginTry;
	
	private String loginId;
	
	private String sourceIP;
	
	private Date loginCreated;
	
	private Integer loginSuccess;

	private String countryCode;
	
	private String date;
	
	private String month;
	
	private Integer successTotal;
	
	private Integer total;
	
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getSuccessTotal() {
		return successTotal;
	}

	public void setSuccessTotal(Integer successTotal) {
		this.successTotal = successTotal;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getIdLoginTry() {
		return idLoginTry;
	}

	public void setIdLoginTry(Integer idLoginTry) {
		this.idLoginTry = idLoginTry;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public Date getLoginCreated() {
		return loginCreated;
	}

	public void setLoginCreated(Date loginCreated) {
		this.loginCreated = loginCreated;
	}

	public Integer getLoginSuccess() {
		return loginSuccess;
	}

	public void setLoginSuccess(Integer loginSuccess) {
		this.loginSuccess = loginSuccess;
	}

	
	
	

	

	
	
	
}
