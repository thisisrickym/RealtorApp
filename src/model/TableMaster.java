package model;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

public class TableMaster {
	
	final static String DB_NAME = "FlexiRentDB";
	final static String TABLE_NAME = "RENTAL_PROPERTY";
	final static String TABLE_NAME2 = "RENTAL_RECORD";
	
	TableMaster(){
		
	}
	
	public static void clearDataBase() {
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			int result = stmt.executeUpdate("DROP TABLE " + TABLE_NAME);
			
			
		} catch (Exception e) {
		}
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			int result = stmt.executeUpdate("DROP TABLE " + TABLE_NAME2);
			

		} catch (Exception e) {
		}
	}
	
	public static void createDataTables(){
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			int result = stmt.executeUpdate("CREATE TABLE RENTAL_PROPERTY ("
										+ "prop_id VARCHAR(45) NOT NULL,"
										+ "street_number VARCHAR(8) NOT NULL," 
										+ "street_name VARCHAR(20) NOT NULL,"
										+ "suburb VARCHAR(20) NOT NULL,"
										+ "prop_type VARCHAR(20) NOT NULL,"
										+ "bed_rooms INT NOT NULL,"
										+ "status VARCHAR(20) NOT NULL,"
										+ "last_maint_date VARCHAR(15),"
										+ "img_file VARCHAR(25),"
										+ "description VARCHAR(200),"
										+ "PRIMARY KEY (prop_id))");
			if(result == 0) {

			} else {

			}
		} catch (Exception e) {

		}
		
		// RENTAL_RECORD
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			int result = stmt.executeUpdate("CREATE TABLE RENTAL_RECORD ("
										+ "prop_id VARCHAR(45) NOT NULL, "
										+ "rent_id VARCHAR(45) NOT NULL,"
										+ "rent_date VARCHAR(15) NOT NULL," 
										+ "expected_return_date VARCHAR(15) NOT NULL,"
										+ "actual_return_date VARCHAR(15),"
										+ "rent_fee DOUBLE,"
										+ "late_fee DOUBLE,"
										+ "PRIMARY KEY (rent_id))");
			if(result == 0) {

			} else {

			}
		} catch (Exception e) {

		}
	}
	
	public static void writeToTable(HashMap<String, RentalProperty> propertyList) {
		
		for (RentalProperty property : propertyList.values()) {
			
			String values = property.toString();
			String[] valuesArray = values.split(":");
			String record[];
			if(valuesArray.length == 9) {
				record = apartmentHelper(valuesArray);
			} else if(valuesArray.length == 10) {
				record= valuesArray;
			} else {
				record = new String[1];
			}
			writeNewRecord(record, "RENTAL_PROPERTY");
			for (RentalRecord rentalRecord : property.getRentArray()) {
				if (rentalRecord == null) {
					break;
				}
				String rentToString = property.getPropID() + ":" + rentalRecord.toString();
				String rentRecord[] = rentToString.split(":");
				writeNewRecord(rentRecord, "RENTAL_RECORD");
			}
		}
	}
	
	private static String[] apartmentHelper(String[] valuesArray) {
		String[] appartmentLineSplit = new String[10];
		appartmentLineSplit[7] = "NULL";
		for (int i=0; i < 10; i++) {
			if (i < 7) {
				appartmentLineSplit[i] = valuesArray[i];
			} else if (i > 7) {
				appartmentLineSplit[i] = valuesArray[i-1];
			}
		}
		return appartmentLineSplit;
	}
	
	private static void writeNewRecord(String[] lineSplit, String TableName){
		
		String values = "('" + String.join("', '", lineSplit) + "')";
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			String query = "INSERT INTO " + TableName +
					//" VALUES (1, 's3388490', 'Peter', 'Tilmanis')"
					" VALUES " + values;
			
			int result = stmt.executeUpdate(query);
			
			con.commit();
			


		} catch (Exception e) {

		}
	}
}
