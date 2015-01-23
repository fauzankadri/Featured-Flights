package b07.PIII.featuredflights;

import pIIIsoln.MainSystem;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdministrativeTools extends ActionBarActivity
{
	// Initializing
	private MainSystem	main;
	private String emailStr;
	public static final String MAIN_SYSTEM_KEY = Messages
			.getString("MainActivity.0");
	public static final String EMAIL = Messages.getString("MainActivity.3");

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_administrative_tools);

		// Getting the information from previous activity
		Intent previousIntent = getIntent();
		Bundle Bundle = previousIntent.getExtras();

		// gets the user information
		main = (MainSystem) Bundle.get(Messages.getString("MainActivity.0"));
		emailStr = (String) Bundle.get(EMAIL);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.administrative_tools, menu);
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

	/**
	 * Book flight for another user button
	 * 
	 * @param view
	 */
	public void bookFlightAnotherUser(View view)
	{
		// Calls the helper method
		askUserEmail("book");
	}

	/**
	 * Upload information button
	 * 
	 * @param view
	 */
	public void uploadInfo(View view)
	{
		// Creates a alert dialog to display instructions
		AlertDialog.Builder uploadInstruction = new AlertDialog.Builder(this);

		// Message for the instruction alert
		String message = "In order for the upload to work properly please make"
				+ "  sure that all the new files are in the fallowing directory"
				+ ": \"/data/data/b07.PIII.featuredflight/app_UPLOAD_HERE\" "
				+ "before you continue. For more information please press the help"
				+ " button located on top right corner.";

		// Sets the title and message
		uploadInstruction.setTitle("Instructions");
		uploadInstruction.setMessage(message);

		// Creates a "OK" button and waits for user
		uploadInstruction.setPositiveButton("Ok",
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int whichButton)
					{
						// Create Intent
						Intent uploadOptionsIntent = new Intent(
								AdministrativeTools.this, UploadOptions.class);
						uploadOptionsIntent.putExtra(
								Messages.getString("MainActivity.0"), main);
						uploadOptionsIntent.putExtra(
								EMAIL, emailStr);
						AdministrativeTools.this
								.startActivity(uploadOptionsIntent);
					}
				});
		// Shows the alert
		uploadInstruction.show();
	}

	/**
	 * Edit user information for another user button
	 * 
	 * @param view
	 */
	public void editUserInfo(View view)
	{
		// Calls the helper method
		askUserEmail("edit");
	}

	/**
	 * Asks the user for email input
	 * 
	 */
	private void askUserEmail(final String name)
	{
		// Creates a new alert dialog to get user email
		AlertDialog.Builder newUserEmail = new AlertDialog.Builder(this);

		// Title and message for Alert Dialog
		newUserEmail.setTitle("User Email");
		newUserEmail.setMessage("Please Enter the email of the "
				+ "user you are booking the flight for.");

		// Create a EditText and set the input type to email
		final EditText email = new EditText(this);
		email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

		// Show Alert Dialog
		newUserEmail.setView(email);

		// Creates a "OK" button and waits for user
		newUserEmail.setPositiveButton("Ok",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						// Checks to see if user exist
						if ((main.getClient(email.getText().toString()).equals(""))
								|| (main.getAdmin(email.getText().toString()).equals("")))
						{

							if (name.equals("book"))
							{
								// Create Intent
								Intent bookDifferentUserIntent = new Intent(
										AdministrativeTools.this,
										SearchFlight.class);

								// Moves to search flight
								AdministrativeTools.this
										.startActivity(bookDifferentUserIntent);
							}
							else
							{
								Intent editInfoIntent = new Intent(
										AdministrativeTools.this,
										EditInfo.class);

								// Moves to edit Info
								AdministrativeTools.this
										.startActivity(editInfoIntent);
							}

						}
						// Display error message
						else
						{
							Toast.makeText(AdministrativeTools.this,
									"No User With That Email",
									Toast.LENGTH_SHORT).show();
						}

					}
				});

		// Creates a "Cancel" button and waits for user
		newUserEmail.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						// Does Nothing
					}
				});

		// Shows the alert
		newUserEmail.show();
	}
}
