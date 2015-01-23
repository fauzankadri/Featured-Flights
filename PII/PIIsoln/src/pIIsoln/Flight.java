package pIIsoln;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created On: 1/11/2014 Last Edit: 5/11/2014 Represents the flight information.
 * Status: Production
 * 
 * @author Amir Emami
 * @version 0.2
 */
public class Flight {
	// Flight Number of the flight
	private String flightNumber;

	// Date of the departure in format of YYYY-MM-DD HH:MM
	private String departureDateTime;

	// Date of the arrival in format of YYYY-MM-DD HH:MM
	private String arrivalDateTime;

	// Name of the airline
	private String airline;

	// Origin of the flight
	private String origin;

	// Destination of the flight
	private String destination;

	// Total cost of the flight in $dollars
	private double cost;
	
	/****************************************************************************
	 * Constructor: sets up this flight using information specified.
	 * 
	 * @param flightNumber Flight Number of the flight
	 * @param departureDate Date and time of the departure in format of YYYY-MM-DD HH:MM
	 * @param arrivalDateTime Date and time of the arrival in format of YYYY-MM-DD HH:MM
	 * @param airline Name of the airline
	 * @param origin Origin of the flight
	 * @param destination Destination of the flight
	 * @param cost Total cost of the flight in $dollars
	 * @param travelTime Total travel time of the flight in minutes
	 ************************************************************************/
	public Flight(String flightNumber, String departureDateTime,
			String arrivalDateTime, String airline, String origin,
			String destination, double cost) {
		this.flightNumber = flightNumber;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.airline = airline;
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
	}

	/**
	 * Returns the flight Number of the flight
	 * 
	 * @return the flightNumber
	 */
	public String getFlightNumber() 
	{
		return flightNumber;
	}

	/**
	 * Returns the date of the departure in format of YYYY-MM-DD HH:MM
	 * 
	 * @return the departureDateTime
	 */
	public String getDepartureDateTime() 
	{
		return departureDateTime;
	}

	/**
	 * Returns the date of the arrival in format of YYYY-MM-DD HH:MM
	 * 
	 * @return the arrivalDateTime
	 */
	public String getArrivalDateTime() {
		return arrivalDateTime;
	}

	/**
	 * Returns the name of the airline
	 * 
	 * @return the airline
	 */
	public String getAirline() 
	{
		return airline;
	}

	/**
	 * Returns the origin of the flight
	 * 
	 * @return the origin
	 */
	public String getOrigin() 
	{
		return origin;
	}

	/**
	 * Returns the destination of the flight
	 * 
	 * @return the destination
	 */
	public String getDestination() 
	{
		return destination;
	}

	/**
	 * Returns the total cost of the flight in dollars
	 * 
	 * @return the cost
	 */
	public double getCost() 
	{
		return cost;
	}

	/**
	 * Returns the total travel time of the flight in minutes
	 * 
	 * @return the travelTime Total travel time
	 * @throws ParseException 
	 */
	public String getTravelTime() throws ParseException 
	{
		// Get hours and minutes
		int hours = this.getHours();
		int minutes = this.getMinutes();
		
		// Initialising result
		String result = "";
		
		// Display 0 in case hours is in [0,9]
		if(hours < 10)
		{
			result += "0";
		}
		
		// Add hours and :
		result += hours;
		result += ":";
		
		// Display 0 in case minutes is in [0,9]
		if(minutes < 10)
		{
			result += "0";
		}
		
		// Add to the result
		result += minutes;
		
		return result;
	}
	
	/**
	 * Returns the hours of the flight without minutes.
	 * @return the the hours of the flight without minutes
	 */
	public int getHours()
	{
		try
		{
			// Separating time and date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date depDate = sdf.parse(this.departureDateTime);
			Date arriveDate = sdf.parse(this.arrivalDateTime);
		
			// Calculates the difference
			long diff = arriveDate.getTime() - depDate.getTime();
		
			return (int) ((diff/(1000*60*60)));
		} catch (ParseException e)
		{
			return -1;
		}
	}
	
	/**
	 * Returns only the minutes of the flight without hour.
	 * @return
	 */
	public int getMinutes()
	{
		try
		{
			// Separating time and date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date depDate = sdf.parse(this.departureDateTime);
			Date arriveDate = sdf.parse(this.arrivalDateTime);
		
			// Calculates the difference
			long diff = arriveDate.getTime() - depDate.getTime();
			
			return (int) ((diff/(1000*60))) - this.getHours()*60;
		}catch (ParseException e)
		{
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() 
	 * Returns the information about flight as a string
	 */
	@Override
	public String toString() {
		return flightNumber + "," + departureDateTime + "," + arrivalDateTime
				+ "," + airline + "," + origin + "," + destination + "," + cost;
	}

}
