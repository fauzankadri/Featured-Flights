package b07.PIII.featuredflights;

import java.io.File;
import java.io.IOException;

import pIIIsoln.Admin;
import pIIIsoln.MainSystem;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private EditText email;
	private EditText password;

	private MainSystem main;

	public static final String MAIN_SYSTEM_KEY = Messages
			.getString("MainActivity.0");
	public static final String EMAIL = Messages.getString("MainActivity.3");
	private String flightDataPath;
	private String clientDataPath;
	private String adminDataPath;
	private String passwordDataPath;
	private Handler handler = new Handler();
	private boolean allFilesExist = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Accessing email and password text box object
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);

		new Thread(new Runnable() {
			public void run() {
				// Sets up the directory for data
				File systemData = MainActivity.this.getApplicationContext()
						.getDir("Data", MODE_PRIVATE);

				// Gets all the paths for data files
				flightDataPath = systemData.getAbsolutePath().toString() + "/"
						+ "flightData.ser";
				clientDataPath = systemData.getAbsolutePath().toString() + "/"
						+ "clientData.ser";
				adminDataPath = systemData.getAbsolutePath().toString() + "/"
						+ "adminData.ser";
				passwordDataPath = systemData.getAbsolutePath().toString()
						+ "/" + "password.txt";

				// Sets up the data files
				File flightDataFile = new File(flightDataPath);
				File clientDataFile = new File(clientDataPath);
				File adminDataFile = new File(adminDataPath);
				File passwordDataFile = new File(passwordDataPath);

				// Creates a new main system from loaded data.
				try {
					main = new MainSystem(flightDataFile, clientDataFile,
							adminDataFile);
					main.savePasswords(passwordDataFile);
					allFilesExist = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (flightDataFile.length() == 0
						&& clientDataFile.length() == 0
						&& adminDataFile.length() == 0) {
					// Uploading all the information

					
					 File flightDataFileCsv = new File(systemData
					 .getAbsolutePath().toString() + "/" + "flightData.csv");
					 File clientDataFileCsv = new File(systemData
					 .getAbsolutePath().toString() + "/" + "clientData.csv");
					 File adminDataFileCsv = new File(systemData
					 .getAbsolutePath().toString() + "/" + "adminData.csv");
					 if (clientDataFileCsv.exists() &&
					 adminDataFileCsv.exists() && flightDataFileCsv.exists())
					 { main.uploadClientInfo(clientDataFileCsv.getPath());
					 main.uploadAdminInfo(adminDataFileCsv.getPath());
					 main.uploadFlightInfo(flightDataFileCsv
					 .getAbsolutePath());
					 
					main.createAdmin("Admin", "Default",
							"admin@hotmail.com", "defaultAdress",
							"defaultCreditNumber", "DefaultExpiryDate");
				}}
			}

		}).start();

		// TODO: Remove after testing
		email.setText("admin@hotmail.com");
		password.setText("password");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Log in button
	 * 
	 * @param view
	 * @throws IOException
	 */
	public void logIn(View view) throws IOException {
		new Thread(new Runnable() {
			public void run() {

				Intent intent = new Intent(MainActivity.this,
						GeneralOptions.class);
				if (allFilesExist) {
					if ((main.getClient(email.getText().toString()) != null && (main
							.getClient(email.getText().toString())
							.getPassword()).equals(password.getText()
							.toString()))) {
						// Passing the information
						intent.putExtra(EMAIL, email.getText().toString());
						intent.putExtra(MAIN_SYSTEM_KEY, main);
						handler.post(new Runnable() {
							public void run() {

								// Sets the email and password to null
								email.setText(null);
								password.setText(null);
							}

						});

						// Moving to General Options
						startActivity(intent);
					} else if ((main.getAdmin(email.getText().toString()) != null && (main
							.getAdmin(email.getText().toString()).getPassword())
							.equals(password.getText().toString()))) {
						// Passing the information
						intent.putExtra(MAIN_SYSTEM_KEY, main);
						intent.putExtra(EMAIL, email.getText().toString());

						// Sets the email and password to null
						//email.setText(null);
						//password.setText(null);

						// Moving to General Options
						startActivity(intent);

					} else {
						// In case of invalid input
						password.setError("Invalid Username or Password");
					}
				} else {
					handler.post(new Runnable() {
						public void run() {
							Toast.makeText(
									MainActivity.this,
									"Please Ensure That All The Necessary Files Are Present",
									Toast.LENGTH_SHORT).show();
						}

					});
				}
			}

		}).start();
	}

	/**
	 * Log in as guest button
	 * 
	 * @param view
	 */
	public void logInAsGuest(View view) {
		// Set text of toast and display
		Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Switch for all options
		switch (item.getItemId()) {
		// If help is clicked
		case R.id.help:
			// Create Intent
			Intent helpIntent = new Intent(this, Help.class);

			// Move to help activity
			this.startActivity(helpIntent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}
}
