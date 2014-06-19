package com.fi.fimanagment;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import org.zeromq.*;

public class MainActivity extends Activity {
	
	
	public void changeIoState(int v){
		final Integer pin_id = v;
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
				String command = "set:"+pin_id.toString()+";";
				Log.d("command", command);
			    requester.send(command.getBytes(), 0);
			    requester.recv(0);
			    				    
	            /* ZMQ close */
			    requester.close();
		        context.term();
			};
		}).start();
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		for (int i =0; i < 4 ; i++){
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
			final Button button = new Button(this);
			int buttonId= i+1;
			
			/* set id*/
			button.setId(buttonId);
			
			/* set button label */
			Resources res = getResources();
			String text = String.format(res.getString(R.string.button_label),buttonId);
			button.setText(text);
			
			/* assign listner */
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					changeIoState(button.getId());
				}
			});
			
			
			/* set active after click*/
		    //button.setFocusableInTouchMode(true);
			
			/* add button to main linear  layout */
			linearLayout.addView(button);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
