package com.TCNJ.crestronsqlite;



import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "DB5";
 
    // Contacts table name
    private static final String TABLE_SYSTEMS = "SYS5";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String SYSTEM_NAME = "_systemName";
	private static final String HOST_NAME = "_hostName";
	private static final String IPID = "_ipid";
	private static final String CIP_PORT = "_cipPort";
	private static final String WEB_PORT = "_webPort";
	private static final String USE_SSL = "_useSSL";
	private static final String USER_NAME = "_userName";
	private static final String PASSWORD = "_password";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SYSTEMS_TABLE = "CREATE TABLE " + TABLE_SYSTEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + SYSTEM_NAME + " TEXT NOT NULL UNIQUE," + HOST_NAME + " TEXT,"
                + IPID + " TEXT," +  CIP_PORT + " TEXT," + WEB_PORT + " TEXT," + USE_SSL + " TEXT," + USER_NAME + " TEXT," + PASSWORD + " TEXT" +");";
        db.execSQL(SYSTEMS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYSTEMS);
 
        // Create tables again
        onCreate(db);
    }
 

 
    // Adding new system
    void addSystem(SystemInfo sysInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(SYSTEM_NAME, sysInfo.getSystemName());
        values.put(HOST_NAME, sysInfo.getHostName());
		values.put(IPID, sysInfo.getIPID());
		values.put(CIP_PORT, sysInfo.getCIPPort());
		values.put(WEB_PORT, sysInfo.getWebPort());
		values.put(USE_SSL, sysInfo.getUseSSL());
		values.put(USER_NAME, sysInfo.getUserName());
		values.put(PASSWORD, sysInfo.getPassword());
 
        // Inserting Row
        db.insert(TABLE_SYSTEMS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single system
    SystemInfo getSystem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_SYSTEMS, new String[] { KEY_ID, SYSTEM_NAME,
                HOST_NAME, IPID, CIP_PORT, WEB_PORT, USE_SSL, USER_NAME, PASSWORD }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        SystemInfo system = new SystemInfo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Boolean.parseBoolean(cursor.getString(6)), cursor.getString(7), cursor.getString(8));
        // return contact
        return system;
    }
     
    // Getting All systems
    public List<SystemInfo> getAllSystems() {
        List<SystemInfo> systemList = new ArrayList<SystemInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SYSTEMS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SystemInfo sysInfo = new SystemInfo();
                sysInfo.setID(Integer.parseInt(cursor.getString(0)));
				sysInfo.setSystemName(cursor.getString(1));
				sysInfo.setHostName(cursor.getString(2));
				sysInfo.setIPID(Integer.parseInt(cursor.getString(3)));
				sysInfo.setCIPPort(Integer.parseInt(cursor.getString(4)));
				sysInfo.setWebPort(Integer.parseInt(cursor.getString(5)));
				sysInfo.setUseSSL(Boolean.parseBoolean(cursor.getString(6)));
				sysInfo.setUserName(cursor.getString(7));
				sysInfo.setPassword(cursor.getString(8));
                systemList.add(sysInfo);
            } while (cursor.moveToNext());
        }
 
        // return system list
        return systemList;
    }
 
    // Updating single system
    public int updateSystem(SystemInfo sysInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(SYSTEM_NAME, sysInfo.getSystemName());
        values.put(HOST_NAME, sysInfo.getHostName());
		values.put(IPID, sysInfo.getIPID());
		values.put(CIP_PORT, sysInfo.getCIPPort());
		values.put(WEB_PORT, sysInfo.getWebPort());
		values.put(USE_SSL, sysInfo.getUseSSL());
		values.put(USER_NAME, sysInfo.getUserName());
		values.put(PASSWORD, sysInfo.getPassword());
 
        // updating row
        return db.update(TABLE_SYSTEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(sysInfo.getID()) });
    }
 
    // Deleting single system
    public void deleteSystem(SystemInfo sysInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SYSTEMS, KEY_ID + " = ?",
                new String[] { String.valueOf(sysInfo.getID()) });
        db.close();
    }
 
 
    // Getting system Count
    public int getSystemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SYSTEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
}