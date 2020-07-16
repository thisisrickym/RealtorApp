package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import model.RentalProperty;
import model.MaintException;

public class MaintButtonListener implements EventHandler<ActionEvent>{
	
	private Button button;
	private RentalProperty property;
	
	public MaintButtonListener(Button button, RentalProperty property){
		this.button = button;
		this.property = property;
	}
	
	@Override
	public void handle(ActionEvent event) {
		try {
			property.performMaintenance();
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("NOW IN MAINTENANCE");
		    
		    alert.setContentText(property.getFormattedAddress() + " is now under Maintenance");
		    alert.showAndWait();
		} catch (MaintException e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("Maintenance FAILED");
			failedAlert.setContentText(property.getFormattedAddress() + " could not be put into Maintenance");
			failedAlert.showAndWait();
		}
	}
}