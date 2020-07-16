package model;


public abstract class RentalProperty {
	private String streetNo;
	private String streetName;
	private String suburb;
	private int numOfBeds;
	private String propID;
	private RentalRecord[] rentArray;
	private String status;
	private double rentRate;
	private double lateRate;
	private int minNumOfDays;
	private String propertyType;
	private String imgFile;
	private String description;
	
	RentalProperty(String streetNo, String streetName, String suburb, int numOfBeds,
			String status, String imgFile, String description) throws AddPropException {
		String descriptionTester = description.replaceAll(" ", "");
		if(streetNo == null || streetName == null || suburb == null) {
			throw new AddPropException("Please enter valid inputs in each text field.");
		} else if (descriptionTester.length() < 80 || descriptionTester.length() > 100) {
			throw new AddPropException("Description must be between 80 and 100 characters.");
		}
		this.streetNo = streetNo;
		this.streetName = streetName;
		this.suburb = suburb;
		this.numOfBeds = numOfBeds;
		this.status = status;
		rentArray = new RentalRecord[10];
		this.imgFile = imgFile;
		this.description = description;
	}
	



	public void rent(String customerID, DateTime rentDate, int numOfRentDay) throws RentException, InvalidIdException {
		
		DateTime currentDate = new DateTime();
		DateTime testDate = new DateTime(rentDate, 1);
		// Because we want renters to be able to rent the current day.
		
		try {
			custIdChecker(customerID);
		} catch (Exception e) {
			throw new InvalidIdException("Invalid customer ID");
		}
		if ((!(status.equals("Available")))) {
			throw new RentException ("the property is currently " + getStatus());
		} else if (currentDate.getTime() > testDate.getTime()){
			throw new RentException ("the date entered was invalid or in the past.");
		} else if (numOfRentDay < 1){
			throw new RentException ("you entered an invalid return date.");
		}else {
			makeStatusRented();
			RentalRecord rentalRecord = new RentalRecord(propID, customerID, rentDate, numOfRentDay);
			addRecord(rentalRecord);
		}
	}
	
	public void returnProperty(DateTime returnDate) throws ReturnException {
		
		DateTime rentDate = rentArray[0].getRentDate();
		DateTime estReturnDate = rentArray[0].getEstReturnDate();
		int numOfRentDays = DateTime.diffDays(returnDate, rentDate);
		
		if (!(getStatus().equals("Rented")) || numOfRentDays < minNumOfDays) {
			throw new ReturnException("the property is not currently rented");
		}
		int lateDays = DateTime.diffDays(returnDate, estReturnDate);
		if (lateDays > 0) {
			int regDays = numOfRentDays - lateDays;
			rentArray[0].updateRentalRecord(regDays, rentRate, lateDays, lateRate);
		} else {
			rentArray[0].updateRentalRecord(numOfRentDays, rentRate);
		}
		rentArray[0].setReturnDate(returnDate);
		makeAvailable();
		return;
			
	}
	
	public void performMaintenance() throws MaintException {
		
		if(!(getStatus().equals("Available"))) {
			throw new MaintException("the property is currently " + getStatus());
		} else {
			makeStatusInMaintenance();
		}
		
	} 
	
	public void completeMaintenance(DateTime maintDate) throws MaintException {
		
		if(!(getStatus().equals("In Maintenance"))) {
			throw new MaintException("the property is currently " + getStatus());
		} else {
			makeAvailable();
			return;
		}
	}
	
	private void custIdChecker(String custID) throws Exception {
		
		String testerCus = custID.substring(0, 3).toUpperCase();
		int testInt = Integer.parseInt(custID.substring(3,custID.length()));
		if(testerCus.equals("CUS")) {
			return;
		} else {
			throw new Exception("Invalid Customer ID");
		}
		
	}
	
	public String toString() {
		
		String fullInfo = propID + ":" + streetNo + ":" + streetName + ":"
				+ suburb + ":" + propertyType + ":" + numOfBeds + ":" + status 
				+ ":" + imgFile + ":" + description;
		return fullInfo;
		
	}
	
	public String getDetails() {
		
		String propDetails = getPropDetails();
		String rentalDetails = getRentalRecord();
		return propDetails + rentalDetails;
	}
	
	public String getShortDetails() {
		
		String propDetails = getPropDetails();
		String newestRentalRecord = rentArray[0].getDetails();
		return propDetails + newestRentalRecord;
	}
	public String getPropDetails() {
		
		return "Property ID:	" + propID + "\r\n"
				+ "Address:		" + getFormattedAddress() + "\r\n"
				+ "Type:		" + propertyType + "\r\n"
				+ "Bedroom:		" + numOfBeds + "\r\n"
				+ "Status:		" + status + "\r\n"
				+ "RENTAL RECORD";
	}
	
	public String getRentalRecord() {
		
		if (rentArray[0] == null) {
			return ":	empty" + "\r\n"
					+ "--------------------------------";
		}
		String rentalRecordString = "";
		for ( RentalRecord rentalRecord : rentArray) {
			if (rentalRecord != null) {
				rentalRecordString += "\r\n" + rentalRecord.getDetails() 
						+ "--------------------------------";
			}		
		}
		return rentalRecordString;
	}

	public double getRentRate() {
		return rentRate;
	}
	public void addRecord(RentalRecord rentalRecord) {
		
		for (int i=0; i < 9; i++) {
			rentArray[9-i] = rentArray[8-i];
		}
		rentArray[0] = rentalRecord;
	}
	
	public RentalRecord[] getRentArray() {
		return rentArray;
	}
	public void setPropID(String propID) {
		
		this.propID = propID;
	}
	public void setRentRate(double rentRate, double lateRate) {
		
		this.rentRate = rentRate;
		this.lateRate = lateRate;
	}
	
	public String getPropID() {
		return propID;
	}
	
	public String getStreetNumber() {
		return streetNo;
	}
	public String getStreetName() {
		return streetName;
	}
	public String getSuburb() {
		return suburb;
	}
	public int getNumOfBeds() {
		return numOfBeds;
	}
	public void setPropType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getPropType() {
		return propertyType;
	}
	public String getFormattedAddress() {
		return streetNo + " " + streetName + " " + suburb;
	}
	public void makeStatusRented() {
		setStatus("Rented");
	}
	public void makeStatusInMaintenance() {
		setStatus("In Maintenance");
	}
	public void makeAvailable() {
		setStatus("Available");
	}

	private void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getMinDays() {
		return minNumOfDays;
	}
	
	public String getImgFile() {
		return imgFile;
	}
	
	public String getDescription() {
		return description;
	}
}

