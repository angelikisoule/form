package gr.media24.mSites.core.utils;

import java.util.Date;

/**
 * @author npapadopoulos
 */
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;
	private Date errorDate;
	
	public CustomException() {
		
	}
	
	public CustomException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public CustomException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public CustomException(String errorCode, String errorMessage, Date errorDate) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDate = errorDate;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public Date getErrorDate() {
		return errorDate;
	}
	
	public void setErrorDate(Date errorDate) {
		this.errorDate = errorDate;
	}
}
