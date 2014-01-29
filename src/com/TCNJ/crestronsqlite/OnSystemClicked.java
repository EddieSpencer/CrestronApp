package com.TCNJ.crestronsqlite;

import android.view.View.OnClickListener;

//Simple Class to keep track of selected system's ID in loop
public abstract class OnSystemClicked implements OnClickListener{
	int id;
	
	//Constructor
	public  OnSystemClicked(int id){
		this.id = id;
		
	}
	

}
