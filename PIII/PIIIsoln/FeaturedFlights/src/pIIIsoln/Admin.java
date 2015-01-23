/**
 * 
 */
package pIIIsoln;

import java.util.ArrayList;

/**
 * @author alex
 * 
 */
public class Admin extends Client
{

	//serial id for the Admin object
	private static final long	serialVersionUID	= -6579878391672551708L;

	/**
	 * 
	 */
	public Admin()
	{
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
			String address, String creditCardNum, String expiryDate)
	{
		super(firstName, lastName, email, address, creditCardNum, expiryDate);
	}

	/**
	 * Returns the billing info of the admin
	 * @return the billing info of admin
	 */
	public BillingInfo getThisBillingInfo()
	{
		return this.billInfo;
	}

	/**
	 * Returns the billing info of the client
	 * @param client the client the admin wants the billing info of
	 * @return the billing info of the client
	 */
	public BillingInfo getClientBillingInfo(Client client)
	{
		return client.billInfo;
	}

	/**
	 * Edits the billing info of the admin
	 * @param infoType the type of information the admin wants to edit
	 * @param newInfo the new information the admin wants to put
	 */
	public void editBillingInfo(String infoType, String newInfo)
	{
		this.editBillingInfo(infoType, newInfo);
	}

	/**
	 * Edits the billing information of the client
	 * @param infoType the type of information the admin wants to edit
	 * @param newInfo the new information the admin puts in for client
	 * @param client the client to change the billing info of
	 */
	public void editBillingInfo(String infoType, String newInfo, Client client)
	{
		client.editBillingInfo(infoType, newInfo);
	}

	/**
	 * Uploads the flight information from a csv file to system
	 * @param path the file path for the flights
	 * @param currentMainSystem the place to upload flights to
	 */
	public void uploadFlights(String path, MainSystem currentMainSystem)
	{
		currentMainSystem.uploadFlightInfo(path);
	}

	/**
	 * Edits the flight information
	 * @param flight the flight to edit
	 * @param category the type of information to edit
	 * @param newValue the new information to put in
	 */
	public void editFlightInfo(Flight flight, String category, String newValue)
	{
		if (category.equals("flightNumber"))
		{
			flight.setFlightNumber(newValue);
		}
		else if (category.equals("departureDateTime"))
		{
			flight.setDepartureDateTime(newValue);
		}
		else if (category.equals("arrivalDateTime"))
		{
			flight.setArrivalDateTime(newValue);
		}
		else if (category.equals("airline"))
		{
			flight.setAirline(newValue);
		}
		else if (category.equals("origin"))
		{
			flight.setOrigin(newValue);
		}
		else if (category.equals("destination"))
		{
			flight.setDestination(newValue);
		}
		else if (category.equals("cost"))
		{
			try
			{
				flight.setCost(Double.parseDouble(newValue));
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Books an itinerary for a given client
	 * @param client the client to book itinerary to
	 * @param givenItinerary the itinerary to book
	 */
	public void bookClientItinerary(Client client, Itinerary givenItinerary)
	{
		client.bookItinerary(givenItinerary);
	}

	/**
	 * Returns the booked itineraries the client has
	 * @param client the client to grab book itineraries from
	 * @return the book itineraries that client has
	 */
	public ArrayList<Itinerary> getClientBookedItineraries(Client client)
	{
		return client.bookedItineraries;
	}
}
