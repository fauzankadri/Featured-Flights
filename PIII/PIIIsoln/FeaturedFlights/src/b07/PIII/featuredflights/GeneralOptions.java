package b07.PIII.featuredflights;

import pIIIsoln.Admin;
import pIIIsoln.BillingInfo;
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

public class GeneralOptions extends ActionBarActivity {
	private MainSystem main;
	private BillingInfo billInfo;
	private String email;
	private Client client;
	private Admin admin;
	public static final String MAIN_SYSTEM_KEY = Messages
			.getString("MainActivity.0");
	public static final String EMAIL = Messages.getString("MainActivity.3");
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general_options);

		new Thread(new Runnable() {
			public void run() {
				// Get passed intent
				Intent intent = getIntent();

				// Get bundle from intent
				Bundle previousBundle = intent.getExtras();
				main = (MainSystem) previousBundle.get(Messages
						.getString("MainActivity.0")); //$NON-NLS-1$
				email = (String) previousBundle.get(EMAIL);
				client = main.getClient(email);
				admin = main.getAdmin(email);
				// Getting the billing information
				try {
					billInfo = client.getBillInfo();
				} catch (NullPointerException e) {
					billInfo = admin.getBillInfo();
				}
				handler.post(new Runnable() {
					public void run() {

						// Show welcome message on Toast
						Toast.makeText(GeneralOptions.this,
								"Welcome, " + billInfo.getFirstName() + "!", //$NON-NLS-1$ //$NON-NLS-2$
								Toast.LENGTH_SHORT).show();
					}

				});
			}

		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.general_options, menu);
		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		// In case the user is administrator
		MenuItem isAdmin = menu.findItem(R.id.admin);
		if (admin != null)
			isAdmin.setVisible(true);
		else{
			isAdmin.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Switch for different options
		switch (item.getItemId()) {
		// If administrator is clicked
		case R.id.admin:
			Intent adminToolsIntent = new Intent(this,
					AdministrativeTools.class);
			adminToolsIntent.putExtra(Messages.getString("MainActivity.0"), //$NON-NLS-1$
					main);

			// Moves to administrator tools activity
			this.startActivity(adminToolsIntent);
			break;

		// If help is clicked
		case R.id.help:
			Intent helpIntent = new Intent(this, Help.class);

			// Moves to help activity
			this.startActivity(helpIntent);
			break;

		// If logout is clicked
		case R.id.logOut:
			Intent logOutIntent = new Intent(this, MainActivity.class);
			//save data
			main.saveToAdminsFile(Messages.getString("saveAdminFile"));
			main.saveToClientsFile(Messages.getString("saveClientFile"));
			main.saveToFlightsFile(Messages.getString("saveFlightFile"));

			// Moves to logout activity
			this.startActivity(logOutIntent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	/**
	 * Search flight button
	 * 
	 * @param view
	 */
	public void searchFlight(View view) {
		new Thread(new Runnable() {
			public void run() {
				// Passing information through intent
				Intent searchFlightIntent = new Intent(GeneralOptions.this,
						SearchFlight.class);
				searchFlightIntent.putExtra(
						Messages.getString("MainActivity.0"), //$NON-NLS-1$
						main);
				searchFlightIntent.putExtra(EMAIL,email);
				searchFlightIntent.putExtra(MAIN_SYSTEM_KEY, main);

				// Moving to search flight activity
				startActivity(searchFlightIntent);
			}

		}).start();
	}

	/**
	 * View booked flight button
	 * 
	 * @param view
	 */
	public void viewBooked(View view) {
		new Thread(new Runnable() {
			public void run() {
				// Passing information through intent
				Intent viewBookedIntent = new Intent(GeneralOptions.this,
						ViewBooked.class);
				viewBookedIntent.putExtra(Messages.getString("MainActivity.0"), //$NON-NLS-1$
						main);
				viewBookedIntent.putExtra(EMAIL,email);
				viewBookedIntent.putExtra(MAIN_SYSTEM_KEY, main);

				// Moving to view booked activity
				startActivity(viewBookedIntent);
			}

		}).start();
	}

	/**
	 * Edit information button button
	 * 
	 * @param view
	 */
	public void editInfo(View view) {
		new Thread(new Runnable() {
			public void run() {
				// Passing information through intent
				Intent editInfoIntent = new Intent(GeneralOptions.this,
						EditInfo.class);

				editInfoIntent.putExtra(EMAIL,email);
				editInfoIntent.putExtra(MAIN_SYSTEM_KEY, main);

				// Moving to edit info activity
				startActivity(editInfoIntent);
			}

		}).start();
	}

}
