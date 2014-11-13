package com.fi.fimanagment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import org.zeromq.*;
import com.fi.finamanagment.model.DataBaseManager;
import com.fi.finamanagment.model.DbButton;


/**
 * Author: Artur Chwedoruk
 * Version: 0.1
 * Name: FiManagment Application
 * License type: X11
 * Description:
 * 		Application for Android OS, which enable easy change output states on RPI via 0mq framework. 
 * 
 * Known limitations:
 *  @TODO socket labels should be moved to the end-point side (RPI). (currently each user device, contain own labels)
 *  @TODO no status for connection to the end-point
 *  @TODO after reset wifi router, require reset connection on the end-point device (RPI)
 * 
 * ToDo list
 * 	@TODO readout from end-point number of sockets
 *  @TODO build widgets to easy change socket state
 *  @TODO read current state of I/O
 *  @TODO set identifier on User side 
 *  @TODO rebuild code to use one command to set all i/o's
 *  @TODO enable easy WiFi configuration on RPI 
 *  	@TODO build searching device mechanism, make end-point device IP configurable
 *  
 * Dream list
 * 	@DREAM enable bluetooth 4 LE 
 **/

public class MainActivity extends Activity {
	private Integer MAX_SOCKET_NUMBER = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DataBaseManager dbManager = new DataBaseManager(this);	
		if (dbManager.size()==0){
			dbManager.initDb(MAX_SOCKET_NUMBER);
		}
		dbManager.close();
	}
	
	// @INFO - often remove buttons and add it again, to be sure if user change their labels, it will get properly label.
	@Override
	public void onStart(){
		super.onStart();
		this.bindButtons();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
		linearLayout.removeAllViews();
		this.bindButtons();
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
		linearLayout.removeAllViews();
	};
	
	@Override
	public void onPause(){
		super.onPause();
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
		linearLayout.removeAllViews();
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle press on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Context context = getApplicationContext();
				Intent intent = new Intent(context, SettingsActivity.class);
				startActivity(intent);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/*
	 * Private auxiliary functions
	 */
	
	// create buttons for view
	private void bindButtons(){
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
			DataBaseManager dbManager = new DataBaseManager(this);
			int buttonID;
			DbButton buttonDB;
			for (int i = 0; i < MAX_SOCKET_NUMBER ; i++){
				final Button button = new Button(this);
				buttonID = i+1;
				button.setId(buttonID);
				buttonDB = dbManager.getDbButtonBySocketNumber(i);
				button.setText(buttonDB.getLabel());
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						changeIoState(button.getId());
					}
				});
				linearLayout.addView(button);
			}
			dbManager.close();
		}
		
	// Proxy to FiManagment Unit
	private void changeIoState(int v){
		final Integer pinID = v;
		new Thread(new Runnable() {
			@Override
			public void run() {
				/* ZMQ open connection*/
				String host = "tcp://192.168.0.34:5555";
				
				/* ZMQ open connection*/						
				ZMQ.Context context = ZMQ.context(1);
				ZMQ.Socket requester = context.socket(ZMQ.REQ);
				requester.connect(host);
				
				/*  Set pin */
				String command = "set:"+pinID.toString()+";";
				Log.d("command", command);
			    requester.send(command.getBytes(), 0);
			    requester.recv(0);
			    				    
	            /* ZMQ close */
			    requester.close();
		        context.term();
			};
		}).start();
	}
	
}
