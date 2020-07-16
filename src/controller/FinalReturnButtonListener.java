package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import model.DateTime;
import model.ReturnException;
import model.RentalProperty;
import view.ReturnPane;

public class FinalReturnButtonListener implements EventHandler<ActionEvent> {
	
	private RentalProperty property;
	private ComboBox<Integer> returnDay, returnMonth, returnYear;
	
	public FinalReturnButtonListener(ComboBox<Integer> returnDay, ComboBox<Integer> returnMonth,
			ComboBox<Integer> returnYear, RentalProperty property){
		this.property = property;
		this.returnDay = returnDay;
		this.returnMonth = returnMonth;
		this.returnYear = returnYear;
	}

	@Override
	public void handle(ActionEvent event) {
		
		DateTime returnDate = new DateTime(returnDay.getValue(), returnMonth.getValue(), returnYear.getValue());
		
		try {
			property.returnProperty(returnDate);
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Return Completed");
		    
		    alert.setContentText(property.getFormattedAddress() + " has been returned on "
		    		+ returnDate.getFormattedDate() + ". It is now Available.");
		    alert.showAndWait();
		    ReturnPane.close();
		} catch (ReturnException e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("RETURN FAILED");
			failedAlert.setContentText("Could not return" + property.getFormattedAddress());
			failedAlert.showAndWait();
			ReturnPane.close();
		}
	}
}
