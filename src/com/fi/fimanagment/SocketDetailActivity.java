package com.fi.fimanagment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SocketDetailActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInsatenceState){
		super.onCreate(savedInsatenceState);
		setContentView(R.layout.activity_socket_detail);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (savedInsatenceState == null){
			Bundle arguments = new Bundle();
			arguments.putString(SocketDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(SocketDetailFragment.ARG_ITEM_ID));
			
			SocketDetailFragment fragment = new SocketDetailFragment();
			fragment.setArguments(arguments);
			
			getSupportFragmentManager().beginTransaction().add(R.id.socket_detail_container, fragment).commit();
		}
	}
}
