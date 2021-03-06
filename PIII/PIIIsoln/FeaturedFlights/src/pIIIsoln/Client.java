package pIIIsoln;

import android.annotation.SuppressLint;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alex
 * 
 */
public class Client extends User implements Serializable
{

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 447466997641142786L;
	protected BillingInfo			billInfo;
	protected ArrayList<Itinerary>	bookedItineraries;

	/**
	 * creates a empty client
	 */
	public Client()
	{
		super();
		this.bookedItineraries = new ArrayList<Itinerary>();
		this.billInfo = new BillingInfo();

	}

	/**
	 * creates a representation of the Client class
	 * 
	 * @param firstName
	 *            firstName of the user
	 * @param lastName
	 *            lastName of the user
	 * @param email
	 *            user's email
	 * @param adress
	 *            user's address
	 * @param creditCardNum
	 *            user's credit card number
	 * @param expiryDate
	 *            user's expire date
	 */
	public Client(String firstName, String lastName, String email,
			String address, String creditCardNum, String expiryDate)
	{
		super(email);
		this.bookedItineraries = new ArrayList<Itinerary>();
		this.billInfo = new BillingInfo(firstName, lastName, email, address,
				creditCardNum, expiryDate);
	}

	/**
	 * @param date
	 *            the date of to depart
	 * @param origin
	 *            the initial location
	 * @param destination
	 *            final destination
	 * @return a list of all Itineraries given a date,origin and destination
	 */
	public List<Itinerary> searchItineraries(String date, String origin,
			String destination, MainSystem main)
	{
		return main.makeAllItineraries(date, origin, destination);
	}

	/**
	 * 
	 * @return the email of the client as a String
	 */
	public String getEmail()
	{
		return email;
	}

	public ArrayList<Itinerary> getBookedItineraries()
	{
		return this.bookedItineraries;
	}

	/**
	 * returns an ordered ArrayList<itineraries> by time
	 * 
	 * @param itineraries
	 *            the given ArrayList to be ordered by time
	 * @return an ordered ArrayList<itineraries> by time
	 * @throws ParseException
	 */
	public ArrayList<Itinerary> orderByTime(ArrayList<Itinerary> itineraries)
	{
		ArrayList<Itinerary> orderedIti = new ArrayList<Itinerary>();

		// goes through all the Itineraries
		for (Itinerary currentIti : itineraries)
		{

			// if the list is empty it adds the element to orderedIti
			if (orderedIti.isEmpty())
			{
				orderedIti.add(currentIti);
			}
			else
			{

				// goes through orderedIti
				for (int index = 0; index < orderedIti.size(); index++)
				{

					// gets the hours and minutes for both orderedIti object
					// at location index and currentIti
					int orderedItiHours = orderedIti.get(index).getTotalHours();
					int orderedItiMinutes = orderedIti.get(index)
							.getTotalMinutes();
					int currentItiMinutes = currentIti.getTotalMinutes();
					int currentItiHours = currentIti.getTotalHours();

					// if currentIti requires less time than orderedIti at the
					// index
					// adds the currentIti before the orderedIti in the
					// orderedIti
					if (currentItiHours < orderedItiHours)
					{
						orderedIti.add(index, currentIti);

						// ends the for loop
						index = orderedIti.size();

						// if both Itineraries have the same hours it checks the
						// minutes
					}
					else if (currentItiHours == orderedItiHours)
					{
						if (currentItiMinutes <= orderedItiMinutes)
						{
							orderedIti.add(index, currentIti);

							// ends the for loop
							index = orderedIti.size();
						}
					}

					// if it reaches the end of the orderedIti then adds the
					// currentIti
					// to the end of orderedIti
					else if (index + 1 == orderedIti.size())
					{
						orderedIti.add(currentIti);

						// ends the for loop
						index = orderedIti.size();
					}

				}
			}

		}
		return orderedIti;
	}

	/**
	 * returns an ordered ArrayList<itineraries> by cost
	 * 
	 * @param itineraries
	 *            the given ArrayList to be ordered by cost
	 * @return an ordered ArrayList<itineraries> by cost
	 * @throws ParseException
	 */
	public ArrayList<Itinerary> orderByCost(ArrayList<Itinerary> itineraries)
	{

		ArrayList<Itinerary> orderedIti = new ArrayList<Itinerary>();

		// goes through all the Itineraries
		for (Itinerary currentIti : itineraries)
		{

			// if the list is empty it adds the element to orderedIti
			if (orderedIti.isEmpty())
			{
				orderedIti.add(currentIti);
			}
			else
			{

				// goes through orderedIti
				for (int index = 0; index < orderedIti.size(); index++)
				{

					// gets the total cost for orderedIti at the index and
					// currentIti
					// checks if the Itinerary to be added from itineraries is
					// less than the itinerary at the index in orderedIti
					double current = currentIti.totalCost();
					double ordered = orderedIti.get(index).totalCost();
					if (current <= ordered)
					{
						orderedIti.add(index, currentIti);

						// ends the for loop
						index = orderedIti.size();
					}

					// if it reaches the end of the orderedIti then adds the
					// currentIti
					// to the end of orderedIti
					else if (index + 1 == orderedIti.size())
					{
						orderedIti.add(currentIti);

						// ends the for loop
						index = orderedIti.size();
					}

				}
			}

		}
		return orderedIti;

	}

	/**
	 * books an givenItenrary
	 * 
	 * @param givenItinerary
	 *            the Itinerary to be added to be booked
	 */
	public void bookItinerary(Itinerary givenItinerary)
	{
		this.bookedItineraries.add(givenItinerary);
		givenItinerary.decreaseNumSeats();

	}

	/**
	 * given an infoType and a new value it changes the infoType to the value of
	 * newInfo
	 * 
	 * @param infoType
	 *            type of the info to be changed. Must be one of "firstName",
	 *            "lastName","email","address","creditCardNum","expiryDate".
	 * @param newInfo
	 *            new value to be set
	 */
	public void editBillingInfo(String infoType, String newInfo)
	{
		if (infoType.equals("firstName"))
		{
			this.billInfo.setFirstName(newInfo);
		}
		else if (infoType.equals("lastName"))
		{
			this.billInfo.setLastName(newInfo);
		}
		else if (infoType.equals("email"))
		{
			this.billInfo.setEmail(newInfo);
		}
		else if (infoType.equals("address"))
		{
			this.billInfo.setAddress(newInfo);
		}
		else if (infoType.equals("creditCardNum"))
		{
			this.billInfo.setCreditCardNum(newInfo);
		}
		else if (infoType.equals("expiryDate"))
		{
			this.billInfo.setExpiryDate(newInfo);
		}
	}

	/**
	 * Returns the billing info object.
	 * 
	 * @return the billInfo returns the billing info object.
	 */
	public BillingInfo getBillInfo()
	{
		return billInfo;
	}

	/**
	 * returns the clients info as a string of the format
	 * LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
	 */
	@Override
	public String toString()
	{
		return this.billInfo.toString();
	}

	@SuppressLint("NewApi")
	public void saveToFile(String filePathFlights, String filePathClients)
	{
		try (OutputStream fileFlights = new FileOutputStream(filePathFlights);
				OutputStream bufferFlights = new BufferedOutputStream(
						fileFlights);
				ObjectOutput outputFlights = new ObjectOutputStream(
						bufferFlights);)
		{
			outputFlights.writeObject(outputFlights);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
