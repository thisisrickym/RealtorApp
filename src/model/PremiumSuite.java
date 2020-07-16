package model;


public class PremiumSuite extends RentalProperty {
	
	private DateTime lastMaint;
	

	public PremiumSuite(String propID, String streetNo, String streetName, String suburb, int numOfBeds,
			String status, DateTime lastMaint, String imgFile, String description) throws AddPropException {
		
		super(streetNo, streetName, suburb, numOfBeds, status, imgFile, description);
		if(numOfBeds != 3) {
			throw new AddPropException("Premium Suites MUST HAVE 3 bed rooms.");
		}
		setPropID(propID);
		this.lastMaint = lastMaint;
		setPropType("Premium Suite");
		setRentRate(554.00, 662.00);
	}
	public PremiumSuite(String streetNo, String streetName, String suburb, int numOfBeds,
			String imgFile, String description) throws AddPropException {
		
		super(streetNo, streetName, suburb, numOfBeds, "Available", imgFile, description);
		if(numOfBeds != 3) {
			throw new AddPropException("Premium Suites MUST HAVE 3 bed rooms.");
		}
		String streetNameNoSpace = streetName.replace(" ", "");
		setPropID("S_"+streetNo.toUpperCase()+streetNameNoSpace+suburb.substring(0,2).toUpperCase());
		lastMaint = new DateTime();
		setPropType("Premium Suite");
		setRentRate(554.00, 662.00);
	}
	
	public void rent(String customerID, DateTime rentDate, int numOfRentDay) throws RentException, InvalidIdException{
		
		DateTime nextMaint = new DateTime(lastMaint, 10);
		DateTime returnDate = new DateTime(rentDate, numOfRentDay);
		
		if (nextMaint.getTime() < returnDate.getTime() || numOfRentDay < 1) {
			throw new RentException ("Return Date is after next scheduled Maintenance Date.");
		}else {
			try{
				super.rent(customerID, rentDate, numOfRentDay);
			} catch (Exception e) {
				throw e;
			} 
		}
	}
	
	public void completeMaintenance(DateTime maintDate) throws MaintException {

		try{
			super.completeMaintenance(maintDate); 
			lastMaint = maintDate;
			return;
		} catch (Exception e){
			throw e;
		}
	}
	
	public String toString() {
		String fullInfo = getPropID() + ":" + getStreetNumber() + ":" + getStreetName() 
				+ ":" + getSuburb() + ":" + getPropType() + ":" + getNumOfBeds() + ":" + getStatus() 
				+ ":" + lastMaint + ":" + getImgFile() + ":" + getDescription();
		return fullInfo;
	}
	

}
