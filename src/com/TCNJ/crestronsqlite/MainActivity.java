package com.TCNJ.crestronsqlite;



import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {


	Button saveButton;
	EditText editTextSystemName,editTextHostName,editTextIPID, editTextCIPPort;
	EditText editTextWebPort, editTextUseSSL, editTextUserName, editTextPassword;
	TextView textViewSystems;
	DatabaseHandler db = new DatabaseHandler(this);
	String systemName;
	String hostName;
	int ipid;
	int cipPort;
	int webPort;
	boolean useSSL;
	String userName;
	String password;
	String systemsTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editTextSystemName = (EditText) findViewById(R.id.editTextSystemName);
		editTextHostName = (EditText)findViewById(R.id.editTextHostName);
		editTextIPID = (EditText)findViewById(R.id.editTextIPID);
		editTextCIPPort = (EditText)findViewById(R.id.editTextCIPPort);
		editTextWebPort = (EditText)findViewById(R.id.editTextWebPort);
		editTextUseSSL = (EditText)findViewById(R.id.editTextUseSSL);
		editTextUserName = (EditText)findViewById(R.id.editTextUserName);
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);
		textViewSystems = (TextView)findViewById(R.id.textViewSystems);

		saveButton = (Button)findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {

				systemName = editTextSystemName.getText().toString(); 
				hostName = editTextHostName.getText().toString();
				ipid = Integer.parseInt(editTextIPID.getText().toString());
				cipPort = Integer.parseInt(editTextCIPPort.getText().toString());
				webPort = Integer.parseInt(editTextWebPort.getText().toString());
				useSSL = Boolean.parseBoolean(editTextUseSSL.getText().toString());
				userName = editTextUserName.getText().toString();
				password = editTextPassword.getText().toString();
				

				db.addSystem(new SystemInfo(systemName, hostName, ipid, cipPort, webPort, useSSL, userName, password)); 
					
				List<SystemInfo> systems = db.getAllSystems();  
								
				for (SystemInfo si: systems) {
					String log = "SystemName: " + si.getSystemName() + " ," + "HostName: " + si.getHostName() + " ," + "IPID: " + si.getIPID() + " ," + 
							"CIPPort: " + si.getCIPPort() + " ," + "WebPort: " + si.getWebPort() + " ," + 
							"UseSSL: " + si.getUseSSL() + " ," + "UserName: " + si.getUserName() + " ," + "Password: " + si.getPassword();
					
					String systemsNames = si.getID() + ". " + si.getSystemName();
					
					Log.d("Name: ", log);
					
					textViewSystems.setText(systemsNames);

				}
				
			}
			

		});
		
		
		
	}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

	}
