package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import model.RentalProperty;
import view.CompMaintPane;
import model.DateTime;
import model.MaintException;

public class FinalCompMaintButtonListener implements EventHandler<ActionEvent> {
	
	private RentalProperty property;
	private ComboBox<Integer> maintDay, maintMonth, maintYear;

	public FinalCompMaintButtonListener(ComboBox<Integer> maintDay, ComboBox<Integer> maintMonth,
			ComboBox<Integer> maintYear, RentalProperty property) {
		this.property = property;
		this.maintDay = maintDay;
		this.maintMonth = maintMonth;
		this.maintYear = maintYear;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		DateTime maintDate = new DateTime(maintDay.getValue(), maintMonth.getValue(), maintYear.getValue());
		
		try {
			property.completeMaintenance(maintDate);
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Maintenance Completed");
		    
		    alert.setContentText(property.getFormattedAddress() + " has had maintenance completed on "
		    		+ maintDate.getFormattedDate() + ". It is now ready to be rented.");
		    alert.showAndWait();
		    CompMaintPane.close();
		} catch (MaintException e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("MAINT FAILED");
			failedAlert.setContentText("Could not complete maintenance on " + property.getFormattedAddress());
			failedAlert.showAndWait();
			CompMaintPane.close();
		}
	}
	
	
}
