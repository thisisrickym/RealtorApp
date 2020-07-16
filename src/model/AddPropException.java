package model;

public class AddPropException extends Exception {
	
	String errMsg;
	
	public AddPropException(String errMsg){
		this.errMsg = errMsg;
	}
	
	public String getMessage() {
		return errMsg;
	}

}
