package pIIIsoln;

import android.annotation.SuppressLint;

import java.io.Serializable;
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
@SuppressLint("SimpleDateFormat")
public class Flight implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3796620970642311577L;

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
	
	// Number of seats on the flight
	private int numSeats;
	
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
	 * @param numSeats Number of seats on the flight
	 ************************************************************************/
	public Flight(String flightNumber, String departureDateTime,
			String arrivalDateTime, String airline, String origin,
			String destination, double cost, int numSeats) {
		this.flightNumber = flightNumber;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.airline = airline;
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
		this.setNumSeats(numSeats);
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
	 * @return the destination of the flight
	 */
	public String getDestination() 
	{
		return destination;
	}

	/**
	 * Returns the total cost of the flight in dollars
	 * 
	 * @return the cost of the flight
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
	 * @return the minute part of the flight
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

	/**
	 * @return the numSeats
	 */
	public int getNumSeats() {
		return numSeats;
	}

	/**
	 * @param numSeats the numSeats to set
	 */
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	/**
	 * Sets the flight number to the parameter
	 * @param flightNumber the new flight number
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * Sets the departure date and time
	 * @param departureDateTime the new departure date and time
	 */
	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	/**
	 * Sets the arrival date and time
	 * @param arrivalDateTime the new arrival date and time
	 */
	public void setArrivalDateTime(String arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	/**
	 * Sets the airline for the flight
	 * @param airline the new airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * Sets the origin of the flight
	 * @param origin the new origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Sets the new destination of the flight
	 * @param destination the new destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Sets the new cost of the flight
	 * @param cost the new cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * Adds one seat to the flight
	 */
	public void addOneSeat(){
		this.numSeats++;
	}
	
	/**
	 * Removes one seat from the flight
	 */
	public void removeOneSeat(){
		this.numSeats--;
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
