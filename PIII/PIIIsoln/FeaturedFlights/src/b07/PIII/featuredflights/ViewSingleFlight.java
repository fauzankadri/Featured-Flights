package b07.PIII.featuredflights;

import java.util.List;

import pIIIsoln.Client;
import pIIIsoln.Itinerary;
import pIIIsoln.MainSystem;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ViewSingleFlight extends ActionBarActivity {

	private Client user;
	private MainSystem main;

	private String flightDate;
	private String departureLocation;
	private String destinationLocation;
	private String[] flightArray;

	ArrayAdapter<String> adapter;
	EditText inputSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_single_flight);

		new Thread(new Runnable() {
			public void run() {

				// Getting the information from previous activity
				Intent prevousIntent = getIntent();
				Bundle previousBundle = prevousIntent.getExtras();
				user = (Client) previousBundle.get(Messages
						.getString("MainActivity.1"));
				main = (MainSystem) previousBundle.get(Messages
						.getString("MainActivity.0"));
				flightDate = (String) previousBundle.get(Messages
						.getString("SearchFlight.0"));
				departureLocation = (String) previousBundle.get(Messages
						.getString("SearchFlight.1"));
				destinationLocation = (String) previousBundle.get(Messages
						.getString("SearchFlight.2"));
			}

		}).start();

		ListView listview = (ListView) findViewById(R.id.FlightList);
		inputSearch = (EditText) findViewById(R.id.inputSearch);

		flightArray = changeListToArray();

		// Adding items to list and display
		adapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.city_name, flightArray);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, long id) {
				new Thread(new Runnable() {
					public void run() {
						// Get the name of city
						String cityName = (String) parent
								.getItemAtPosition(position);

						// Creating and passing the information
						Intent departureLocationIntent = new Intent(
								ViewSingleFlight.this, SearchFlight.class);
						departureLocationIntent.putExtra(
								Messages.getString("MainActivity.1"), user);
						departureLocationIntent.putExtra(
								Messages.getString("MainActivity.0"), main);
						departureLocationIntent.putExtra(
								Messages.getString(""), flightDate);
						departureLocationIntent.putExtra(
								Messages.getString(""), cityName);

						// Moving to Search flight
						startActivity(departureLocationIntent);
					}

				}).start();
			}

		});

		// Search method
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				ViewSingleFlight.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}

	/**
	 * Returns an array of Itinerary from a list of Itinerary
	 * 
	 * @return flighArray
	 **/
	private String[] changeListToArray() {
		List<Itinerary> flight = main.makeAllItineraries(flightDate,
				departureLocation, destinationLocation);
		int size = flight.size();
		String[] flights = new String[size];
		int i = 0;
		for (Itinerary itinerary : flight) {
			flights[i] = itinerary.toString();
			i++;
		}
		return flights;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_single_flight, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
}
