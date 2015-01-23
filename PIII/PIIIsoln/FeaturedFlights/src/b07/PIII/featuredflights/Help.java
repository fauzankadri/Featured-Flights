package b07.PIII.featuredflights;

import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Help extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		TextView helpText = (TextView) findViewById(R.id.helpTextView);

		String message = "Welcome \n\nWelcome to featured flights. Below are the "
				+ "instructions on how to use the app properly and effectively."
				+ "\n\nLOG IN SCREEN\n\nIn order to login you must provide email "
				+ "address and a password. If you are a beta tester then your"
				+ "email is already in the system so for password use \"password\""
				+ " without the quotes. However if you are not a beta tester we "
				+ "are incredibly sorry but you are not welcome.\n\nGENERAL "
				+ "OPTIONS\n\nIn this screen you have multiple choices.\n\n"
				+ "VIEW BOOKED\n\nIn this screen you are able to view any booked"
				+ " flights you may have booked previously. You also have "
				+ "the option to remove them from this list. In order to "
				+ "remove any itinerary from the list just tap on them."
				+ "\n\nSEARCH FLIGHT\n\nIn order to view all the flights "
				+ "please fallow these steps:\nStep 1:\nchoose date from "
				+ "the calendar\nStep 2:\npick a departure location\nStep 3:\n"
				+ "pick a destination location\nafter you have finished all "
				+ "the above steps you can press the view flight button"
				+ "and you will see all the possible itinearies. In order "
				+ "to book a flight you must press the itinerary\n\nEDIT "
				+ "PERSONAL INFO\n\nIn this screen you have the chance to edit"
				+ " all your personal information.";

		helpText.setMovementMethod(new ScrollingMovementMethod());

		helpText.setText(message);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
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
