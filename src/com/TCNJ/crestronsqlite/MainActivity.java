package com.TCNJ.crestronsqlite;



import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {


	Button saveButton, clearButton;
	EditText editTextSystemName,editTextHostName,editTextIPID, editTextCIPPort;
	EditText editTextWebPort, editTextUseSSL, editTextUserName, editTextPassword;
	ScrollView scrollViewSystems;
	LinearLayout scrollLayout;
	DatabaseHandler db = new DatabaseHandler(this);
	List<SystemInfo> systems;
	String systemName;
	String hostName;
	int ipid;
	int cipPort;
	int webPort;
	boolean useSSL;
	String userName;
	String password;
	int currentID = -1;



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
		scrollViewSystems = (ScrollView)findViewById(R.id.scrollView);
		scrollLayout = (LinearLayout)findViewById(R.id.scrollingLayout);


		saveButton = (Button)findViewById(R.id.saveButton);
		clearButton = (Button)findViewById(R.id.clearButton);
		clearButton.setOnClickListener(new View.OnClickListener() {

			@Override
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

		saveButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {

				try{
					systemName = editTextSystemName.getText().toString(); 
					hostName = editTextHostName.getText().toString();
					ipid = Integer.parseInt(editTextIPID.getText().toString());
					cipPort = Integer.parseInt(editTextCIPPort.getText().toString());
					webPort = Integer.parseInt(editTextWebPort.getText().toString());
					useSSL = Boolean.parseBoolean(editTextUseSSL.getText().toString());
					userName = editTextUserName.getText().toString();
					password = editTextPassword.getText().toString();

					if(currentID == -1)
						db.addSystem(new SystemInfo(systemName, hostName, ipid, cipPort, webPort, useSSL, userName, password)); 
					else
						db.updateSystem(new SystemInfo(currentID,systemName, hostName, ipid, cipPort, webPort, useSSL, userName, password));
					List<SystemInfo> systems = db.getAllSystems();  

					for (SystemInfo si: systems) {
						String log = "SystemName: " + si.getSystemName() + " ," + "HostName: " + si.getHostName() + " ," + "IPID: " + si.getIPID() + " ," + 
								"CIPPort: " + si.getCIPPort() + " ," + "WebPort: " + si.getWebPort() + " ," + 
								"UseSSL: " + si.getUseSSL() + " ," + "UserName: " + si.getUserName() + " ," + "Password: " + si.getPassword();

						String systemsNames = si.getID() + ". " + si.getSystemName();

						Log.d("Name: ", log);

						//textViewSystems.setText(systemsNames);
						loadScrollView();


					}

				}
				catch(Exception e){
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Input Error");
					builder.setMessage(e.getMessage());
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					
					AlertDialog alert = builder.create();
					alert.show();
				}
			
			}

		});

		loadScrollView();



	}

	public void loadScrollView(){

		scrollLayout.removeAllViews();

		systems = db.getAllSystems();  


		for (int i = 0; i < systems.size(); i++){
			TextView tv = new TextView(this);
			tv.setText(systems.get(i).getSystemName());
			tv.setOnClickListener(new OnSystemClicked(i){

				@Override
				public void onClick(View v) {
					currentID = systems.get(id).getID();
					editTextSystemName.setText(systems.get(id).getSystemName());
					editTextHostName.setText(systems.get(id).getHostName());
					editTextIPID.setText(String.valueOf(systems.get(id).getIPID()));
					editTextCIPPort.setText(String.valueOf(systems.get(id).getCIPPort()));
					editTextWebPort.setText(String.valueOf(systems.get(id).getWebPort()));
					if(systems.get(id).getUseSSL())
						editTextUseSSL.setText("true");
					else
						editTextUseSSL.setText("false");
					editTextUserName.setText(systems.get(id).getUserName());
					editTextPassword.setText(systems.get(id).getPassword());				
				}

			});
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
