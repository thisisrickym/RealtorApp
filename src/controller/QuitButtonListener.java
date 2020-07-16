package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.DataBaseToPropList;
import javafx.scene.control.MenuItem;

public class QuitButtonListener implements EventHandler<ActionEvent> {
	
	private DataBaseToPropList dbConnect;
	private MenuItem quit;
	
	public QuitButtonListener (DataBaseToPropList dbConnect, MenuItem quit) {
		
		this.dbConnect = dbConnect;
		this.quit = quit;
		
	}

	@Override
	public void handle(ActionEvent event) {
		
		dbConnect.writeToDataBase();
		try{
			Thread.sleep(1000);
		} catch (Exception e) {
			
		}
		System.exit(0);
	}
}
