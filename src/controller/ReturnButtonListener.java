package controller;

import javafx.event.ActionEvent;
import view.ReturnPane;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.RentalProperty;

public class ReturnButtonListener implements EventHandler<ActionEvent>{
	private Button button;
	private RentalProperty property;
	
	public ReturnButtonListener(Button button, RentalProperty property){
		this.button = button;
		this.property = property;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		ReturnPane returnPane = new ReturnPane(property);
	
	}
}