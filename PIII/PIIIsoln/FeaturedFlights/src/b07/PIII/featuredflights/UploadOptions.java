package b07.PIII.featuredflights;

import java.io.File;

import pIIIsoln.MainSystem;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class UploadOptions extends ActionBarActivity
{
	// Initializing
	private String emailStr;
	public static final String MAIN_SYSTEM_KEY = Messages
			.getString("MainActivity.0");
	public static final String EMAIL = Messages.getString("MainActivity.3");
	private MainSystem	main;
	ProgressDialog		progressBar;
	private int			progressBarStatus	= 0;
	private Handler		progressBarHandler	= new Handler();
	private long		fileSize			= 0;
	private Button		uploadFlightInfoBtn;
	private Button		uploadAdminInfoBtn;
	private Button		uploadClientInfoBtn;
	private String		newFlightDataPath;
	private String		newClientDataPath;
	private String		newAdminDataPath;
	private Handler		handler				= new Handler();
	private File		flightDataFile;
	private File		clientDataFile;
	private File		adminDataFile;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_options);

		new Thread(new Runnable()
		{
			public void run()
			{
				// Getting the information from previous activity
				Intent previousIntent = getIntent();
				Bundle previousBundle = previousIntent.getExtras();

				// Sets up the main system
				main = (MainSystem) previousBundle.get(Messages
						.getString("MainActivity.0"));
				emailStr = (String) previousBundle.get(EMAIL);
				// Sets up the directory for administrator upload
				File uploadData = UploadOptions.this.getApplicationContext()
						.getDir("UPLOAD_HERE", MODE_APPEND);

				// Sets up the path for all the files
				newFlightDataPath = uploadData.getAbsolutePath().toString()
						+ "/" + "flightData.csv";
				newClientDataPath = uploadData.getAbsolutePath().toString()
						+ "/" + "clientData.csv";
				newAdminDataPath = uploadData.getAbsolutePath().toString()
						+ "/" + "adminData.csv";
				System.out.println(newFlightDataPath);
				flightDataFile = new File(newFlightDataPath);
				clientDataFile = new File(newClientDataPath);
				adminDataFile = new File(newAdminDataPath);

				uploadFlightInfoBtn = (Button) findViewById(R.id.uploadFlightInfoBtn);
				uploadAdminInfoBtn = (Button) findViewById(R.id.uploadAdminInfoBtn);
				uploadClientInfoBtn = (Button) findViewById(R.id.uploadClientInfoBtn);
				
				uploadData(uploadAdminInfoBtn);
				uploadData(uploadClientInfoBtn);
				uploadData(uploadFlightInfoBtn);

				handler.post(new Runnable()
				{
					public void run()
					{
						Toast.makeText(
								UploadOptions.this,
								"Please Ensure That All The Necessary Files Are Present",
								Toast.LENGTH_SHORT).show();
					}

				});

			}

		}).start();

	}

	public void uploadData(final Button button)
	{
		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				// prepare for a progress bar dialog
				progressBar = new ProgressDialog(v.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Uploading File ...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressBar.setProgress(0);
				progressBar.setMax(100);
				progressBar.show();

				// reset progress bar status
				progressBarStatus = 0;

				// reset filesize
				fileSize = 0;

				new Thread(new Runnable()
				{
					public void run()
					{
						while (progressBarStatus < 100)
						{
							if (button == uploadFlightInfoBtn)
							{
								// Checks for file existence
								if (flightDataFile.exists())
								{
									// process some tasks
									progressBarStatus = uploadFlightDataTask();
								}
								else
								{
									// Display error message
									handler.post(new Runnable()
									{
										public void run()
										{
											Toast.makeText(
													UploadOptions.this,
													"Please Ensure That \"flightData.csv\" "
															+ "File Is Present In \"UPLOAD_HERE\" Directory",
													Toast.LENGTH_SHORT).show();
										}

									});
								}
							}
							else if (button == uploadClientInfoBtn)
							{
								// Checks for file existence
								if (clientDataFile.exists())
								{
									// process some tasks
									progressBarStatus = uploadClientDataTask();
								}
								else
								{
									// Display error message
									handler.post(new Runnable()
									{
										public void run()
										{
											Toast.makeText(
													UploadOptions.this,
													"Please Ensure That \"clientData.csv\" "
															+ "File Is Present In \"UPLOAD_HERE\" Directory",
													Toast.LENGTH_SHORT).show();
										}

									});
								}
							}
							else
							{
								// Checks for file existence
								if (adminDataFile.exists())
								{
									// process some tasks
									progressBarStatus = uploadAdminDataTask();
								}
								else
								{
									// Display error message
									handler.post(new Runnable()
									{
										public void run()
										{
											Toast.makeText(
													UploadOptions.this,
													"Please Ensure That \"adminData.csv\" "
															+ "File Is Present In \"UPLOAD_HERE\" Directory",
													Toast.LENGTH_SHORT).show();
										}

									});
								}
							}

							// your computer is too fast, sleep 1 second
							try
							{
								Thread.sleep(1000);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}

							// Update the progress bar
							progressBarHandler.post(new Runnable()
							{
								public void run()
								{
									progressBar.setProgress(progressBarStatus);
								}
							});
						}

						// ok, file is downloaded,
						if (progressBarStatus >= 100)
						{

							// sleep 2 seconds, so that you can see the 100%
							try
							{
								Thread.sleep(2000);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}

							// close the progress bar dialog
							progressBar.dismiss();
						}
					}
				}).start();

			}

		});
	}

	/**
	 * Returns an integer representing the percentage of work done
	 * 
	 * @return integer
	 */
	public int uploadFlightDataTask()
	{

		while (fileSize <= 1000000)
		{

			fileSize++;

			if (fileSize == 100000)
			{
				return 10;
			}
			else if (fileSize == 200000)
			{
				return 20;
			}
			else if (fileSize == 300000)
			{
				return 30;
			}
			else if (fileSize == 400000)
			{

				return 40;
			}
			else if (fileSize == 500000)
			{
				main.uploadFlightInfo(newFlightDataPath);
				return 50;
			}
			else if (fileSize == 600000)
			{
				return 60;
			}
			else if (fileSize == 700000)
			{
				return 70;
			}
			else if (fileSize == 800000)
			{
				return 80;
			}
			else if (fileSize == 900000)
			{
				return 90;
			}
		}
		Intent intent = new Intent(UploadOptions.this,
				 GeneralOptions.class);
				 intent.putExtra(EMAIL,emailStr);
				 intent.putExtra(MAIN_SYSTEM_KEY, main);
				//Moving to General Options startActivity(intent);
				 startActivity(intent);
		return 100;

	}

	/**
	 * Returns an integer representing the percentage of work done
	 * 
	 * @return integer
	 */
	public int uploadClientDataTask()
	{

		while (fileSize <= 1000000)
		{

			fileSize++;

			if (fileSize == 100000)
			{
				return 10;
			}
			else if (fileSize == 200000)
			{
				return 20;
			}
			else if (fileSize == 300000)
			{
				return 30;
			}
			else if (fileSize == 400000)
			{

				return 40;
			}
			else if (fileSize == 500000)
			{			
				main.uploadClientInfo(newClientDataPath);
				return 50;
			}
			else if (fileSize == 600000)
			{
				return 60;
			}
			else if (fileSize == 700000)
			{
				return 70;
			}
			else if (fileSize == 800000)
			{
				return 80;
			}
			else if (fileSize == 900000)
			{
				return 90;
			}
		}

		Intent intent = new Intent(UploadOptions.this,
				 GeneralOptions.class);
				 intent.putExtra(EMAIL,emailStr);
				 intent.putExtra(MAIN_SYSTEM_KEY, main);
				//Moving to General Options startActivity(intent);
				 startActivity(intent);
		return 100;

	}

	/**
	 * Returns an integer representing the percentage of work done
	 * 
	 * @return integer
	 */
	public int uploadAdminDataTask()
	{

		while (fileSize <= 1000000)
		{

			fileSize++;

			if (fileSize == 100000)
			{
				return 10;
			}
			else if (fileSize == 200000)
			{
				return 20;
			}
			else if (fileSize == 300000)
			{
				return 30;
			}
			else if (fileSize == 400000)
			{

				return 40;
			}
			else if (fileSize == 500000)
			{
				main.uploadAdminInfo(newAdminDataPath);
				return 50;
			}
			else if (fileSize == 600000)
			{
				return 60;
			}
			else if (fileSize == 700000)
			{
				return 70;
			}
			else if (fileSize == 800000)
			{
				return 80;
			}
			else if (fileSize == 900000)
			{
				return 90;
			}
		}
		Intent intent = new Intent(UploadOptions.this,
				 GeneralOptions.class);
				 intent.putExtra(EMAIL,emailStr);
				 intent.putExtra(MAIN_SYSTEM_KEY, main);
				//Moving to General Options startActivity(intent);
				 startActivity(intent);


		return 100;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload_options, menu);
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
