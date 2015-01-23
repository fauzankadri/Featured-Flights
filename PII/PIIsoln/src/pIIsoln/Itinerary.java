package pIIsoln;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date Created: 1/11/2014 Last Edit: 3/11/2014 Status: Production
 * 
 * @author Amir Emami
 * @version 0.5
 */

public abstract class Itinerary {
	// Creates a new list of flights
	private List<Flight> flights = new ArrayList<Flight>();

	/**
	 * Returns the total cost of the all the flights users takes.
	 * 
	 * @param flightList
	 *            List of all the flights the user takes.
	 * @return Total cost of the all the flights.
	 */
	public Itinerary(List<Flight> flights) {
		this.flights = flights;
	}

	/**
	 * Returns the total cost of the itinerary.
	 * 
	 * @return returns the total cost of the itinerary.
	 */
	public double totalCost() {
		// Total cost of all the flights
		double totalCost = 0;

		// Increments the total cost by the cost of the flights taken
		for (Flight flight : this.flights) {
			totalCost += flight.getCost();
		}

		return totalCost;
	}

	/**
	 * Returns the total travel time of the all the flights users takes.
	 * 
	 * @param flightList
	 *            List of all the flights the user takes.
	 * @return Total travel time of the all the flights.
	 */
	public String totalTravelTime() {
		// Total cost of all the flights
		int hours = this.getTotalHours();
		int minutes = this.getTotalMinutes();

		// Initialising hours and minutes
		String strHours = "" + hours;
		String strMinutes = "" + minutes;
		// Display 0 in case hours is in [0,9]
		if (hours < 10) {
			strHours = "0" + hours;
		}
		// Display 0 in case minutes is in [0,9]
		if (minutes < 10) {
			strMinutes = "0" + minutes;
		}
		// Adds the : between hour and minute
		String result = strHours + ":" + strMinutes;

		return result;
	}

	/**
	 * Returns the total hour of the itinerary.
	 * 
	 * @return returns the total hour of the itinerary.
	 */
	public int getTotalHours() {
		try {
			// Separates and formats the time
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Flight originFlight = flights.get(0);
			Flight lastFlight = flights.get(flights.size() - 1);

			// Stores departure and arrival time
			Date dep = sdf.parse(originFlight.getDepartureDateTime());
			Date arrive = sdf.parse(lastFlight.getArrivalDateTime());

			// Calculates the difference
			long diff = arrive.getTime() - dep.getTime();

			return (int) ((diff / (1000 * 60 * 60)));
		} catch (ParseException e) {
			System.out.print("Invalid Format");
		}
		return -1;
	}

	/**
	 * Returns the total minutes of the itinerary.
	 * 
	 * @return returns the total minutes of the itinerary.
	 */
	public int getTotalMinutes() {
		try {
			// Separates and formats the time
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Flight originFlight = flights.get(0);
			Flight lastFlight = flights.get(flights.size() - 1);

			// Stores departure and arrival time
			Date dep = sdf.parse(originFlight.getDepartureDateTime());
			Date arrive = sdf.parse(lastFlight.getArrivalDateTime());

			// Calculates the difference
			long diff = arrive.getTime() - dep.getTime();

			return (int) ((diff / (1000 * 60))) - this.getTotalHours() * 60;
		} catch (ParseException e) {
			System.out.println("Invalid Format");
		}
		return -1;
	}

	/**
	 * Adds a new flight to the list of flights.
	 * 
	 * @param flight
	 *            it's the flight that needs to be added to the list of flights.
	 */

	public void addFlight(Flight flight) {
		flights.add(flight);
	}

	/**
	 * Removes the flight from the list of flights.
	 * 
	 * @param flight
	 *            it's the flight that needs to be removed from the list of
	 *            flights.
	 */
	public void removeFlight(Flight flight) {
		flights.remove(flights.indexOf(flight));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String flightString = "";

		// Formating the data
		for (Flight flight : flights) {
			flightString += flight.getFlightNumber() + ","
					+ flight.getDepartureDateTime() + ","
					+ flight.getArrivalDateTime() + "," + flight.getAirline()
					+ "," + flight.getOrigin() + "," + flight.getDestination()
					+ "\n";
		}
		return flightString + totalCost() + "\n" + totalTravelTime();
	}
}
