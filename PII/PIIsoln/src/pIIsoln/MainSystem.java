package pIIsoln;

import java.util.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainSystem {

	// to store information neatly
	private Map<String, List<Flight>> flights;
	private List<Client> clients;

	// constructor
	/**
	 * Constructor to make MainSystem Object. Will store flights and clients
	 */
	public MainSystem() {
		flights = new HashMap<String, List<Flight>>();
		clients = new ArrayList<Client>();
	}

	/**
	 * Creates a client with the given parameters. Will store in a List<Client>
	 * 
	 * @param firstName
	 *            first name of client
	 * @param lastName
	 *            last name of client
	 * @param email
	 *            email of client
	 * @param address
	 *            address of client
	 * @param creditCardNum
	 *            credit card number of client
	 * @param expiryDate
	 *            expiry date for client
	 */
	public void createClient(String firstName, String lastName, String email,
			String address, String creditCardNum, String expiryDate) {
		clients.add(new Client(firstName, lastName, email, address,
				creditCardNum, expiryDate));
	}

	/**
	 * Uses all information to make valid Itineraries
	 * 
	 * @param date
	 *            date of departure to take flights
	 * @param origin
	 *            starting location
	 * @param destination
	 *            ending location
	 * @return List of Itinerary
	 */
	public List<Itinerary> makeAllItineraries(String date, String origin,
			String destination) {
		try {
			// standard format to use
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// make date into a Date object so we can compare to other dates
			Date newDate = sdf.parse(date + " 00:00");
			// makes all itineraries from origin to destination without checking
			// time
			List<List<Flight>> itineraries = makeAllItineraries(origin,
					destination);
			// removes itineraries that are invalid by dateTime
			itineraries = validByDateTime(itineraries);
			// removes itineraries that don't departure at the given date
			itineraries = validByDepDate(newDate, itineraries);
			// make a set to remove duplicates then add it back to lists
			HashSet<List<Flight>> h = new HashSet<List<Flight>>();
			for (List<Flight> itinerary : itineraries) {
				h.add(itinerary);
			}
			itineraries.clear();
			itineraries.addAll(h);
			// create itinerary objects
			List<Itinerary> itinerariesObj = createItinerary(itineraries);
			return itinerariesObj;
		} catch (ParseException e) {
			System.out.println("Invalid Format"); // get user know that format
													// is invalid
		}
		return null;
	}

	/**
	 * removes itineraries that don't departure at given date
	 * 
	 * @param date
	 *            date of departure
	 * @param itineraries
	 *            List of List of Flights
	 * @return itineraries that departure at date
	 */
	private List<List<Flight>> validByDepDate(Date date,
			List<List<Flight>> itineraries) {
		List<List<Flight>> validItinerary = new ArrayList<List<Flight>>();
		try {
			// take each itinerary one at a time
			for (List<Flight> itinerary : itineraries) {
				// get the first flight of the itinerary and make a date object
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String firstDateString = itinerary.get(0)
						.getDepartureDateTime();
				Date firstDate = sdf.parse(firstDateString);
				// compare the date to make sure it is valid
				if ((firstDate.after(date)) || (firstDate.equals(date))) {
					validItinerary.add(itinerary);
				}

			}
		} catch (ParseException e) {
			System.out.println("Invalid Format");
		}
		return validItinerary;
	}

	/**
	 * removes itinerary that are not valid by date and time and layover is more
	 * than 6 hours
	 * 
	 * @param itineraries
	 *            List of List of Flight
	 * @return itineraries that are valid by date and time and layover is less
	 *         than 6 hours
	 */
	private List<List<Flight>> validByDateTime(List<List<Flight>> itineraries) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
		List<List<Flight>> validItineraries = new ArrayList<List<Flight>>();
		try {
			long sixHoursInSec = 21600;
			// take each itinerary one at a time
			for (List<Flight> itinerary : itineraries) {
				boolean isValid = true;
				// use index loop to compare to next flight
				for (int i = 0; i < itinerary.size() - 1; i++) {
					Flight currentFlight = itinerary.get(i);
					Flight nextFlight = itinerary.get(i + 1);
					// create date objects to compare
					Date currentFlightArriveDateTime = sdf.parse(currentFlight
							.getArrivalDateTime());
					Date nextFlightDepDateTime = sdf.parse(nextFlight
							.getDepartureDateTime());
					// the difference in time between the current flight and
					// next flight
					long diff = (nextFlightDepDateTime.getTime() - currentFlightArriveDateTime
							.getTime()) / 1000;
					// conditions that make invalid itinerary
					if ((currentFlightArriveDateTime
							.after(nextFlightDepDateTime))
							|| (diff > sixHoursInSec)) {
						isValid = false;
					}
				}
				if (isValid) {
					validItineraries.add(itinerary);
				}
			}
		} catch (ParseException e) {
			System.out.println("Invalid Format");
		}
		return validItineraries;
	}

	/**
	 * makes all itineraries that go from origin to destination
	 * 
	 * @param origin
	 *            starting location
	 * @param destination
	 *            ending location
	 * @return List of List of Flights that go from origin to destination
	 */
	private List<List<Flight>> makeAllItineraries(String origin,
			String destination) {

		// get all flights that departure from origin
		List<Flight> originFlights = flights.get(origin);
		List<Flight> itinerary;
		List<List<Flight>> itineraries = new ArrayList<List<Flight>>();
		// hashmap returns null when you are trying to get key that doesn't
		// exist
		if (originFlights == null) {

			return itineraries;
		}
		// take one flight at a time
		for (Flight flight : originFlights) {
			// base case: flight takes to destination
			if (flight.getDestination().equals(destination)) {
				itinerary = new ArrayList<Flight>();
				itinerary.add(flight);
				// add to our itinerary
				itineraries.add(itinerary);
			} else {
				// temporarily remove key so we dont have a round trip
				String previouskey = origin;
				List<Flight> removedFlights = flights.get(origin);
				flights.remove(origin);
				// recursively make itinerary for next flights if possible
				List<List<Flight>> flight2 = makeAllItineraries(
						flight.getDestination(), destination);
				flights.put(previouskey, removedFlights);
				// add the first flight that takes us to flight2
				for (List<Flight> f : flight2) {
					f.add(0, flight);
					itineraries.add(f);
				}
			}
		}
		return itineraries;
	}

	/**
	 * Uploads information of flights from a csv file
	 * 
	 * @param path
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public void uploadFlightInfo(String path) {
		try {
			BufferedReader newFile = new BufferedReader(new FileReader(path));
			String newLine = "";
			// first line to read
			newLine = newFile.readLine();
			// will only be null when reaches end of file
			while (newLine != null) {
				// split information
				String[] tokens = newLine.split(",");
				// use information to add Flight object
				addFlight(new Flight(tokens[0], tokens[1], tokens[2],
						tokens[3], tokens[4], tokens[5],
						Double.parseDouble(tokens[6])));
				newLine = newFile.readLine();
			}
			newFile.close();
		} catch (IOException e) {
			System.out.println("Invalid Path");
		}
	}

	/**
	 * Uploads information of clients from a csv file
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void uploadClientInfo(String path) {
		try {
			BufferedReader newFile = new BufferedReader(new FileReader(path));
			String newLine = "";
			// first line to read
			newLine = newFile.readLine();
			// will be null when reaches end of file
			while (newLine != null) {
				// split information
				String[] tokens = newLine.split(",");
				// create and add client so we can use them
				Client newClient = new Client(tokens[1], tokens[0], tokens[2],
						tokens[3], tokens[4], tokens[5]);
				clients.add(newClient);
				newLine = newFile.readLine();
			}
			newFile.close();
		} catch (IOException e) {
			System.out.println("Invalid Path");
		}
	}

	/**
	 * create Itinerary objects
	 * 
	 * @param itineraries
	 *            List of List of Flight
	 * @return List of Itinerary objects
	 */
	public List<Itinerary> createItinerary(List<List<Flight>> itineraries) {
		List<Itinerary> itineraryObj = new ArrayList<Itinerary>();
		for (List<Flight> itinerary : itineraries) {
			itineraryObj.add(new OneWayTrip(itinerary));
		}
		return itineraryObj;
	}

	/**
	 * creates a Flight object
	 * 
	 * @param flightNumber
	 *            number of the flight
	 * @param departureDateTime
	 *            the date and time to departure
	 * @param arrivalDateTime
	 *            the date and time to arrive
	 * @param airline
	 *            the airline company
	 * @param origin
	 *            the location where flight will leave
	 * @param destination
	 *            the location where flight will land
	 * @param cost
	 *            the cost of taking this flight
	 * @return a Flight object
	 */
	public Flight createFlight(String flightNumber, String departureDateTime,
			String arrivalDateTime, String airline, String origin,
			String destination, double cost) {
		return new Flight(flightNumber, departureDateTime, arrivalDateTime,
				airline, origin, destination, cost);
	}

	/**
	 * get Flights given date, origin, destination
	 * 
	 * @param date
	 *            the date of flights departing
	 * @param origin
	 *            the origin of flights
	 * @param destination
	 *            the destination of flights
	 * @return String of Flights that meet the date, origin, destination
	 */
	public String getFlight(String date, String origin, String destination) {
		String ret = "";
		try {
			// make date into start of day object and end of day object
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startOfDay = sdf.parse(date + " 00:00");
			Date endOfDay = sdf.parse(date + " 23:59");
			if (flights.containsKey(origin)) {
				for (Flight flight : flights.get(origin)) {
					// create date object of flight to compare to startOfDay,
					// endOfDay
					Date dep = sdf.parse(flight.getDepartureDateTime());
					// meeting the conditions will add it to the String ret
					if ((dep.after(startOfDay) || dep.equals(startOfDay))
							&& ((dep.before(endOfDay) || dep.equals(endOfDay)))) {
						if (flight.getDestination().equals(destination)) {
							ret += flight + "\n";
						}

					}
				}
			}
		} catch (ParseException e) {
			System.out.println("Invalid format");

		}
		return ret;

	}

	/**
	 * add Flights to HashMap
	 * 
	 * @param flight
	 *            the flight object to add
	 */
	private void addFlight(Flight flight) {
		Set<String> keys = flights.keySet();
		String key = flight.getOrigin();
		boolean added = false;
		// check if key already exist for the Flight
		for (String k : keys) {
			if (key.equals(k)) {
				added = true;
				// take the values and add the new Flight
				List<Flight> f = new ArrayList<Flight>();
				f.add(flight);
				for (Flight eachFlight : flights.get(key)) {
					f.add(eachFlight);
				}
				flights.put(key, f);
			}
		}
		// create new key and values for Flight
		if (!added) {
			List<Flight> f = new ArrayList<Flight>();
			f.add(flight);
			flights.put(key, f);
		}
	}

	/**
	 * get the client that matches the unique email
	 * 
	 * @param email
	 *            the email of the client
	 * @return the Client object
	 */
	public Client getClient(String email) {
		for (Client client : clients) {
			if (client.getEmail().equals(email)) {
				return client;
			}
		}
		return null;

	}
}
