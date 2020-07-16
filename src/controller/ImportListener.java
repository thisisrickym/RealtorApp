package controller;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataBaseToPropList;
import model.ImportFile;

public class ImportListener implements EventHandler<ActionEvent> {

	private Stage primaryStage;
	private DataBaseToPropList dbConnect;
	
	public ImportListener(Stage stage, DataBaseToPropList dbConnect) {
		
		this.dbConnect = dbConnect;
		primaryStage = stage;
	}
	
	public void handle(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a folder...");
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
//			String selectedDirPath = dirChooser.showDialog(primaryStage).getAbsolutePath();
//			System.out.println(selectedDir.getAbsolutePath());
			ImportFile read = new ImportFile(selectedFile, dbConnect);
			
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("IMPORT WAS SUCCESFUL");
		    
		    alert.setContentText("You have succesfully added the properties and/or rental records to the DataBase. "
		    		+ "Please click refresh on the Main Menu to access this new information.");
		    alert.showAndWait();
		} catch (Exception e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("IMPORT FAILED");
			failedAlert.setContentText("SORRY, THE DATA IMPORT HAS FAILED. \r\n"
					+ "Please try again.");
			failedAlert.showAndWait();
		}
	}
	
}
