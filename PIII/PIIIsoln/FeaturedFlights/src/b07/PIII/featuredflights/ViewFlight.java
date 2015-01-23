package b07.PIII.featuredflights;

import java.util.List;

import pIIIsoln.Client;
import pIIIsoln.Itinerary;
import pIIIsoln.MainSystem;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ViewFlight extends ActionBarActivity
{

	// Initialize
	private Client					user;
	private MainSystem				main;
	private String					flightDate;
	private String					departureLocation;
	private String					destinationLocation;
	private String[]				itineraryArray;
	private ListView				listview;
	private Handler					handler	= new Handler();
	private ArrayAdapter<String>	adapter;
	private EditText				inputSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_flight);

		new Thread(new Runnable()
		{
			public void run()
			{

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

				// Get the itinerary list
				List<Itinerary> itineraryList = main.makeAllItineraries(flightDate,
						departureLocation, destinationLocation);

				// Accessing list and TextEdit
				listview = (ListView) findViewById(R.id.FlightList);
				inputSearch = (EditText) findViewById(R.id.inputSearch);

				// Converting the list to array for list usage
				int size = itineraryList.size();
				itineraryArray = new String[size];
				int i = 0;
				for (Itinerary itinerary : itineraryList)
				{
					itineraryArray[i] = itinerary.toString();
					i++;
				}

				// In case there are no flights with given information
				if (itineraryArray.length != 0)
				{
					// Adding items to list and display
					adapter = new ArrayAdapter<String>(ViewFlight.this,
							R.layout.list_item, R.id.city_name, itineraryArray);
					listview.setAdapter(adapter);

					// In case user clicks on a flight
					listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
					{

						@Override
						public void onItemClick(final AdapterView<?> parent,
								final View view, final int position, long id)
						{

							// Get the selected itinerary
							//String selectedItinerary = (String) parent
									//.getItemAtPosition(position);

							// TODO: figure out how to change the string to
							// itinerary object

						}

					});

					// Search method
					inputSearch.addTextChangedListener(new TextWatcher()
					{

						@Override
						public void onTextChanged(CharSequence cs, int arg1,
								int arg2, int arg3)
						{
							// When user searches
							ViewFlight.this.adapter.getFilter().filter(cs);
						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3)
						{

						}

						@Override
						public void afterTextChanged(Editable arg0)
						{
						}

					});
				}
				else
				{
					handler.post(new Runnable()
					{
						public void run()
						{
							// Creates a new alert dialog to get user email
							AlertDialog.Builder noFlightAlert = new AlertDialog.Builder(
									ViewFlight.this);

							// Title and message for Alert Dialog
							noFlightAlert.setTitle("No Flights Available");
							noFlightAlert
									.setMessage("Please select a different time "
											+ "or location and try again");

							// Creates a "OK" button and waits for user
							noFlightAlert.setPositiveButton("Ok",
									new DialogInterface.OnClickListener()
									{

										public void onClick(
												DialogInterface dialog,
												int whichButton)
										{

										}
									});
							// Shows the alert
							noFlightAlert.show();
						}

					});

				}

			}

		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_flight, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

}
