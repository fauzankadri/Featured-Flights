/**
 * 
 */
package pIIsoln;

import java.util.List;

/**
 * Represents the one way trip
 * Date Created: 2/11/2014
 * Last Edit: 5/11/2014
 * Status: Production
 * @author Amir Emami
 * @version 0.2
 *
 */
public class OneWayTrip extends Itinerary {

	/**
	 * Constructor: sets up one way trip using it's parents constructor. 
	 */
	public OneWayTrip(List<Flight> flights) {
		super(flights);
		
	}
}
