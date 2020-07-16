package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class ImportFile {

	private RentalProperty property;
	private RentalRecord rentalRecord;
	private DataBaseToPropList dbConnect;
	
	public ImportFile(File file1, DataBaseToPropList dbConnect) throws Exception {
		
		this.dbConnect = dbConnect;
		
		try (Scanner input = new Scanner(file1)) {
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] lineSplit = line.split(":");


				if (lineSplit.length == 9) {
					apartmentHelper(lineSplit);
					
				} else if(lineSplit.length == 10) {
					premiumHelper(lineSplit);

				}else if (lineSplit.length == 6) {
					rentalRecordHelper(lineSplit);

				}else {
					throw new Exception("File is in an incorrect format...");
				}
			}
		} catch (FileNotFoundException e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("IMPORT FAILED");
			failedAlert.setContentText("File could not be opened or is in an incorrect format.");
			failedAlert.showAndWait();;
		}
		String[] args = new String[1];
	}
	
	private void apartmentHelper(String[] lineSplit) {
		
		try {
			int bedRooms = Integer.parseInt(lineSplit[5]);
			property = new Apartment(lineSplit[0], lineSplit[1], lineSplit[2], lineSplit[3], 
					bedRooms, lineSplit[6], lineSplit[7], lineSplit[8]);
			dbConnect.addPropToList(property);
		} catch (Exception e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("IMPORT FAILED");
			failedAlert.setContentText("A line could not be added.");
			failedAlert.showAndWait();
		}
		
		
	}
	
	private void premiumHelper(String[] lineSplit) {
		try{
			int bedRooms = Integer.parseInt(lineSplit[5]);
			DateTime lastMaintDate = stringToDate(lineSplit[7]);
			property = new PremiumSuite(lineSplit[0], lineSplit[1], lineSplit[2], lineSplit[3], 
					bedRooms, lineSplit[6], lastMaintDate, lineSplit[8], lineSplit[9]);
			dbConnect.addPropToList(property);
		} catch (Exception e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("IMPORT FAILED");
			failedAlert.setContentText("A line could not be added.");
			failedAlert.showAndWait();
		}
	
	}
	
	private void rentalRecordHelper(String[] lineSplit) {
		DateTime rentDate = stringToDate(lineSplit[1]);
		DateTime expReturnDate = stringToDate(lineSplit[2]);
		try {
			DateTime actReturnDate = stringToDate(lineSplit[3]);
			double rentFee = Double.parseDouble(lineSplit[4]);
			double lateFee = Double.parseDouble(lineSplit[5]);
			rentalRecord = new RentalRecord(lineSplit[0], rentDate, expReturnDate, actReturnDate, rentFee, lateFee);
		} catch (Exception e) {
			rentalRecord = new RentalRecord(lineSplit[0], rentDate, expReturnDate);
		}
		property.addRecord(rentalRecord);
	}
	
	private DateTime stringToDate(String dateString) {
		String delims = "[/]+";
		String[] dateSplit = dateString.split(delims);
		int day = Integer.parseInt(dateSplit[0]);
		int month = Integer.parseInt(dateSplit[1]);
		int year = Integer.parseInt(dateSplit[2]);
		DateTime Date = new DateTime(day, month, year);
		return Date;
	}
}
