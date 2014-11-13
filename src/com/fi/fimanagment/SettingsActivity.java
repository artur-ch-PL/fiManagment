package com.fi.fimanagment;

import com.fi.finamanagment.model.DataBaseManager;
import com.fi.finamanagment.model.DbButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity {
	public static final String BUTTON_PREFERENCES = "ButtonPreferences";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
	@Override 
	public void onStart(){
		super.onStart();
		bindEditTextPreferences();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		PreferenceScreen settingsScreen = (PreferenceScreen)getPreferenceScreen().findPreference("settings");
		settingsScreen.removeAll();
		bindEditTextPreferences();
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		PreferenceScreen settingsScreen = (PreferenceScreen)getPreferenceScreen().findPreference("settings");
		settingsScreen.removeAll();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	// save to database modified button label
	Preference.OnPreferenceChangeListener changeListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			DataBaseManager dbManager = new DataBaseManager(getBaseContext());	
			DbButton dbButton = dbManager.getDbButtonBySocketNumber(Integer.parseInt(preference.getKey()));
			dbButton.setLabel(newValue.toString());
			dbManager.updateDbButtonLabel(dbButton);
			dbManager.close();
			PreferenceScreen settingsScreen = (PreferenceScreen)getPreferenceScreen().findPreference("settings");
			settingsScreen.removeAll();
			bindEditTextPreferences();
			return true;
		}
	};
	
	// bind buttons
	private void bindEditTextPreferences(){
		PreferenceScreen settingsScreen = (PreferenceScreen)getPreferenceScreen().findPreference("settings");
		SharedPreferences screenSharedPreferences = getSharedPreferences(BUTTON_PREFERENCES,  MODE_PRIVATE);
		DataBaseManager dbManager = new DataBaseManager(getBaseContext());	
		EditTextPreference editTextPreference;
		Integer size = dbManager.size();
		Integer editTextPreferenceKey;
		Integer dialogTitle;
		
		for (int i = 0; i < size; i++){
			
			editTextPreference = new EditTextPreference(this);
			editTextPreferenceKey = dbManager.getDbButtonBySocketNumber(i).getSocket_number(); // since 0 to max. number of socket. Key to make ID for Edit Preferences
			dialogTitle = editTextPreferenceKey+1;
			editTextPreference.setKey(editTextPreferenceKey.toString());			
			editTextPreference.setTitle("Socket ("+dbManager.getDbButtonBySocketNumber(i).getLabel()+")");
			if (i==0){
				editTextPreference.setDialogTitle("Socket");
			}
			else {
				editTextPreference.setDialogTitle("Socket "+dialogTitle.toString());
			} 
			editTextPreference.setOnPreferenceChangeListener(changeListener);
			editTextPreference.setDefaultValue("Socket "+dialogTitle.toString());
			settingsScreen.addPreference(editTextPreference);
			
			SharedPreferences.Editor preferencesEditor = screenSharedPreferences.edit();
			preferencesEditor.putString(editTextPreference.getKey(), dbManager.getDbButtonBySocketNumber(i).getLabel());
			preferencesEditor.commit();
		}
		dbManager.close();
	}
	
}