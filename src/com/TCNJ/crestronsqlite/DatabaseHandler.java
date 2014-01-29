package com.TCNJ.crestronsqlite;



import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//DatabaseHandler class creates SQLite database and manages adding, updating, and deleting.
public class DatabaseHandler extends SQLiteOpenHelper {

	// Static Variables
	private static final int DATABASE_VERSION = 1; //Database Version
	private static final String DATABASE_NAME = "Systems_Database"; //Database Name
	private static final String TABLE_SYSTEMS = "Systems_Table"; //SystemInfo table name

	// SystemInfo Table column names
	private static final String KEY_ID = "_id";
	private static final String SYSTEM_NAME = "_systemName";
	private static final String HOST_NAME = "_hostName";
	private static final String IPID = "_ipid";
	private static final String CIP_PORT = "_cipPort";
	private static final String WEB_PORT = "_webPort";
	private static final String USE_SSL = "_useSSL";
	private static final String USER_NAME = "_userName";
	private static final String PASSWORD = "_password";

	//Constructor
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//Creates table in database with ID as primary key, and unique SystemNames.
	@Override
	public void onCreate(SQLiteDatabase db) {
		String SYSTEMS_TABLE = "CREATE TABLE " + TABLE_SYSTEMS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + SYSTEM_NAME + " TEXT NOT NULL UNIQUE," + HOST_NAME + " TEXT,"
				+ IPID + " TEXT," +  CIP_PORT + " TEXT," + WEB_PORT + " TEXT," + USE_SSL + " TEXT," + USER_NAME + " TEXT," + PASSWORD + " TEXT" +");";
		db.execSQL(SYSTEMS_TABLE);
	}

	//Upgrades Database, drops older table if it existed and creates the tables again.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYSTEMS);
		onCreate(db);
	}

	//Adds a new System to database
	void addSystem(SystemInfo sysInfo) {
		SQLiteDatabase db = this.getWritableDatabase();
		//Stores values of System
		ContentValues values = new ContentValues();
		values.put(SYSTEM_NAME, sysInfo.getSystemName());
		values.put(HOST_NAME, sysInfo.getHostName());
		values.put(IPID, sysInfo.getIPID());
		values.put(CIP_PORT, sysInfo.getCIPPort());
		values.put(WEB_PORT, sysInfo.getWebPort());
		//Convert Boolean to "1" or "0" since SQLite does not support Boolean data types.
		if(sysInfo.getUseSSL())
			values.put(USE_SSL, "1");
		else
			values.put(USE_SSL,  "0");
		
		values.put(USER_NAME, sysInfo.getUserName());
		values.put(PASSWORD, sysInfo.getPassword());

		//Inserts row into database and closes connection
		db.insert(TABLE_SYSTEMS, null, values);
		db.close();
	}

	//Returns a single system from the database based on ID.
	SystemInfo getSystem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		//Queries database for specific System based on ID
		Cursor cursor = db.query(TABLE_SYSTEMS, new String[] { KEY_ID, SYSTEM_NAME,
				HOST_NAME, IPID, CIP_PORT, WEB_PORT, USE_SSL, USER_NAME, PASSWORD }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		//Creates System object to be returned
		SystemInfo system = new SystemInfo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), cursor.getString(6).equals("1"), cursor.getString(7), cursor.getString(8));
		return system;
	}

	//Returns a list of all Systems in Database.
	public List<SystemInfo> getAllSystems() {
		List<SystemInfo> systemList = new ArrayList<SystemInfo>();
		
		//Select all query from database
		String selectQuery = "SELECT  * FROM " + TABLE_SYSTEMS;
		
		//Accesses the Database and loops through all rows adding them to the list
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			do {
				SystemInfo sysInfo = new SystemInfo();
				sysInfo.setID(Integer.parseInt(cursor.getString(0)));
				sysInfo.setSystemName(cursor.getString(1));
				sysInfo.setHostName(cursor.getString(2));
				sysInfo.setIPID(Integer.parseInt(cursor.getString(3)));
				sysInfo.setCIPPort(Integer.parseInt(cursor.getString(4)));
				sysInfo.setWebPort(Integer.parseInt(cursor.getString(5)));
				//Converts UseSSL back to boolean type
				sysInfo.setUseSSL(cursor.getString(6).equals("1"));
				sysInfo.setUserName(cursor.getString(7));
				sysInfo.setPassword(cursor.getString(8));
				systemList.add(sysInfo);
			} while (cursor.moveToNext());
		}
		
		//Returns list of all Systems
		return systemList;
	}

	//Updates a single system
	public int updateSystem(SystemInfo sysInfo) {
		SQLiteDatabase db = this.getWritableDatabase();
		//Stores values of Systems in Database
		ContentValues values = new ContentValues();
		values.put(SYSTEM_NAME, sysInfo.getSystemName());
		values.put(HOST_NAME, sysInfo.getHostName());
		values.put(IPID, sysInfo.getIPID());
		values.put(CIP_PORT, sysInfo.getCIPPort());
		values.put(WEB_PORT, sysInfo.getWebPort());
		//Converts Boolean to "1" or "0". SQL does not support Boolean types
		if(sysInfo.getUseSSL())
			values.put(USE_SSL, "1");
		else
			values.put(USE_SSL,  "0");
		values.put(USER_NAME, sysInfo.getUserName());
		values.put(PASSWORD, sysInfo.getPassword());
		
		//Returns the updated row with new values based on ID
		return db.update(TABLE_SYSTEMS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(sysInfo.getID()) });
	}

	//Deletes a single System from database
	public void deleteSystem(SystemInfo sysInfo) {
		SQLiteDatabase db = this.getWritableDatabase();
		//Finds System with unique ID and removes from database
		db.delete(TABLE_SYSTEMS, KEY_ID + " = ?",
				new String[] { String.valueOf(sysInfo.getID()) });
		db.close();
	}


	//Returns the total number of systems in database
	public int getSystemsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SYSTEMS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		//Returns count of systems
		return cursor.getCount();
	}
}