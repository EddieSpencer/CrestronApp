package com.TCNJ.crestronsqlite;

import java.security.InvalidParameterException;





public class SystemInfo {
	int _id;
	String _systemName; //unique
	String _hostName;
	int _ipid; //hex 0x03 - 0xff (03-255)
	int _cipPort; // (1-65535)
	int _webPort;
	boolean _useSSL;
	String _userName;
	String _password;
	
	public SystemInfo(){
		
	}
	
	
	public SystemInfo(String systemName, String hostName, int ipid, int cipPort, 
			int webPort, boolean useSSL, String userName, String password){
		this._systemName = systemName;
		this._hostName = hostName;
		setIPID(ipid);
		setCIPPort(cipPort);
		setWebPort(webPort);
		setUseSSL(useSSL);
		this._userName = userName;
		this._password = password;
	}
	
	public SystemInfo(int id, String systemName, String hostName, int ipid, int cipPort, 
			int webPort, boolean useSSL, String userName, String password){
		this._id = id;
		this._systemName = systemName;
		this._hostName = hostName;
		setIPID(ipid);
		setCIPPort(cipPort);
		setWebPort(webPort);
		setUseSSL(useSSL);
		this._userName = userName;
		this._password = password;
	}
	
	public int getID(){
		return this._id;
	}
	
	public void setID(int id){
		this._id = id;
	}
	public String getSystemName(){
		return this._systemName;
	}
	
	public void setSystemName(String systemName){
		this._systemName = systemName;
	}
	
	public String getHostName(){
		return this._hostName;
	}
	
	public void setHostName(String hostName){
		this._hostName = hostName;
	}
	
	public int getIPID(){
		return this._ipid;
	}
	
	public void setIPID(int IPID){
		if(IPID >= 3 && IPID <= 255)
			this._ipid = IPID;
		else
			throw new InvalidParameterException("IPID between 3 - 255");
	}
	
	public int getCIPPort(){
		return this._cipPort;
	}
	
	public void setCIPPort(int CIPPort){
		if(CIPPort >= 1 && CIPPort <=65535)
			this._cipPort = CIPPort;
		else
			throw new InvalidParameterException("CIPPort between 1 - 65535");
	}
	
	public int getWebPort(){
		return this._webPort;
	}
	
	public void setWebPort(int webPort){
		this._webPort = webPort;
	}
	public boolean getUseSSL(){
		return this._useSSL;
	}
	
	public void setUseSSL(boolean useSSL){
		this._useSSL = useSSL;
	}
	
	public String getUserName(){
		return this._userName;
	}
	
	public void setUserName(String userName){
		this._userName = userName;
	}
	
	public String getPassword(){
		return this._password;
	}
	
	public void setPassword(String password){
		this._password = password;
	}
	
	
	
}
