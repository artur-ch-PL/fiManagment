package com.fi.fimanagment;
import com.fi.fimanagment.dummy.DummyContent.DummyItem;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SocketListFragment extends ListFragment{

		
	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	private Callbacks mCallbacks = sDummyCallbacks;
	private int mActivatedPosition = ListView.INVALID_POSITION;
	
	public interface Callbacks {
		public void onItemSelected(String id);
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};
	
	public SocketListFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		setListAdapter(new ArrayAdapter<DummyItem>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, com.fi.fimanagment.dummy.DummyContent.ITEMS));
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		super .onViewCreated(view, savedInstanceState);
		
		if (savedInstanceState != null
			&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)){
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callbacks)){
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}
		mCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = sDummyCallbacks;
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		mCallbacks.onItemSelected(com.fi.fimanagment.dummy.DummyContent.ITEMS.get(position).id);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}
	

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION){
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}
		mActivatedPosition = position;
	}

}
