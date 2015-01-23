package pIIIsoln;

import java.util.Date;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;

public class MainSystem implements Serializable
{

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 5248662145827601929L;
	// to store information neatly
	private Map<String, List<Flight>>	flights;
	private Map<String, Client>			clients;
	private Map<String, Admin>			admins;
	private Set<String>					locations;

	// constructor
	/**
	 * Constructor to make MainSystem Object. Will store flights and clients
	 * 
	 * @throws IOException
	 */
	public MainSystem(File flightFile, File clientFile, File adminFile)
			throws IOException
	{
		flights = new HashMap<String, List<Flight>>();
		clients = new HashMap<String, Client>();
		admins = new HashMap<String, Admin>();
		locations = new HashSet<String>();

		if (flightFile.exists())
		{
			this.loadFlightsFile(flightFile.getPath());
		}
		else
		{
			flightFile.createNewFile();
		}

		if (clientFile.exists())
		{
			this.loadClientsFile(clientFile.getPath());
		}
		else
		{
			clientFile.createNewFile();
		}

		if (adminFile.exists())
		{
			this.loadAdminsFile(adminFile.getPath());
		}
		else
		{
			adminFile.createNewFile();
		}

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
			String address, String creditCardNum, String expiryDate)
	{
		clients.put(email, new Client(firstName, lastName, email, address,
				creditCardNum, expiryDate));
	}
	
