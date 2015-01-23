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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditInfo extends ActionBarActivity {
	// Initializing
	private BillingInfo billInfo;
	private String emailStr;
	private Client client;
	private Admin admin;
	public static final String MAIN_SYSTEM_KEY = Messages
			.getString("MainActivity.0");
	public static final String EMAIL = Messages.getString("MainActivity.3");
	private MainSystem main;
	
	private EditText firstName;
	private EditText lastName;
	private EditText email;
	private EditText address;
	private EditText creditCardNumber;
	private EditText newPassword;
	private EditText confirmNewPassword;
	private Spinner spinner;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_info);

		new Thread(new Runnable() {
			public void run() {

				firstName = (EditText) findViewById(R.id.firstNameTxt);
				lastName = (EditText) findViewById(R.id.lastNameTxt);
				email = (EditText) findViewById(R.id.emailTxt);
				address = (EditText) findViewById(R.id.addressTxt);
				creditCardNumber = (EditText) findViewById(R.id.creditCardNumberTxt);
				newPassword = (EditText) findViewById(R.id.newPasswordTxt);
				confirmNewPassword = (EditText) findViewById(R.id.newPasswordConfirmTxt);

				// Getting the information from previous activity
				Intent previousIntent = getIntent();
				Bundle previousBundle = previousIntent.getExtras();
				//billInfo = (BillingInfo) previousBundle.get(Messages
						//.getString("GeneralOptions.0"));
				main = (MainSystem) previousBundle.get(Messages
						.getString("MainActivity.0"));
				emailStr = (String) previousBundle.get(EMAIL);
				client = main.getClient(emailStr);
				admin = main.getAdmin(emailStr);
				// Getting the billing information
				
				try {
					billInfo = client.getBillInfo();
				} catch (NullPointerException e) {
					billInfo = admin.getBillInfo();
				}
				handler.post(new Runnable() {
					public void run() {
						// Setting the hints based on information
						System.out.println(billInfo.getFirstName());
						firstName.setHint(billInfo.getFirstName());
						lastName.setHint(billInfo.getLastName());
						email.setHint(billInfo.getEmail());
						address.setHint(billInfo.getAdress());
						creditCardNumber.setHint(numberHider(billInfo
								.getCreditCardNum()));
						try {
						newPassword.setHint(client.getPassword());
						confirmNewPassword.setHint(client.getPassword());
						} catch (NullPointerException e) {
							newPassword.setHint(admin.getPassword());
							confirmNewPassword.setHint(admin.getPassword());
						}

					}

				});
			}

		}).start();

		// Spinner for expiry date with default layout
		spinner = (Spinner) findViewById(R.id.expiryDateSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.expiary_date_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_info, menu);
		return true;
	}

	/**
	 * Returns a string with all the character replaced with * except the last
	 * four character
	 * 
	 * @param creditCardNumber
	 *            represents a credit card number
	 * @return creditCardNumber
	 */

	private String numberHider(String creditCardNumber) {
		StringBuilder cardNumberStringBuilder = new StringBuilder(
				creditCardNumber);

		// Replaces all characters wit * except the last four
		for (int i = 0; i < creditCardNumber.length() - 4; i++) {
			cardNumberStringBuilder.setCharAt(i, '*');
		}
		return cardNumberStringBuilder.toString();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Switch for different options
		switch (item.getItemId()) {
		// If help is clicked
		case R.id.help:
			new Thread(new Runnable() {
				public void run() {
					Intent helpIntent = new Intent(EditInfo.this, Help.class);

					// Moves to help activity
					EditInfo.this.startActivity(helpIntent);
				}

			}).start();
			break;

		// If logout is clicked
		case R.id.logOut:
			new Thread(new Runnable() {
				public void run() {
					Intent logOutIntent = new Intent(EditInfo.this,
							MainActivity.class);

					// Moves to logout activity
					EditInfo.this.startActivity(logOutIntent);
				}

			}).start();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	/**
	 * Save button
	 * 
	 * @param view
	 */
	public void save(View view) {
		new Thread(new Runnable() {
			public void run() {
				if (!firstName.getText().toString().equals("")) {
					billInfo.setFirstName(firstName.getText().toString());
				}
				if (!lastName.getText().toString().equals("")) {
					billInfo.setLastName(lastName.getText().toString());
				}
				if (!email.getText().toString().equals("")) {
					billInfo.setEmail(email.getText().toString());
				}
				if (!address.getText().toString().equals("")) {
					billInfo.setAddress(address.getText().toString());
				}
				if (!creditCardNumber.getText().toString().equals("")) {
					billInfo.setCreditCardNum(creditCardNumber.toString());
				}
				if (!newPassword.getText().toString().equals("")
						|| !confirmNewPassword.getText().toString().equals("")) {
					if (newPassword.toString().equals(
							confirmNewPassword.getText().toString())) {
						client.setPassword(newPassword.getText().toString());
					}
				}
				if (spinner.getSelectedItemPosition() != 0) {
					if (spinner.getSelectedItemPosition() == 1) {
						billInfo.setExpiryDate("12/2014");
					} else if (spinner.getSelectedItemPosition() == 2) {
						billInfo.setExpiryDate("12/2014");
					} else if (spinner.getSelectedItemPosition() == 3) {
						billInfo.setExpiryDate("12/2014");
					} else if (spinner.getSelectedItemPosition() == 4) {
						billInfo.setExpiryDate("01/2015");
					} else if (spinner.getSelectedItemPosition() == 5) {
						billInfo.setExpiryDate("02/2015");
					} else if (spinner.getSelectedItemPosition() == 6) {
						billInfo.setExpiryDate("03/2015");
					} else if (spinner.getSelectedItemPosition() == 7) {
						billInfo.setExpiryDate("04/2015");
					} else if (spinner.getSelectedItemPosition() == 8) {
						billInfo.setExpiryDate("05/2015");
					} else if (spinner.getSelectedItemPosition() == 9) {
						billInfo.setExpiryDate("06/2015");
					} else if (spinner.getSelectedItemPosition() == 10) {
						billInfo.setExpiryDate("07/2015");
					}
				}
				System.out.println(billInfo.toString());
				handler.post(new Runnable() {
					public void run() {
						System.out.println(billInfo.toString());
						Toast.makeText(EditInfo.this,
								"Data Saved\nYou Can Now Go Back",
								Toast.LENGTH_SHORT).show();

					}

				});
				
				  Intent intent = new Intent(EditInfo.this,
				  GeneralOptions.class);
				  intent.putExtra(EMAIL,emailStr);
				  intent.putExtra(MAIN_SYSTEM_KEY, main);
				  //Moving to General Options startActivity(intent);
				  startActivity(intent);
			}
		}

		).start();

	}
}
