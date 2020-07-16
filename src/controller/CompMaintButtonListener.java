package controller;

import javafx.event.ActionEvent;
import view.CompMaintPane;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.RentalProperty;

public class CompMaintButtonListener implements EventHandler<ActionEvent>{

	private Button button;
	private RentalProperty property;
	
	public CompMaintButtonListener(Button button, RentalProperty property){
		this.button = button;
		this.property = property;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		CompMaintPane compMaintPane = new CompMaintPane(property);
	}
}