	/**
	 * Creates a client with the given parameters. Will store in a List<Client>
	 * 
	 * @param firstName
	 *            first name of admin
	 * @param lastName
	 *            last name of admin
	 * @param email
	 *            email of admin
	 * @param address
	 *            address of admin
	 * @param creditCardNum
	 *            credit card number of admin
	 * @param expiryDate
	 *            expiry date for admin
	 */
	public void createAdmin(String firstName, String lastName, String email,
			String address, String creditCardNum, String expiryDate)
	{
		admins.put(email, new Admin(firstName, lastName, email, address,
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
			String destination)
	{
		try
		{
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
			// removes itineraries that don't have available seats anymore
			itineraries = validBySeats(itineraries);
			// make a set to remove duplicates then add it back to lists
			HashSet<List<Flight>> h = new HashSet<List<Flight>>();
			for (List<Flight> itinerary : itineraries)
			{
				h.add(itinerary);
			}
			itineraries.clear();
			itineraries.addAll(h);
			// create itinerary objects
			List<Itinerary> itinerariesObj = createItinerary(itineraries);
			return itinerariesObj;
		}
		catch (ParseException e)
		{
			// get user know that format is invalid
			e.printStackTrace();
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
			List<List<Flight>> itineraries)
	{
		List<List<Flight>> validItinerary = new ArrayList<List<Flight>>();
		try
		{
			// take each itinerary one at a time
			for (List<Flight> itinerary : itineraries)
			{
				// get the first flight of the itinerary and make a date object
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String firstDateString = itinerary.get(0)
						.getDepartureDateTime();
				Date firstDate = sdf.parse(firstDateString);
				// compare the date to make sure it is valid
				if ((firstDate.after(date)) || (firstDate.equals(date)))
				{
					validItinerary.add(itinerary);
				}

			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
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
	private List<List<Flight>> validByDateTime(List<List<Flight>> itineraries)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
		List<List<Flight>> validItineraries = new ArrayList<List<Flight>>();
		try
		{
			long sixHoursInSec = 21600;
			// take each itinerary one at a time
			for (List<Flight> itinerary : itineraries)
			{
				boolean isValid = true;
				// use index loop to compare to next flight
				for (int i = 0; i < itinerary.size() - 1; i++)
				{
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
							|| (diff > sixHoursInSec))
					{
						isValid = false;
					}
				}
				if (isValid)
				{
					validItineraries.add(itinerary);
				}
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return validItineraries;
	}

	/**
	 * Removes itineraries that has flights with no seats available
	 * @param itineraries the itinerary list to check
	 * @return the new itinerary list with itineraries with no seats removed
	 */
	private List<List<Flight>> validBySeats(List<List<Flight>> itineraries)
	{
		List<List<Flight>> validItineraries = new ArrayList<List<Flight>>();

		for (List<Flight> itinerary : itineraries)
		{
			boolean valid = true;
			for (Flight flight : itinerary)
			{
				if (flight.getNumSeats() < 1)
				{
					valid = false;
				}
			}
			if (valid)
			{
				validItineraries.add(itinerary);
			}
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
			String destination)
	{

		// get all flights that departure from origin
		List<Flight> originFlights = flights.get(origin);
		List<Flight> itinerary;
		List<List<Flight>> itineraries = new ArrayList<List<Flight>>();
		// hashmap returns null when you are trying to get key that doesn't
		// exist
		if (originFlights == null)
		{

			return itineraries;
		}
		// take one flight at a time
		for (Flight flight : originFlights)
		{
			// base case: flight takes to destination
			if (flight.getDestination().equals(destination))
			{
				itinerary = new ArrayList<Flight>();
				itinerary.add(flight);
				// add to our itinerary
				itineraries.add(itinerary);
			}
			else
			{
				// temporarily remove key so we dont have a round trip
				String previouskey = origin;
				List<Flight> removedFlights = flights.get(origin);
				flights.remove(origin);
				// recursively make itinerary for next flights if possible
				List<List<Flight>> flight2 = makeAllItineraries(
						flight.getDestination(), destination);
				flights.put(previouskey, removedFlights);
				// add the first flight that takes us to flight2
				for (List<Flight> f : flight2)
				{
					f.add(0, flight);
					itineraries.add(f);
				}
			}
		}
		return itineraries;
	}

	/**
	 * Uploads information of clients from a csv file
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void uploadClientInfo(String path)
	{
		try
		{
			BufferedReader newFile = new BufferedReader(new FileReader(path));
			String newLine = "";
			// first line to read
			newLine = newFile.readLine();
			// will be null when reaches end of file
			while (newLine != null)
			{
				// split information
				String[] tokens = newLine.split(",");
				// create and add client so we can use them
				Client newClient = new Client(tokens[1], tokens[0], tokens[2],
						tokens[3], tokens[4], tokens[5]);
				clients.put(tokens[2], newClient);
				newLine = newFile.readLine();
			}
			newFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Uploads information of flights from a csv file
	 * 
	 * @param path
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public void uploadFlightInfo(String path)
	{
		try
		{
			BufferedReader newFile = new BufferedReader(new FileReader(path));
			String newLine = "";
			// first line to read
			newLine = newFile.readLine();
			// will only be null when reaches end of file
			while (newLine != null)
			{
				// split information
				String[] tokens = newLine.split(",");
				// use information to add Flight object
				addFlight(new Flight(tokens[0], tokens[1], tokens[2],
						tokens[3], tokens[4], tokens[5],
						Double.parseDouble(tokens[6]),
						Integer.parseInt(tokens[7])));
				locations.add(tokens[4]);
				locations.add(tokens[5]);
				newLine = newFile.readLine();
			}
			newFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Uploads admin information to the system
	 * @param path the file path for the admin csv file
	 */
	public void uploadAdminInfo(String path)
	{
		try
		{
			BufferedReader newFile = new BufferedReader(new FileReader(path));
			String newLine = "";
			// first line to read
			newLine = newFile.readLine();
			// will be null when reaches end of file
			while (newLine != null)
			{
				// split information
				String[] tokens = newLine.split(",");
				// create and add client so we can use them
				Client newAdmin = new Admin(tokens[1], tokens[0], tokens[2],
						tokens[3], tokens[4], tokens[5]);
				clients.put(tokens[2], newAdmin);
				newLine = newFile.readLine();
			}
			newFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * create Itinerary objects
	 * 
	 * @param itineraries
	 *            List of List of Flight
	 * @return List of Itinerary objects
	 */
	public List<Itinerary> createItinerary(List<List<Flight>> itineraries)
	{
		List<Itinerary> itineraryObj = new ArrayList<Itinerary>();
		for (List<Flight> itinerary : itineraries)
		{
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
			String destination, double cost, int numSeats)
	{
		return new Flight(flightNumber, departureDateTime, arrivalDateTime,
				airline, origin, destination, cost, numSeats);
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
	public String getFlight(String date, String origin, String destination)
	{
		String ret = "";
		try
		{
			// make date into start of day object and end of day object
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startOfDay = sdf.parse(date + " 00:00");
			Date endOfDay = sdf.parse(date + " 23:59");
			if (flights.containsKey(origin))
			{
				for (Flight flight : flights.get(origin))
				{
					// create date object of flight to compare to startOfDay,
					// endOfDay
					Date dep = sdf.parse(flight.getDepartureDateTime());
					// meeting the conditions will add it to the String ret
					if ((dep.after(startOfDay) || dep.equals(startOfDay))
							&& ((dep.before(endOfDay) || dep.equals(endOfDay))))
					{
						if (flight.getDestination().equals(destination))
						{
							ret += flight + "\n";
						}

					}
				}
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();

		}
		return ret;

	}

	/**
	 * add Flights to HashMap
	 * 
	 * @param flight
	 *            the flight object to add
	 */
	private void addFlight(Flight flight)
	{
		Set<String> keys = flights.keySet();
		String key = flight.getOrigin();
		boolean added = false;
		// check if key already exist for the Flight
		for (String k : keys)
		{
			if (key.equals(k))
			{
				added = true;
				// take the values and add the new Flight
				List<Flight> f = new ArrayList<Flight>();
				f.add(flight);
				for (Flight eachFlight : flights.get(key))
				{
					f.add(eachFlight);
				}
				flights.put(key, f);
			}
		}
		// create new key and values for Flight
		if (!added)
		{
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
	public Client getClient(String email)
	{
		return clients.get(email);

	}

	/**
	 * Gets the Admin that matches the email
	 * @param email the email of the Admin
	 * @return the corresponding Admin object
	 */
	public Admin getAdmin(String email)
	{
		return admins.get(email);
	}

	/**
	 * Saves the flight information in system to the file path
	 * @param filePathFlights the file path to save flights to
	 */
	@SuppressLint("NewApi")
	public void saveToFlightsFile(String filePathFlights)
	{
		try (OutputStream fileFlights = new FileOutputStream(filePathFlights);
				OutputStream bufferFlights = new BufferedOutputStream(
						fileFlights);
				ObjectOutput outputFlights = new ObjectOutputStream(
						bufferFlights);)
		{
			outputFlights.writeObject(flights);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Saves the client information to the given file path
	 * @param filePathClients the path to save clients to
	 */
	@SuppressLint("NewApi")
	public void saveToClientsFile(String filePathClients)
	{
		try (OutputStream fileClients = new FileOutputStream(filePathClients);
				OutputStream bufferClients = new BufferedOutputStream(
						fileClients);
				ObjectOutput outputClients = new ObjectOutputStream(
						bufferClients);)
		{
			outputClients.writeObject(clients);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Saves the admin information to the given file path
	 * @param filePathAdmins the path to save admins to
	 */
	@SuppressLint("NewApi")
	public void saveToAdminsFile(String filePathAdmins)
	{
		try (OutputStream fileAdmins = new FileOutputStream(filePathAdmins);
				OutputStream bufferAdmins = new BufferedOutputStream(fileAdmins);
				ObjectOutput outputAdmins = new ObjectOutputStream(bufferAdmins);)
		{
			outputAdmins.writeObject(admins);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Loads the saved flight information from the file path
	 * @param filePathFlights the file path to load flights from
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	private void loadFlightsFile(String filePathFlights)
	{
		try (InputStream fileFlights = new FileInputStream(filePathFlights);
				InputStream bufferFlights = new BufferedInputStream(fileFlights);
				ObjectInput inputFlights = new ObjectInputStream(bufferFlights);)
		{
			flights = (Map<String, List<Flight>>) inputFlights.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Loads the saved client information from the file path
	 * @param filePathClients the file path to load clients from
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	private void loadClientsFile(String filePathClients)
	{
		try (InputStream fileClients = new FileInputStream(filePathClients);
				InputStream bufferClients = new BufferedInputStream(fileClients);
				ObjectInput inputClients = new ObjectInputStream(bufferClients);)
		{
			clients = (Map<String, Client>) inputClients.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Returns the list of locations from system
	 * @return the list of locations
	 */
	public List<String> getLocations()
	{
		return new ArrayList<String>(this.locations);
	}

	/**
	 * Loads the saved Admin information from the file path
	 * @param filePathAdmins the file path for the saved Admin information
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	private void loadAdminsFile(String filePathAdmins)
	{
		try (InputStream fileAdmins = new FileInputStream(filePathAdmins);
				InputStream bufferAdmins = new BufferedInputStream(fileAdmins);
				ObjectInput inputAdmins = new ObjectInputStream(bufferAdmins);)
		{
			admins = (Map<String, Admin>) inputAdmins.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Saves passwords to a given file
	 * @param file the file to save passwords to
	 * @throws IOException
	 */
	public void savePasswords(File file) throws IOException
	{
		file.createNewFile();
		try
		{
			@SuppressWarnings("resource")
			BufferedWriter txtFile = new BufferedWriter(new FileWriter(
					file.getPath()));
			txtFile.write("Client's Username Password");
			Set<String> clientKeys = clients.keySet();
			for (String clientKey : clientKeys)
			{
				txtFile.newLine();
				txtFile.write(clients.get(clientKey).getEmail() + " "
						+ clients.get(clientKey).getPassword());
			}
			txtFile.newLine();
			txtFile.write("Admin's Username Password");
			Set<String> adminKeys = admins.keySet();
			for (String adminKey : adminKeys)
			{
				txtFile.newLine();
				txtFile.write(admins.get(adminKey).getEmail() + " "
						+ clients.get(adminKey).getPassword());
			}
			txtFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Loads passwords and sets them in corresponding users
	 * @param path the path to the password files
	 */
	public void loadPasswords(String path)
	{
		try
		{
			BufferedReader newFile = new BufferedReader(new FileReader(path));
			String newLine = "";
			// first line to read
			newLine = newFile.readLine();
			// will be null when reaches end of file
			while (newLine != null)
			{
				// split information
				String[] tokens = newLine.split(",");
				// grabs the admin or client based on the first token
				// and sets the password to the object
				if (tokens[0].equals("Admin")) {
					if (admins.containsKey(tokens[1])){
						Admin changeAdmin = admins.get(tokens[1]);
						changeAdmin.setPassword(tokens[2]);
						admins.put(tokens[1], changeAdmin);
					}
				}
				else if (tokens[0].equals("Client")) {
					if (clients.containsKey(tokens[1])){
						Client changeClient = clients.get(tokens[1]);
						changeClient.setPassword(tokens[2]);
						clients.put(tokens[1], changeClient);
					}
				}
			}
			newFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}