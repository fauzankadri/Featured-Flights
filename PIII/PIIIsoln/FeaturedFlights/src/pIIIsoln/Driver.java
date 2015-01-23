package pIIIsoln;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacky
 * 
 */
public class Driver
{

	private static MainSystem	testing;
	private static Client		tester;

	static
	{
		// testing = new MainSystem();
		tester = new Client();
	}

	/**
	 * Uploads client information into the application, given the file path as
	 * string.
	 * 
	 * @param the
	 *            file path for client information
	 */
	public static void uploadClientInfo(String path)
	{
		testing.uploadClientInfo(path);
	}

	/**
	 * Uploads flight information into the application, given the file path as a
	 * string
	 * 
	 * @param file
	 *            path for flight information
	 */
	public static void uploadFlightInfo(String path)
	{
		testing.uploadFlightInfo(path);
	}

	/**
	 * Returns the information stored for the client with the given email
	 * 
	 * @param The
	 *            email address of the requested client
	 * @return A string with the client information
	 */
	public static String getClient(String email)
	{
		return testing.getClient(email).toString();
	}

	/**
	 * Returns all flights that matches the date, origin and destination
	 * 
	 * @param the
	 *            date, origin and destination to find flights that match the
	 *            requirements
	 * @return A string with all flights that matches the parameters
	 */
	public static String getFlights(String date, String origin,
			String destination)
	{
		return testing.getFlight(date, origin, destination);
	}

	/**
	 * Returns all itineraries that depart from origin on date and arrive at
	 * destination
	 * 
	 * @param a
	 *            date, origin and destination to find itineraries
	 * @return A string that has all itineraries from origin to destination at
	 *         date
	 */
	public static String getItineraries(String date, String origin,
			String destination)
	{
		List<Itinerary> itineraries = testing.makeAllItineraries(date, origin,
				destination);
		String itinerary = "";
		for (Itinerary each : itineraries)
		{
			itinerary += each.toString() + "\n";
		}
		return itinerary;
	}

	/**
	 * Returns all itineraries that depart from origin on date and arrive at
	 * destination, sorted by the total cost in non-decreasing order
	 * 
	 * @param a
	 *            date, origin and destination for itineraries sorted by cost
	 * @return A string that has all itineraries that matches the parameters
	 *         sorted by cost
	 */
	public static String getItinerariesSortedByCost(String date, String origin,
			String destination)
	{
		ArrayList<Itinerary> testItinerary = (ArrayList<Itinerary>) testing
				.makeAllItineraries(date, origin, destination);
		ArrayList<Itinerary> newTestItinerary = tester
				.orderByCost(testItinerary);
		String itinerary = "";
		for (Itinerary each : newTestItinerary)
		{
			itinerary += each.toString() + "\n";
		}
		return itinerary;
	}

	/**
	 * Returns all itineraries that depart from origin on date and arrive at
	 * destination, sorted by the total travel time in non-decreasing order
	 * 
	 * @param a
	 *            date, origin and destination for itineraries sorted by time
	 * @return A string that has all itineraries that matches the parameters
	 *         sorted by time
	 */
	public static String getItinerariesSortedByTime(String date, String origin,
			String destination)
	{
		ArrayList<Itinerary> testItinerary = (ArrayList<Itinerary>) testing
				.makeAllItineraries(date, origin, destination);
		ArrayList<Itinerary> newTestItinerary = tester
				.orderByTime(testItinerary);
		String itinerary = "";
		for (Itinerary each : newTestItinerary)
		{
			itinerary += each.toString() + "\n";
		}
		return itinerary;
	}

	/*
	public static void main(String[] args)
	{
		//TODO: Remove after testing
		Driver.uploadFlightInfo("C:\\Users\\Fauzan\\Desktop\\flight_test.csv");
		System.out.println(Driver.getItinerariesSortedByTime("2014-12-14",
				"Toronto", "Shanghai"));
	}
	*/
}
