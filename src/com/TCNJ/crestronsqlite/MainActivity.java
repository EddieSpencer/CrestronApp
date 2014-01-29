package com.TCNJ.crestronsqlite;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button; 
import android.widget.EditText; 

import java.util.List;

import android.util.Log;

public class MainActivity extends Activity {

	
	Button saveButton;
	EditText editTextSystemName,editTextHostName,editTextIPID, editTextCIPPort;
	EditText editTextWebPort, editTextUseSSL, editTextUserName, editTextPassword;
	
	String systemName;
	String hostName;
	int ipid;
	int cipPort;
	int webPort;
	boolean useSSL;
	String userName;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseHandler db = new DatabaseHandler(this);
		
		
		editTextSystemName = (EditText) findViewById(R.id.editTextSystemName);
		editTextHostName = (EditText)findViewById(R.id.editTextHostName);
		editTextIPID = (EditText)findViewById(R.id.editTextIPID);
		editTextCIPPort = (EditText)findViewById(R.id.editTextCIPPort);
		editTextWebPort = (EditText)findViewById(R.id.editTextWebPort);
		editTextUseSSL = (EditText)findViewById(R.id.editTextUseSSL);
		editTextUserName = (EditText)findViewById(R.id.editTextUserName);
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);
		
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
						
			}
		});
		
		
		
		Log.d("Insert: ", "Inserting .."); 
		
       
        
        
		db.addSystem(new SystemInfo(systemName, hostName, ipid, cipPort, webPort, useSSL, userName, password)); 
		db.addSystem(new SystemInfo("TheSystem", "TheHost", 1, 2, 3, true, "spencee", "redd")); 
		
		
      
        Log.d("Reading: ", "Reading all systems.."); 
        List<SystemInfo> systems = db.getAllSystems();       
         
        for (SystemInfo si: systems) {
        	String log = "SystemName: " + si.getSystemName() + " ," + "HostName: " + si.getHostName() + " ," + "IPID: " + si.getIPID() + " ," + 
        			"CIPPort: " + si.getCIPPort() + " ," + "WebPort: " + si.getWebPort() + " ," + 
        			"UseSSL: " + si.getUseSSL() + " ," + "UserName: " + si.getUserName() + " ," + "Password: " + si.getPassword();
        Log.d("Name: ", log);
        
    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
