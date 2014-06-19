package com.fi.fimanagment;

import com.fi.fimanagment.dummy.DummyContent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SocketDetailFragment extends Fragment {
	
	public static final String ARG_ITEM_ID = "item_id";
	private DummyContent.DummyItem mItem;
	public SocketDetailFragment() {
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_ID)){
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanBundle){
		View rootView = inflater.inflate(R.layout.fragment_socket_detail, container, false);
		
		//Show dummy text
		if (mItem != null){
			rootView.findViewById(R.id.socket_detail);
		}
		return rootView;
	}
}
