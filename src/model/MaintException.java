package model;

public class MaintException extends Exception {
	private String errMsg;
	
	MaintException(String errMsg){
	
		this.errMsg = errMsg;
		
	}
	
	public String getMessage() {
		return errMsg;
	}
}
