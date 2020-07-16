package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class DataBaseToPropList {
	private String DB_NAME;
	private HashMap<String, RentalProperty> propertyList = new HashMap(25);;
	RentalProperty property;
	
	public DataBaseToPropList(String DB_NAME){
		this.DB_NAME = DB_NAME;
		createPropList();
		
	}
	public void createPropList(){
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			// FOR RENTAL_PROPERTY
			String query = "SELECT * FROM RENTAL_PROPERTY";
			
			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while(resultSet.next()) {
					String propID = resultSet.getString("prop_id");
					String streetNo = resultSet.getString("street_number");
					String streetName = resultSet.getString("street_name");
					String suburb =  resultSet.getString("suburb");
					String propType = resultSet.getString("prop_type");
					int bedRooms = resultSet.getInt("bed_rooms");
					String status = resultSet.getString("status");
					String lastMaintDateString = resultSet.getString("last_maint_date");
					String imgFile = resultSet.getString("img_file");
					String description = resultSet.getString("description");
					if (propType.equals("Premium Suite")) {
						DateTime lastMaintDate = stringToDate(lastMaintDateString);
						property = new PremiumSuite(propID, streetNo, streetName, suburb, bedRooms, status,
													lastMaintDate, imgFile, description);
						
					} else if (propType.equals("Apartment")) {
						property = new Apartment(propID, streetNo, streetName, suburb, bedRooms, status,
												 imgFile, description);
					} else {
						// PUT IN ERROR FOR DB WITH NON CONFORMING PROP TYPE
					}
					addRentalRecords(propID);
					propertyList.put(propID, property);
				}
			} catch (SQLException e) {
				Alert failedAlert = new Alert(AlertType.ERROR);
				failedAlert.setTitle("SQL FAILURE");
				failedAlert.setContentText(e.getMessage());
				failedAlert.showAndWait();
			}

		} catch (Exception e) {
			Alert failedAlert = new Alert(AlertType.ERROR);
			failedAlert.setTitle("DATABASE FAILURE");
			failedAlert.setContentText(e.getMessage());
			failedAlert.showAndWait();
		}
	}
	public void addRentalRecords(String propID) {
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			String query = "SELECT * FROM RENTAL_RECORD WHERE prop_id = '"
					+ propID +"'";
			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while(resultSet.next()) {
					String rentID = resultSet.getString("rent_id");
					String rentDateString = resultSet.getString("rent_date");
					DateTime rentDate = stringToDate(rentDateString);
					String expectedReturnDateString = resultSet.getString("expected_return_date");
					DateTime expectedReturnDate = stringToDate(expectedReturnDateString);
					String actualReturnDateString = resultSet.getString("actual_return_date");
					DateTime actualReturnDate = stringToDate(actualReturnDateString);
					double rentFee = resultSet.getDouble("rent_fee");
					double lateFee = resultSet.getDouble("late_fee");
					RentalRecord rentalRecord = new RentalRecord(rentID, rentDate, expectedReturnDate,
																actualReturnDate, rentFee, lateFee);
					property.addRecord(rentalRecord);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	public HashMap<String, RentalProperty> returnPropList() {
		return propertyList;
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
	public HashMap<String, RentalProperty> updatePropList(String propType, int bedRooms, String status, String suburb) {
		
		HashMap<String, RentalProperty> searchedPropList = new HashMap<String, RentalProperty>();
		
		for(HashMap.Entry entry : propertyList.entrySet()) {
			String key = (String) entry.getKey();
			RentalProperty entryProperty = (RentalProperty) entry.getValue();
			if (updatePropType(propType, entryProperty) && updateBedRooms(bedRooms, entryProperty)
					&& updateStatus(status, entryProperty) && updateSuburb(suburb, entryProperty)) {
				searchedPropList.put(key, entryProperty);
			}
		}
		
		
		return searchedPropList;
	}
	public void addPropToList(RentalProperty property) {
		propertyList.put(property.getPropID(), property);
	}
	
	public void writeToDataBase() {
		
		TableMaster.clearDataBase();
		TableMaster.createDataTables();
		TableMaster.writeToTable(propertyList);
		
	}
	
	public String exportTextFile() {
		String textFile = "";
		for(RentalProperty property : propertyList.values()) {
			textFile = textFile + property.toString()+ "\r\n";
			for(RentalRecord rentalRecord : property.getRentArray()) {
				if(rentalRecord == null) {
					break;
				}
				textFile = textFile + rentalRecord.toString()+ "\r\n" ;
			}
		}
		return textFile;
	}
	
	public List<String> getSuburbList() {
		List<String> suburbList = new ArrayList<>();
		for(RentalProperty property : propertyList.values()) {
			if(!(suburbList.contains(property.getSuburb()))){
				suburbList.add(property.getSuburb());
			}
		}
		java.util.Collections.sort(suburbList);
		return suburbList;
	}
	
	private boolean updatePropType(String propType, RentalProperty rentalProperty) {
		
		if (propType.equals("Property Type")) {
			return true;
		} else if (propType.equals(rentalProperty.getPropType())) {
			return true;
		} else {
			return false;
		}
				
	}
	
	private boolean updateBedRooms(int bedRooms, RentalProperty rentalProperty) {
		
		if (bedRooms == 0) {
			return true;
		} else if (bedRooms == rentalProperty.getNumOfBeds()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean updateStatus(String status, RentalProperty rentalProperty) {
		
		if (status.equals("Property Status")) {
			return true;
		} else if(status.equals(rentalProperty.getStatus())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean updateSuburb(String suburb, RentalProperty rentalProperty) {
		
		if (suburb.equals("Suburb")) {
			return true;		
		} else if (suburb.equals(rentalProperty.getSuburb())) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
