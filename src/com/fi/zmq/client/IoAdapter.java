package com.fi.zmq.client;

public class IoAdapter {
	
	// set pin status
	// get pin status
	// set all pins
	// -- encrypt all pins
	// get all pin status
	//	-- decrypt pin status
	// speratation sentiel: ":"
	// end sentiel ";"
	
	//
	// Single read/write;
	// @ToDo add interfaces with one arugment (pin requested) to toggle state.
	//
	private static String status;
	
	public String set_pin(int pinRequested, int pinState){
		String pin = "set:"+pinRequested+":"+pinState+";";
		return pin;
	}
	
	public String set_pin(int pinRequested){
		String pin = "set:"+pinRequested+";";
		return pin;
	}
	
	public String get_pin(int pinRequested){
		String pin = "get:"+pinRequested+";";
		return pin;
	}
	
	//
	//	Group read/write
	//
	public String set_all_pins(int[] pins){
		String command = "set_all_pins";
			for (int x : pins )
				command += ":"+x;
			command+=";";
		return command;
	}
	
	public String set_all_pins_enc(int pins){
		return "set_all_pins_enc;";
	}
	
	public String get_all_pins(){
		return "get_all_pins;";
	}
	
	public String get_all_pins_enc(){
		return "get_all_pins_enc;";
	}
	
	
	//
	// machine state - 
	//	@ToDo rewrite as enum field, and use it
	//
	public String get_app_status(){
		//actual
		//unknown
		//require_update
		return IoAdapter.status;
	}
	
	public void set_app_status(String incomeStatus){
		/*not compatible with 1.6 (used currently by android...)
		 * try {
			switch (incomeStatus){
			case "actual":
				IoAdapter.status = incomeStatus;
			case "unknown":
				IoAdapter.status = incomeStatus;
			case "require_update":
				IoAdapter.status = incomeStatus;
			default:
				Exception e = null;
					throw e; 
			} 
		}
			catch (Exception e1) {
				System.out.println("Unknown machine state");
				e1.printStackTrace();
			}*/
		
	}
}
