package model;


public class RentalRecord {
	
	private String rentID;
	private DateTime rentDate;
	private DateTime estReturnDate;
	private DateTime actReturnDate;
	private double rentFee;
	private double lateFee;
	
	RentalRecord(String rentID, DateTime rentDate, DateTime estReturnDate,
			DateTime actReturnDate, double rentFee, double lateFee){
		this.rentID = rentID;
		this.rentDate = rentDate;
		this.estReturnDate = estReturnDate;
		this.actReturnDate = actReturnDate;
		this.lateFee = lateFee;
		this.rentFee = rentFee;
	}
	
	RentalRecord(String rentID, DateTime rentDate, DateTime estReturnDate){
		this.rentID = rentID;
		this.rentDate = rentDate;
		this.estReturnDate = estReturnDate;
		actReturnDate = null;
		rentFee = 0;
		lateFee = 0;
	}
	
	RentalRecord(String propID, String customerID, DateTime rentDate, int numOfRentDay){
		
		rentID = propID + "_" + customerID + "_" + rentDate.getEightDigitDate();
		this.rentDate = rentDate;
		estReturnDate = new DateTime(rentDate, numOfRentDay);
		actReturnDate = null;
		rentFee = 0;
		lateFee = 0;
	}
	
	
	public String getDetails() {
		
		String rentalRecord;
		rentalRecord = "Record ID:		" + rentID + "\r\n"
				+ "Rent Date:		" + rentDate + "\r\n"
				+ "Est Return Date: " + estReturnDate.getFormattedDate() + "\r\n";
		if (actReturnDate != null) {
			rentalRecord += "Act Return Date:		" + actReturnDate.getFormattedDate() + "\r\n"
					+ "Rental Fee:		" + String.format("%.2f", rentFee) + "\r\n"
					+ "Late Fee:			" + String.format("%.2f", lateFee) + "\r\n";
		} 
		return rentalRecord;
	}

	public String toString() {
		
		String rentDateString = rentDate.getFormattedDate();
		String estReturnDateString = estReturnDate.getFormattedDate();
		String startOfString = rentID + ":" + rentDateString + ":" + estReturnDateString ;
		
		if (actReturnDate == null) {
			return startOfString + ":none:none:none:";
		} else {
			String actReturnDateString = actReturnDate.getFormattedDate();
			return startOfString + ":" + actReturnDateString + ":" + String.format("%.2f", rentFee) + ":" + String.format("%.2f", lateFee);
		}
	}
	public void setReturnDate(DateTime actReturnDate) {
		this.actReturnDate = actReturnDate;	
	}
	
	public DateTime getRentDate() {
		return rentDate;
	}
	public DateTime getEstReturnDate() {
		return estReturnDate;
	}
	public void updateRentalRecord(int regDays, double rentRate) {
		rentFee = regDays * rentRate;
	}
	public void updateRentalRecord(int regDays, double rentRate, int lateDays, double lateRate) {
		
		lateFee = lateDays * lateRate;
		updateRentalRecord(regDays, rentRate);
	}
}
