package com.skula.cts.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.skula.cts.R;
import com.skula.cts.services.CTSService;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);	
		setContentView(R.layout.main_layout);
		

		TextView btnVerbsEng = (TextView) findViewById(R.id.message);
		String msg = CTSService.getBusStopsRequest("Saint");
		btnVerbsEng.setText(msg);
		
	}
}
