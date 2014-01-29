package com.TCNJ.crestronsqlite;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	//UI Buttons to save data to database and clear data forms
	Button saveButton, clearButton;

	//UI EditText fields that represent SystemInfo attributes the user types
	EditText editTextSystemName,editTextHostName,editTextIPID, editTextCIPPort;
	EditText editTextWebPort, editTextUseSSL, editTextUserName, editTextPassword;

	//Scroll and Layout used for scrolling list of SystemNames in database
	ScrollView scrollViewSystems;
	LinearLayout scrollLayout;

	//List of all Systems in database
	List<SystemInfo> systems;

	//Database object that stores all System information
	DatabaseHandler db = new DatabaseHandler(this);

	//Attributes to be stored in System objects
	String systemName, hostName, userName, password;
	int ipid, cipPort, webPort;
	boolean useSSL;

	//Represents the ID of a System in loop to be sent to OnSystemClicked class
	int currentID = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Saves the user input data from text fields
		editTextSystemName = (EditText) findViewById(R.id.editTextSystemName);
		editTextHostName = (EditText)findViewById(R.id.editTextHostName);
		editTextIPID = (EditText)findViewById(R.id.editTextIPID);
		editTextCIPPort = (EditText)findViewById(R.id.editTextCIPPort);
		editTextWebPort = (EditText)findViewById(R.id.editTextWebPort);
		editTextUseSSL = (EditText)findViewById(R.id.editTextUseSSL);
		editTextUserName = (EditText)findViewById(R.id.editTextUserName);
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);

		//Saves the scrollView and scrollLayout
		scrollViewSystems = (ScrollView)findViewById(R.id.scrollView);
		scrollLayout = (LinearLayout)findViewById(R.id.scrollingLayout);

		//Saves the clearButton which is used to clear input fields easily
		clearButton = (Button)findViewById(R.id.clearButton);
		clearButton.setOnClickListener(new View.OnClickListener() {

			@Override
			//Resets all input fields
			public void onClick(View v) {
				currentID = -1;
				editTextSystemName.setText("");
				editTextHostName.setText("");
				editTextIPID.setText("");
				editTextCIPPort.setText("");
				editTextWebPort.setText("");
				editTextUseSSL.setText("");
				editTextUserName.setText("");
				editTextPassword.setText("");

			}
		});

		//Saves the saveButton data
		saveButton = (Button)findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener(){

			@Override
			//Saves data from user input fields into System objects which are stored into database
			public void onClick(View v) {

				try{
					//Saves all user input data into variables
					systemName = editTextSystemName.getText().toString(); 
					hostName = editTextHostName.getText().toString();
					ipid = Integer.parseInt(editTextIPID.getText().toString());
					cipPort = Integer.parseInt(editTextCIPPort.getText().toString());
					webPort = Integer.parseInt(editTextWebPort.getText().toString());
					useSSL = Boolean.parseBoolean(editTextUseSSL.getText().toString());
					userName = editTextUserName.getText().toString();
					password = editTextPassword.getText().toString();

					//SystemInfo object which will contain all user input
					SystemInfo sys;

					//Checks to make sure this is a new database entry
					if(currentID == -1){
						//Sets SystemInfo object with user input data and adds to database
						sys = new SystemInfo(systemName, hostName, ipid, cipPort, webPort, useSSL, userName, password); 
						db.addSystem(sys);
					}

					else{
						//Updating existing System in database based on id
						sys = new SystemInfo(currentID,systemName, hostName, ipid, cipPort, webPort, useSSL, userName, password);
						db.updateSystem(sys);
					}

					//creates a list of all System objects in database
					List<SystemInfo> systems = db.getAllSystems();  

					//Logging used for debugging purposes with LogCat
					/*
					for (SystemInfo si: systems) {
						String log = "SystemName: " + si.getSystemName() + " ," + "HostName: " + si.getHostName() + " ," + "IPID: " + si.getIPID() + " ," + 
								"CIPPort: " + si.getCIPPort() + " ," + "WebPort: " + si.getWebPort() + " ," + 
								"UseSSL: " + si.getUseSSL() + " ," + "UserName: " + si.getUserName() + " ," + "Password: " + si.getPassword();

						Log.d("Name: ", log);
					}
					 */

					//Call to loadScrollView to handle updating scroll view with multiple database entries
					loadScrollView();

				}
				//Handles exceptions such as invalid integer ranges and variable types
				catch(Exception e){
					//new Builder is created to handle dialog interfaces
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Input Error");
					//Gets specific error message
					builder.setMessage(e.getMessage());
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						//Closes Dialog interface
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					//Shows the alert created by the builder
					AlertDialog alert = builder.create();
					alert.show();
				}

			}

		});

		//Call to loadScrollView to handle updating scroll view with multiple database entries
		loadScrollView();
	}

	//Supporting method to help with scrolling list of Systems in database
	public void loadScrollView(){

		//Clears all views on startup 
		scrollLayout.removeAllViews();

		//Gets a List of all Systems in Database
		systems = db.getAllSystems();  

		//For each System in List of systems, make TextView of SystemName
		//Each SystemName is then clickable to show attributes
		for (int i = 0; i < systems.size(); i++){
			TextView tv = new TextView(this);
			tv.setText(systems.get(i).getSystemName());
			tv.setOnClickListener(new OnSystemClicked(i){

				@Override
				//Each ListView in the ScrollView is clickable to display System attributes
				public void onClick(View v) {
					//gets ID from OnSystemClicked to keep track in the loop
					currentID = systems.get(id).getID();

					//Sets the Text to the value the System has and is displayed to the user
					editTextSystemName.setText(systems.get(id).getSystemName());
					editTextHostName.setText(systems.get(id).getHostName());
					editTextIPID.setText(String.valueOf(systems.get(id).getIPID()));
					editTextCIPPort.setText(String.valueOf(systems.get(id).getCIPPort()));
					editTextWebPort.setText(String.valueOf(systems.get(id).getWebPort()));

					//Converts back to Boolean
					if(systems.get(id).getUseSSL())
						editTextUseSSL.setText("true");
					else
						editTextUseSSL.setText("false");

					editTextUserName.setText(systems.get(id).getUserName());
					editTextPassword.setText(systems.get(id).getPassword());				
				}
			});

			//Adds TextView to ScrollView and updates.
			scrollLayout.addView(tv);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
