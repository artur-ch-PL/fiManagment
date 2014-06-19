package com.fi.fimanagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SocketListActivity extends FragmentActivity 
				implements 
					SocketListFragment.Callbacks{
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		mTwoPane = true;
		((SocketListFragment) getSupportFragmentManager().findFragmentById(
				R.id.socket_list)).setActivateOnItemClick(true);
	}

	@Override
	public void onItemSelected(String id) {
		if (mTwoPane){
			Bundle arguments = new Bundle();
			arguments.putString(SocketDetailFragment.ARG_ITEM_ID, id);
			
			SocketDetailFragment fragment = new SocketDetailFragment();
			fragment.setArguments(arguments);
			
			getSupportFragmentManager().beginTransaction().replace(R.id.socket_detail_container, fragment).commit();
		}else {
			Intent detailIntent = new Intent(this, SocketDetailActivity.class);
			detailIntent.putExtra(SocketDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
		
	}

}
