package model;



public class Apartment extends RentalProperty {
	
	
	public Apartment(String propID, String streetNo, String streetName, String suburb, int numOfBeds,
			String status, String imgFile, String description) throws AddPropException{
		super(streetNo, streetName, suburb, numOfBeds, status, imgFile, description);
		getRentRate(numOfBeds);
		setPropType("Apartment");
		setPropID(propID);
	}
	public Apartment(String streetNo, String streetName, String suburb, int numOfBeds,
			String imgFile, String description) throws AddPropException {
		super(streetNo, streetName, suburb, numOfBeds, "Available", imgFile, description);
		
		getRentRate(numOfBeds);
		setPropType("Apartment");
		String streetNameNoSpace = streetName.replace(" ", "");
		setPropID("A_"+streetNo.toUpperCase()+streetNameNoSpace+suburb.substring(0,2).toUpperCase());
	}
	

	
	public void rent(String customerID, DateTime rentDate, int numOfRentDay) throws RentException, InvalidIdException {
		
		int minNumOfDays = getMinNumOfDays(rentDate);
		
		if (numOfRentDay < minNumOfDays) {
			
			throw new RentException ("the length of stay is less than the minimum number of rent days("
					+ minNumOfDays +") for this property on the specified rent date.");
			
		}else {
			try {
				super.rent(customerID, rentDate, numOfRentDay);
			} catch(Exception e) {
				throw e;
			}
			
		}
	
	}
	
	
	
	private void getRentRate(int numOfBeds) {
		
		double rentRate = 0;
		switch(numOfBeds) {
		case 1:
			rentRate = 143.00;
			break;
		case 2:
			rentRate = 210.00;
			break;
		case 3:
			rentRate = 319.00;
			break;
		default:
			System.out.println("Error: Apartments should have 1, 2, or 3 bedrooms.");
			break;		
		}
		
		double lateRate = 1.15 * rentRate;
		
		setRentRate(rentRate, lateRate);
				
	}
	
	public int getMinNumOfDays(DateTime rentDate) {
		
		long dayOfWeek = ((rentDate.getTime() / (24 * 60 * 60000)) + 1) % 7;
		// This gives us  Thurs = 0, Fri = 1 etc...
		if (dayOfWeek < 3) {
			return 3;
		}else {
			return 2;
		}
		
	}

}
