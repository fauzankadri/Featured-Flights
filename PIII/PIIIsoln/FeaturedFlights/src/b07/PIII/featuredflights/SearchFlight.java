package b07.PIII.featuredflights;

import pIIIsoln.Admin;
import pIIIsoln.Client;
import pIIIsoln.MainSystem;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SearchFlight extends ActionBarActivity {

	// Initializing
	private String email;
	private Client client;
	private Admin admin;
	private Client user;
	private MainSystem main;
	private String flightDate;
	private String departureLocation;
	private String destinationLocation;
	public static final String MAIN_SYSTEM_KEY = Messages
			.getString("MainActivity.0");
	public static final String FLIGHT_DATE_KEY = Messages
			.getString("SearchFlight.0");
	public static final String DEPARTURE_LOCATION_KEY = Messages
			.getString("SearchFlight.1");
	public static final String DESTINATION_LOCATION_KEY = Messages
			.getString("SearchFlight.2");
	public static final String EMAIL = Messages.getString("MainActivity.3");
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_flight);

		new Thread(new Runnable() {
			public void run() {
				// Getting the information from previous activity
				Intent prevousIntent = getIntent();
				Bundle previousBundle = prevousIntent.getExtras();
				main = (MainSystem) previousBundle.get(MAIN_SYSTEM_KEY); //$NON-NLS-1$
				email = (String) previousBundle.get(EMAIL);
				client = main.getClient(email);
				admin = main.getAdmin(email);
				if (client != null){
					user = client;
				}
				else{
					user = admin;
				}
				main = (MainSystem) previousBundle.get(Messages
						.getString("MainActivity.0"));

				flightDate = (String) previousBundle.get(FLIGHT_DATE_KEY);
				departureLocation = (String) previousBundle
						.get(DEPARTURE_LOCATION_KEY);
				destinationLocation = (String) previousBundle
						.get(DESTINATION_LOCATION_KEY);
				handler.post(new Runnable() {
					public void run() {

						// TODO: Remove after testing
						Toast.makeText(
								SearchFlight.this,
								"date:" + flightDate + "\nDeparture:"
										+ departureLocation + "\nDestination"
										+ destinationLocation,
								Toast.LENGTH_SHORT).show();
					}

				});
			}

		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_flight, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Select date button
	 * 
	 * @param view
	 */
	public void selectDateTime(View view) {
		new Thread(new Runnable() {
			public void run() {
				// Creating and passing the information
				Intent selceDateTimeIntent = new Intent(SearchFlight.this,
						SelectDateAndTime.class);

				selceDateTimeIntent.putExtra(
						Messages.getString("MainActivity.1"), user);
				selceDateTimeIntent.putExtra(
						Messages.getString("MainActivity.0"), main);

				// Moves to select Date And Time
				startActivity(selceDateTimeIntent);
			}

		}).start();
	}

	/**
	 * Choose departure location button
	 * 
	 * @param view
	 */
	public void chooseDepartureLocation(View view) {

		new Thread(new Runnable() {
			public void run() {
				// In case the user haven't selected a date
				if (flightDate != null) {
					// Creating and passing the information
					Intent chooseDepartureLocationIntent = new Intent(
							SearchFlight.this, ChooseDepartureLocation.class);
					chooseDepartureLocationIntent.putExtra(
							Messages.getString("MainActivity.1"), user);
					chooseDepartureLocationIntent.putExtra(
							Messages.getString("MainActivity.0"), main);
					chooseDepartureLocationIntent.putExtra(FLIGHT_DATE_KEY,
							flightDate);

					// Moves to select Date And Time
					startActivity(chooseDepartureLocationIntent);
				} else {

					handler.post(new Runnable() {
						public void run() {

							// Display message
							Toast.makeText(SearchFlight.this,
									"Please Select Date First",
									Toast.LENGTH_SHORT).show();
						}

					});

				}

			}

		}).start();

	}

	/**
	 * View flight button
	 * 
	 * @param view
	 */
	public void ViewFlight(View view) {

		new Thread(new Runnable() {
			public void run() {
				if (flightDate != null && departureLocation != null
						&& destinationLocation != null) {
					// Creating and passing the information
					Intent viewFlightIntent = new Intent(SearchFlight.this,
							ViewFlight.class);
					viewFlightIntent.putExtra(
							Messages.getString("MainActivity.1"), user);
					viewFlightIntent.putExtra(
							Messages.getString("MainActivity.0"), main);
					viewFlightIntent.putExtra(FLIGHT_DATE_KEY, flightDate);
					viewFlightIntent.putExtra(DEPARTURE_LOCATION_KEY,
							departureLocation);
					viewFlightIntent.putExtra(DESTINATION_LOCATION_KEY,
							destinationLocation);

					// Moves to select Date And Time
					startActivity(viewFlightIntent);
				} else {
					handler.post(new Runnable() {
						public void run() {
							// Display message
							Toast.makeText(SearchFlight.this,
									"Please Select Destination Location First",
									Toast.LENGTH_SHORT).show();
						}

					});
				}
			}

		}).start();

	}

	/**
	 * Choose destination button
	 * 
	 * @param view
	 */
	public void chooseDestination(View view) {

		new Thread(new Runnable() {
			public void run() {
				if (flightDate != null && departureLocation != null) {
					// Creating and passing the information
					Intent ChooseDestinatioLocationIntent = new Intent(
							SearchFlight.this, ChooseDestinationLocation.class);
					ChooseDestinatioLocationIntent.putExtra(
							Messages.getString("MainActivity.1"), user);
					ChooseDestinatioLocationIntent.putExtra(
							Messages.getString("MainActivity.0"), main);
					ChooseDestinatioLocationIntent.putExtra(FLIGHT_DATE_KEY,
							flightDate);
					ChooseDestinatioLocationIntent.putExtra(
							DEPARTURE_LOCATION_KEY, departureLocation);

					// Moves to select Date And Time
					startActivity(ChooseDestinatioLocationIntent);
				} else {
					handler.post(new Runnable() {
						public void run() {
							// Display message
							Toast.makeText(SearchFlight.this,
									"Please Select Departure Location First",
									Toast.LENGTH_SHORT).show();
						}

					});
				}
			}

		}).start();

	}
}
