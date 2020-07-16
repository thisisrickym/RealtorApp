package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.DataBaseToPropList;
import view.AddPropPane;

public class AddPropButtonListener implements EventHandler<ActionEvent> {

	private DataBaseToPropList dbConnect;
	
	public AddPropButtonListener(DataBaseToPropList dbConnect) {
		this.dbConnect =dbConnect;
	}
	
	@Override
	public void handle(ActionEvent event) {
		AddPropPane addProp = new AddPropPane(dbConnect);
	}
}
