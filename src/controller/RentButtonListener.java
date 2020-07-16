package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.RentalProperty;
import view.RentPane;

public class RentButtonListener implements EventHandler<ActionEvent>{
	
	private Button button;
	private RentalProperty property;
	
	public RentButtonListener(Button button, RentalProperty property){
		this.button = button;
		this.property = property;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		RentPane rent = new RentPane(property);
		
	}
}