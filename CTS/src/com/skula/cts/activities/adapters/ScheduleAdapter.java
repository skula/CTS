package com.skula.cts.activities.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skula.cts.R;
import com.skula.cts.models.Schedule;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {

	Context context;
	int layoutResourceId;
	Schedule data[] = null;

	public ScheduleAdapter(Context context, int layoutResourceId, Schedule[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Schedule shdl = data[position];
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.schedule_item_layout, parent, false);

		ImageView number = (ImageView) rowView.findViewById(R.id.schedule_pict);
		int i = shdl.getNumberPict();
		number.setImageResource(shdl.getNumberPict());
		//number.setImageResource(R.drawable.plop);
		TextView destination = (TextView) rowView.findViewById(R.id.schedule_destination);
		destination.setText(shdl.getDestination());
		TextView timeLeft = (TextView) rowView.findViewById(R.id.schedule_timeleft);
		timeLeft.setText(shdl.getTimeLeft());
		TextView time = (TextView) rowView.findViewById(R.id.schedule_time);
		time.setText("(" + shdl.getTime() + ")");

		return rowView;
	}
}