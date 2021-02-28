package aaa.bbb.ccc.entity;

import java.util.Date;

public class LoginLog {

	private Integer idLoginTry;
	
	private String loginId;
	
	private String sourceIP;
	
	private Date loginCreated;
	
	private Integer loginSuccess;

	
	
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
