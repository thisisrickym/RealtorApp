package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import model.RentalProperty;
import view.RentPane;
import model.DateTime;
import model.RentException;
import model.InvalidIdException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class FinalRentButtonListener implements EventHandler<ActionEvent> {
	
	private RentalProperty property;
	private ComboBox<Integer> rentDay, rentMonth, rentYear, returnDay, returnMonth, returnYear;
	private TextField custID;

	
	public FinalRentButtonListener (ComboBox<Integer> rentDay, ComboBox<Integer> rentMonth, ComboBox<Integer> rentYear,
			ComboBox<Integer> returnDay, ComboBox<Integer> returnMonth, ComboBox<Integer> returnYear, TextField custID, RentalProperty property) {
		
		this.property = property;
		this.custID = custID;
		this.rentDay = rentDay;
		this.rentMonth = rentMonth;
		this.rentYear = rentYear;
		this.returnDay = returnDay;
		this.returnMonth = returnMonth;
		this.returnYear = returnYear;
		
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		DateTime rentDate = new DateTime(rentDay.getValue(), rentMonth.getValue(), rentYear.getValue());
		DateTime returnDate = new DateTime(returnDay.getValue(), returnMonth.getValue(), returnYear.getValue());
		
		String custIDString = custID.getText();
		int numOfRentDays = DateTime.diffDays(returnDate, rentDate);
		
		try {
			property.rent(custIDString, rentDate, numOfRentDays);
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("RENT SUCCESSFUL");
		    
		    alert.setContentText(property.getFormattedAddress() + " is now booked from " + rentDate.getFormattedDate()
		    					+ " to " + returnDate.getFormattedDate());
		    alert.showAndWait();
		    RentPane.close();
		} catch (RentException e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("RENT FAILED");
			failedAlert.setContentText(property.getFormattedAddress() + " could not be booked, because " + e.getMessage());
			failedAlert.showAndWait();
		} catch (InvalidIdException e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("INVALID CUSTOMER ID");
			failedAlert.setContentText("Customer ID must start with CUS and contain only integers after.");
			failedAlert.showAndWait();
		}
		
	
	}
}

