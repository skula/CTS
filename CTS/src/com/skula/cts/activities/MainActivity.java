package com.skula.cts.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UpdateLayout;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.skula.cts.R;
import com.skula.cts.activities.adapters.ScheduleAdapter;
import com.skula.cts.models.BusStop;
import com.skula.cts.models.Schedule;
import com.skula.cts.services.CTSService;


public class MainActivity extends Activity {
	private AutoCompleteTextView  searchStop;
	private EditText searchTime;
	private ListView scheduleList;
	
	private List<BusStop> busStops;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);	
		setContentView(R.layout.main_layout);

		this.busStops = new ArrayList<BusStop>();
		this.searchTime = (EditText) findViewById(R.id.search_time);

		updateDate();
		
		this.searchStop = (AutoCompleteTextView ) findViewById(R.id.search_stop);
		searchStop.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {                

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()>4){
					prout(s.toString());
				}else{
					busStops.clear();
				}
				/*//String txt = searchStop.getText().toString();
				if(txt.length()>3){
					//List<BusStop> l = CTSService.getBusStop(searchStop.getText().toString());
					//ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, CTSService.getBusStop("610", "16:00", "1"));
					//textView.setAdapter(adapter);
				}else{
					// vider la lsite
				}*/
			}
		});
		
		this.scheduleList = (ListView) findViewById(R.id.schedule_list);
		updateListe();
	}
	
	private void updateDate(){
		Calendar date = Calendar.getInstance();
		searchTime.setText(new SimpleDateFormat("HH:mm").format(date.getTime()));
	}

	private void prout(String search){	
		busStops = CTSService.getBusStops(search);
		busStops.size();
		String s[] = getTab();
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,getTab() );
		searchStop.setAdapter(adapter);
	}
	
	private String[] getTab(){
		List<String> res = new ArrayList<String>();
		for(BusStop bs :busStops ){
			res.add(bs.getLabel());
		}
		return (String[]) res.toArray(new String[res.size()]);
	}
	
	private void updateListe(){	
		List<Schedule> list = CTSService.getSchedules("18:20", "610", "1");
		Schedule itemArray[] = (Schedule[]) list.toArray(new Schedule[list.size()]);
		ScheduleAdapter adapter = new ScheduleAdapter(this, R.layout.schedule_item_layout, itemArray);
		scheduleList.setAdapter(adapter);
	}
}
