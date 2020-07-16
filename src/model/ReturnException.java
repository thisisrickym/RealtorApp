package model;

public class ReturnException extends Exception {
	
	private String errMsg;
	
	ReturnException(String errMsg){
	
		this.errMsg = errMsg;
		
	}
	
	public String getMessage() {
		return errMsg;
	}
}
