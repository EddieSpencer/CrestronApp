package com.TCNJ.crestronsqlite;

import java.security.InvalidParameterException;

//SystemsInfo class
public class SystemInfo {
	int _id;	//ID in SQlite Database
	String _systemName; //Unique string 
	String _hostName;
	int _ipid; //(03-255)
	int _cipPort; // (1-65535)
	int _webPort;
	boolean _useSSL; //true or false
	String _userName;
	String _password;
	
	//Empty Constructor
	public SystemInfo(){
		}
	
	//Constructor for SystemInfo Object
	public SystemInfo(String systemName, String hostName, int ipid, int cipPort, 
			int webPort, boolean useSSL, String userName, String password){
		this._systemName = systemName;
		this._hostName = hostName;
		//checks for errors with typing ranges
		setIPID(ipid);
		setCIPPort(cipPort);
		setWebPort(webPort);
		setUseSSL(useSSL);
		this._userName = userName;
		this._password = password;
	}
	
	//Constructor for SystemInfo Object including Database ID
	public SystemInfo(int id, String systemName, String hostName, int ipid, int cipPort, 
			int webPort, boolean useSSL, String userName, String password){
		this._id = id;
		this._systemName = systemName;
		this._hostName = hostName;
		//checks for errors with typing ranges
		setIPID(ipid);
		setCIPPort(cipPort);
		setWebPort(webPort);
		setUseSSL(useSSL);
		this._userName = userName;
		this._password = password;
	}
	
	//Returns a System's ID Database location
	public int getID(){
		return this._id;
	}
	//Sets a System's ID
	public void setID(int id){
		this._id = id;
	}
	
	//Returns a System's Name
	public String getSystemName(){
		return this._systemName;
	}
	//Sets a System's Name
	public void setSystemName(String systemName){
		this._systemName = systemName;
	}
	
	//Returns a System's Hostname
	public String getHostName(){
		return this._hostName;
	}
	//Sets a System's Hostname
	public void setHostName(String hostName){
		this._hostName = hostName;
	}
	
	//Returns a System's IPID
	public int getIPID(){
		return this._ipid;
	}
	
	//Sets a System's IPID
	//Checks for valid data range and throws exception if invalid
	public void setIPID(int IPID){
		if(IPID >= 3 && IPID <= 255)
			this._ipid = IPID;
		else
			throw new InvalidParameterException("IPID between 3 - 255");
	}
	
	//Returns a System's CIPPort
	public int getCIPPort(){
		return this._cipPort;
	}
	
	//Sets a System's CIPPort
	//Checks for valid data range and throws exception if invalid
	public void setCIPPort(int CIPPort){
		if(CIPPort >= 1 && CIPPort <=65535)
			this._cipPort = CIPPort;
		else
			throw new InvalidParameterException("CIPPort between 1 - 65535");
	}
	
	//Returns a System's WebPort
	public int getWebPort(){
		return this._webPort;
	}
	//Sets a System's WebPort
	public void setWebPort(int webPort){
		this._webPort = webPort;
	}
	
	//Returns a System's UseSSL
	public boolean getUseSSL(){
		return this._useSSL;
	}
	//Sets a System's UseSSL
	public void setUseSSL(boolean useSSL){
		this._useSSL = useSSL;
	}
	
	//Returns a System's Username
	public String getUserName(){
		return this._userName;
	}
	//Sets a System's Username
	public void setUserName(String userName){
		this._userName = userName;
	}
	
	//Returns a System's Password
	public String getPassword(){
		return this._password;
	}
	//Sets a System's Password
	public void setPassword(String password){
		this._password = password;
	}	
}
