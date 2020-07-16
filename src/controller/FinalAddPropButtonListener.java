package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import model.Apartment;
import model.PremiumSuite;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import model.DataBaseToPropList;
import model.AddPropException;
import view.AddPropPane;

public class FinalAddPropButtonListener implements EventHandler<ActionEvent> {

	private ComboBox<String> propType;
	private TextField streetNumber;
	private TextField streetName;
	private TextField suburb;
	private ComboBox<Integer> bedRooms;
	private TextField imgFile;
	private TextArea description;
	private DataBaseToPropList dbConnect;
	
	public FinalAddPropButtonListener(ComboBox<String> propType, TextField streetNumber, TextField streetName,
									TextField suburb, ComboBox<Integer> bedRooms, TextField imgFile,
									TextArea description, DataBaseToPropList dbConnect) {
		
		this.propType = propType;
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.suburb = suburb;
		this.bedRooms = bedRooms;
		this.imgFile = imgFile;
		this.description = description;
		this.dbConnect = dbConnect;
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		try {
			switch(propType.getValue()) {
			case "Apartment":
				Apartment apartment = new Apartment(streetNumber.getText(), streetName.getText(), suburb.getText(), bedRooms.getValue(),
						imgFile.getText(), description.getText());
				dbConnect.addPropToList(apartment);
				
				break;
			case "Premium Suite":
				PremiumSuite premiumSuite = new PremiumSuite(streetNumber.getText(), streetName.getText(), suburb.getText(), bedRooms.getValue(),
						imgFile.getText(), description.getText());
				dbConnect.addPropToList(premiumSuite);
				break;
			default:
				throw new AddPropException("Please enter either Apartment or PremiumSuite for propType.");
			}
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("PROPERTY SUCCESFULLY ADDED");
		    
		    alert.setContentText("You have succesfully added the property to the DataBase. Please click refresh on the Main Menu"
		    		+ "to access this property.");
		    alert.showAndWait();
		    AddPropPane.close();
			
		} catch (AddPropException e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("COULD NOT ADD PROPERTY");
			failedAlert.setContentText("The property could not be added." + e.getMessage());
			failedAlert.showAndWait();
		}
	}
}
