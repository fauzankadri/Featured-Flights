package b07.PIII.featuredflights;

import java.util.ArrayList;

import pIIIsoln.Admin;
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

public class ViewBooked extends ActionBarActivity
{
	// Initialize
	private MainSystem main;
	private String email;
	private Client client;
	private Admin admin;
	public static final String MAIN_SYSTEM_KEY = Messages
			.getString("MainActivity.0");
	public static final String EMAIL = Messages.getString("MainActivity.3");
	private Client					user;
	private ListView				listview;
	private Handler					handler	= new Handler();
	private ArrayAdapter<String>	adapter;
	private EditText				inputSearch;
	private String[]				itineraryString;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_booked);

		new Thread(new Runnable()
		{
			public void run()
			{
				// Getting previous information
				Intent previousIntent = getIntent();
				Bundle bundle = previousIntent.getExtras();

				// gets the user
				main = (MainSystem) bundle.get(MAIN_SYSTEM_KEY); //$NON-NLS-1$
				user = (Client) bundle.get(Messages.getString("MainActivity.1"));
				email = (String) bundle.get(EMAIL);
				client = main.getClient(email);
				admin = main.getAdmin(email);
				// Accessing list and TextEdit
				listview = (ListView) findViewById(R.id.bookedList);
				inputSearch = (EditText) findViewById(R.id.inputSearch);
				if (client != null){
					user = client;
				}
				else{
					user = admin;
				}
				ArrayList<Itinerary> itineraries = user.getBookedItineraries();

				// Converting the itineraries to string for display
				int size = itineraries.size();
				itineraryString = new String[size];
				int i = 0;
				for (Itinerary itinerary : itineraries)
				{
					itineraryString[i] = itinerary.toString();
					i++;
				}

				// In case there are no flights with given information
				if (itineraryString.length != 0)
				{
					// Adding items to list and display
					adapter = new ArrayAdapter<String>(ViewBooked.this,
							R.layout.list_item, R.id.city_name, itineraryString);
					listview.setAdapter(adapter);

					// In case user clicks on a flight
					listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
					{

						@Override
						public void onItemClick(final AdapterView<?> parent,
								final View view, final int position, long id)
						{

							// Get the selected itinerary
							// String selectedItinerary = (String) parent
							// .getItemAtPosition(position);

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
							ViewBooked.this.adapter.getFilter().filter(cs);
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
							AlertDialog.Builder noBookedFlightAlert = new AlertDialog.Builder(
									ViewBooked.this);

							// Title and message for Alert Dialog
							noBookedFlightAlert
									.setTitle("No Booked Flights Available");
							noBookedFlightAlert
									.setMessage("There are no booked flights "
											+ "available");

							// Creates a "OK" button and waits for user
							noBookedFlightAlert.setPositiveButton("Ok",
									new DialogInterface.OnClickListener()
									{

										public void onClick(
												DialogInterface dialog,
												int whichButton)
										{

										}
									});
							// Shows the alert
							noBookedFlightAlert.show();
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
		getMenuInflater().inflate(R.menu.view_booked, menu);
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
