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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.skula.cts.R;
import com.skula.cts.activities.adapters.ScheduleAdapter;
import com.skula.cts.models.BusStop;
import com.skula.cts.models.Schedule;
import com.skula.cts.services.CTSService;

public class MainActivity extends Activity {
	private AutoCompleteTextView searchStop;
	private EditText searchTime;
	private Button btnSearch;
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

		this.searchStop = (AutoCompleteTextView) findViewById(R.id.search_stop);
		searchStop.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 4) {
					getBusStops(s.toString());
				} else {
					busStops.clear();
				}
			}
		});

		this.btnSearch = (Button) findViewById(R.id.btn_search);

		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String t = searchTime.getText().toString();
				String s = searchStop.getText().toString();
				if (!t.isEmpty() && !s.isEmpty()){
					if(t.matches("[0-2]?[0-9]:[0-5][0-9]")) {
						String c = getStopCode(s);
						if (c != null) {
							updateListe(t, c);
						}else{								
							Toast.makeText(v.getContext(), "L'arrêt est inconnu", Toast.LENGTH_SHORT).show();
						}
					}else{						
						Toast.makeText(v.getContext(), "L'heure est invalide (HH:mm)", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(v.getContext(), "L'heure ou le nom de l'arrêt est vide", Toast.LENGTH_SHORT).show();
				}
				updateDate();
			}
		});

		this.scheduleList = (ListView) findViewById(R.id.schedule_list);
	}

	private void updateDate() {
		Calendar date = Calendar.getInstance();
		searchTime.setText(new SimpleDateFormat("HH:mm").format(date.getTime()));
	}

	public String getStopCode(String label) {
		try{
			for (BusStop bs : busStops) {
				if (bs.getLabel().equals(label)) {
					return bs.getId();
				}
			}
		}catch(Exception e){
			Toast.makeText(this, "Aucune connexion réseau établie", Toast.LENGTH_SHORT).show();
		}
		return null;
	}

	private void getBusStops(String search) {
		busStops = CTSService.getBusStops(search);
		busStops.size();
		String s[] = getTab();
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, getTab());
		searchStop.setAdapter(adapter);
	}

	private String[] getTab() {
		List<String> res = new ArrayList<String>();
		for (BusStop bs : busStops) {
			res.add(bs.getLabel());
		}
		return (String[]) res.toArray(new String[res.size()]);
	}

	private void updateListe(String time, String stopCode) {
		List<Schedule> list = CTSService.getSchedules(time, stopCode, "1");
		Schedule itemArray[] = (Schedule[]) list.toArray(new Schedule[list.size()]);
		ScheduleAdapter adapter = new ScheduleAdapter(this, R.layout.schedule_item_layout, itemArray);
		scheduleList.setAdapter(adapter);
	}
}
