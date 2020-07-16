package controller;


import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import model.DataBaseToPropList;

public class CloseButtonListener implements EventHandler<WindowEvent> {

	private DataBaseToPropList dbConnect;
	
	public CloseButtonListener(DataBaseToPropList dbConnect){
	
		this.dbConnect = dbConnect;
		
	}
	
	@Override
	public void handle(WindowEvent event) {
		dbConnect.writeToDataBase();
		try{
			Thread.sleep(1000);
		} catch (Exception e) {
			
		}
	}
}
