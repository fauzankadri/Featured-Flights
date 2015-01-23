/**
 * 
 */
package pIIIsoln;

import java.awt.List;
import java.util.ArrayList;

/**
 * @author alex
 *
 */
public class Admin extends Client {

	/**
	 * 
	 */
	public Admin() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param address
	 * @param creditCardNum
	 * @param expiryDate
	 */
	public Admin(String firstName, String lastName, String email,
			String address, String creditCardNum, String expiryDate) {
		super(firstName, lastName, email, address, creditCardNum, expiryDate);
		// TODO Auto-generated constructor stub
	}
	
	public BillingInfo getThisBillingInfo(){
		return this.billInfo;
	}
	
	public BillingInfo getClientBillingInfo(Client client){
		return client.billInfo;
	}

	public void editBillingInfo(String infoType, String newInfo){
		this.editBillingInfo(infoType, newInfo);
	}
	
	public void editBillingInfo(String infoType,String newInfo, Client client){
		client.editBillingInfo(infoType, newInfo);
	}
	
	public void uploadFlights(String path, MainSystem currentMainSystem){
		currentMainSystem.uploadFlightInfo(path);
	}
	
	public void editFlightInfo(Flight flight,String category,String newValue){
		if (category.equals("flightNumber")){
			flight.setFlightNumber(newValue);
		}
		else if (category.equals("departureDateTime")){
			flight.setDepartureDateTime(newValue);
		}
		else if (category.equals("arrivalDateTime")){
			flight.setArrivalDateTime(newValue);
		}
		else if (category.equals("airline")){
			flight.setAirline(newValue);
		}
		else if (category.equals("origin")){
			flight.setOrigin(newValue);
		}
		else if (category.equals("destination")){
			flight.setDestination(newValue);
		}
		else if (category.equals("cost")){
			try{
			flight.setCost(Double.parseDouble(newValue));
		}catch(NumberFormatException e){
			System.out.println("Invalid cost format");
		}
		}
	}
	
	public void bookClientItinerary(Client client,Itinerary givenItinerary){
		client.bookItinerary(givenItinerary);
	}
	
	public ArrayList<Itinerary> getClientBookedItineraries(Client client){
		return client.bookedItineraries;
	}
}
