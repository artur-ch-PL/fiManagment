package com.fi.finamanagment.model;

public class DbButton {

	private int id;
	private int socket_number;
	private String label;
	private int value;
	private String update_status;
	private String button_type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSocket_number() {
		return socket_number;
	}
	public void setSocket_number(int socket_number) {
		this.socket_number = socket_number;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getUpdate_status() {
		return update_status;
	}
	public void setUpdate_status(String string) {
		this.update_status = string;
	}
	public String getButton_type() {
		return button_type;
	}
	public void setButton_type(String string) {
		this.button_type = string;
	}
	
	
	
}
