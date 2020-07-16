package controller;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;
import model.DataBaseToPropList;
import javafx.event.*;
import java.io.*;

public class ExportListener implements EventHandler<ActionEvent> {

	private Stage primaryStage;
	private DataBaseToPropList dbConnect;
	
	public ExportListener(Stage stage, DataBaseToPropList dbConnect) {
		
		this.dbConnect = dbConnect;
		primaryStage = stage;
	}
	
	@Override
	public void handle(ActionEvent event) {
		try {
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setTitle("Select a folder...");
			File selectedDir = dirChooser.showDialog(primaryStage);
//			String selectedDirPath = dirChooser.showDialog(primaryStage).getAbsolutePath();
//			System.out.println(selectedDir.getAbsolutePath());
			writeToFile(selectedDir.getAbsolutePath(), "export_data.txt");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("EXPORT UNSUCCESSFUL");
		    
		    alert.setContentText(e.getMessage());
		    alert.showAndWait();
		}
	}
	
	private void writeToFile(String absolutePath, String fileName) throws Exception {
		PrintWriter writer;
		String textFile = dbConnect.exportTextFile();
		try {
			writer = new PrintWriter(absolutePath + "/" + fileName, "UTF-8");
			writer.println(textFile);
			writer.close();
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("EXPORT SUCCESSFUL");
		    
		    alert.setContentText("File " + fileName + " has been saved to " + absolutePath);
		    alert.showAndWait();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			
		    throw new Exception("File " + fileName + " was NOT saved to " + absolutePath);
		    
		}
	}
	
}
