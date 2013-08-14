package com.csic.player;

import android.app.Application;

public class MyApp extends Application{
	  private int myState=1;  
	  
	  public int getState(){  
	    return myState;  
	  }  
	  public void setState(int s){  
	    myState = s;  
	  } 
	
}
