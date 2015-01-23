package b07.PIII.featuredflights;

import pIIIsoln.Client;
import pIIIsoln.MainSystem;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

public class SelectDateAndTime extends ActionBarActivity
{
	CalendarView		calendar;
	private Client		user;
	private MainSystem	main;
	private EditText	date;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_date_and_time);

		new Thread(new Runnable()
		{
			public void run()
			{
				// Get information from previous intent
				Intent previousIntent = getIntent();
				Bundle previousBundle = previousIntent.getExtras();

				user = (Client) previousBundle.get(Messages
						.getString("MainActivity.1"));
				main = (MainSystem) previousBundle.get(Messages
						.getString("MainActivity.0"));
			}

		}).start();

		// Gets the date
		date = (EditText) findViewById(R.id.datePicker);

	}

	public void submit(View view)
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				// Creating and passing the information.
				Intent calendarIntent = new Intent(SelectDateAndTime.this,
						SearchFlight.class);
				calendarIntent.putExtra(Messages.getString("SearchFlight.0"),
						date.getText().toString());
				calendarIntent.putExtra(Messages.getString("MainActivity.1"),
						user);
				calendarIntent.putExtra(Messages.getString("MainActivity.0"),
						main);

				// Moves to Search flight
				startActivity(calendarIntent);
			}

		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_date_and_time, menu);
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
