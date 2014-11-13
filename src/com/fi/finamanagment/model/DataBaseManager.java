package com.fi.finamanagment.model;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseManager extends SQLiteOpenHelper {
	private String TABLE_NAME = "db_buttons";
	private String COLUMN_0_ID = "id";
	private String COLUMN_1_SOCKET = "socket_number";
	private String COLUMN_2_LABEL = "label";
	private String COLUMN_3_VALUE = "value";
	private String COLUMN_4_UPDATE_STATUS = "update_status";
	private String COLUMN_5_BUTTON_TYPE = "button_type";
	
	public DataBaseManager (Context context) {
		super(context, "contacts.db", null, 1);
	}

	/*
	  COLUMN_0_ID 		- unique ID for row in database
	  COLUMN_1_SOCKET 	- socket number, count since 1
	  COLUMN_2_LABEL 	- button label 
	  COLUMN_3_VALUE	- for future use, current state (OFF - 0, ON - 1) 
	  COLUMN_4_UPDATE_STATUS	- for future use, to use state machine
	  COLUMN_5_BUTTON_TYPE 		- for future use, depend on button type, will be chose button image on widget
	*/
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"create table "+TABLE_NAME+"(" +
					COLUMN_0_ID + " integer primary key autoincrement," +
					COLUMN_1_SOCKET	+ " integer,"+
					COLUMN_2_LABEL 	+ " text,"+
					COLUMN_3_VALUE 	+ " integer,"+
					COLUMN_4_UPDATE_STATUS + " text,"+
					COLUMN_5_BUTTON_TYPE + " text);"+
						"");
	}

	
	public void initDb(int maxSockets){
		SQLiteDatabase db = getWritableDatabase();
		for(int i=0; i < maxSockets; i++){
				ContentValues values = new ContentValues();
				values.put(COLUMN_1_SOCKET, i);
				if(i == 0)
					values.put(COLUMN_2_LABEL, "Socket");
				else
					values.put(COLUMN_2_LABEL, "Socket "+(i+1));
				//TODO change initial value to be taken from device via ZMQ
				values.put(COLUMN_3_VALUE, "0");
				values.put(COLUMN_4_UPDATE_STATUS, "OK");
				values.put(COLUMN_5_BUTTON_TYPE, "default");
			db.insert(TABLE_NAME, null, values);
		}
		db.close();
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub	
	}
	
	public void addDbButton(DbButton db_button){
		SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(COLUMN_1_SOCKET, db_button.getSocket_number());
			values.put(COLUMN_2_LABEL, db_button.getLabel());
			values.put(COLUMN_3_VALUE, db_button.getValue());
			values.put(COLUMN_4_UPDATE_STATUS, db_button.getUpdate_status().toString());
			values.put(COLUMN_5_BUTTON_TYPE, db_button.getButton_type().toString());
		db.insert(TABLE_NAME, null, values);
	}
	
	public void updateDbButtonLabel(DbButton db_button){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
			values.put(COLUMN_1_SOCKET, db_button.getSocket_number());
			values.put(COLUMN_2_LABEL, db_button.getLabel());
			values.put(COLUMN_3_VALUE, db_button.getValue());
			values.put(COLUMN_4_UPDATE_STATUS, db_button.getUpdate_status().toString());
			values.put(COLUMN_5_BUTTON_TYPE, db_button.getButton_type().toString());
			String args[]={db_button.getSocket_number()+""};
			String where_statement = COLUMN_1_SOCKET+"=?";
			long i = db.update(TABLE_NAME, values, where_statement, args);
			if (i > 0)
				Log.d("db updated","db updated success");
			else 
				Log.d("db update", "error during update");
		db.close();
	}
	
	public void removeDbButton(int socket_number){
		SQLiteDatabase db = getWritableDatabase();
		String[] arguments={""+socket_number};
		String where_statement = COLUMN_1_SOCKET+"=?";
		db.delete(TABLE_NAME, where_statement, arguments);
	}
	
	public DbButton getDbButtonBySocketNumber(int socket_number){
		DbButton db_button = new DbButton();
		SQLiteDatabase db = getWritableDatabase();
			String[] columns = {COLUMN_0_ID, COLUMN_1_SOCKET, COLUMN_2_LABEL, COLUMN_3_VALUE, COLUMN_4_UPDATE_STATUS, COLUMN_5_BUTTON_TYPE};
			String args[] = {socket_number+""};
			String where_statement = COLUMN_1_SOCKET+" =?";
			Cursor cursor = db.query(TABLE_NAME, columns, where_statement, args, null, null, null, null);
			if (cursor != null){
				cursor.moveToFirst();
				db_button.setId(cursor.getInt(0));
				db_button.setSocket_number(cursor.getInt(1));
				db_button.setLabel(cursor.getString(2));
				db_button.setValue(cursor.getInt(3));
				db_button.setUpdate_status(cursor.getString(4));
				db_button.setButton_type(cursor.getString(5));
			}
			cursor.close();
			db.close();
			return db_button;
	}
	
	public List<DbButton> getAllDbButtons(){
		List<DbButton> db_buttons = new LinkedList<DbButton>();
		String[] columns = {COLUMN_0_ID, COLUMN_1_SOCKET, COLUMN_2_LABEL, COLUMN_3_VALUE, COLUMN_4_UPDATE_STATUS, COLUMN_5_BUTTON_TYPE};
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

		while (cursor.moveToNext()){
			DbButton db_button = new DbButton();
			db_button.setId(cursor.getInt(0));
			db_button.setSocket_number(cursor.getInt(1));
			db_button.setLabel(cursor.getString(2));
			db_button.setValue(cursor.getInt(3));
			db_button.setUpdate_status(cursor.getString(4));
			db_button.setButton_type(cursor.getString(5));
		}
		cursor.close();
		return db_buttons;
	}
	
	public int size(){
		SQLiteDatabase db = getReadableDatabase();
		Cursor mCursor = db.rawQuery("Select * from " + TABLE_NAME + ";", null);
		int size = mCursor.getCount();
		mCursor.close();
		db.close();
		return size;
	}
}
