package model;

public class RentException extends Exception {
	
	String errMsg;
	
	RentException(String errMsg){
		this.errMsg = errMsg;
	}
	
	public String getMessage() {
		return errMsg;
	}
}
