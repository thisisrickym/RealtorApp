package controller;

import javafx.event.ActionEvent;
import model.DataBaseToPropList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.RentalProperty;
import view.PropertyInfo;

public class InfoButtonListener implements EventHandler<ActionEvent> {
	
	private Button button;
	private RentalProperty property;
	private DataBaseToPropList dbConnect;
	
	public InfoButtonListener(Button button, RentalProperty property, DataBaseToPropList dbConnect) {
		this.button = button;
		this.property = property;
		this.dbConnect = dbConnect;
	}

	@Override
	public void handle(ActionEvent event) {
		
		PropertyInfo propInfoWindow = new PropertyInfo(property, dbConnect);
	
	}
}