package com.skula.cts.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ksoap2.serialization.SoapObject;

public class Schedule {
	private String mode;
	private int numberPict;
	private String destination;
	private String time;
	private boolean midnight;

	public Schedule() {
	}

	public Schedule(String mode, int numberPict, String destination, String time, boolean midnight) {
		this.mode = mode;
		this.numberPict = numberPict;
		this.destination = destination;
		this.time = time;
		this.midnight = midnight;
	}

	public Schedule(SoapObject obj) throws ParseException {
		this.mode = obj.getProperty("Mode").toString();

		String tmp = obj.getProperty("Destination").toString();
		String plop = tmp.substring(0, tmp.indexOf(" "));
		this.numberPict = Bus.getPict(plop);
		this.destination = tmp.substring(tmp.indexOf(" ") + 1);

		if (obj.getProperty("EstApresMinuit").toString().equals("false")) {
			this.midnight = false;
		} else {
			this.midnight = true;
		}

		Date date = new SimpleDateFormat("HH:mm:ss").parse(obj.getProperty("Horaire").toString());
		this.time = new SimpleDateFormat("HH:mm").format(date);
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getNumberPict() {
		return numberPict;
	}

	public void setNumberPict(int numberPict) {
		this.numberPict = numberPict;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public boolean isMidnight() {
		return midnight;
	}

	public void setMidnight(boolean midnight) {
		this.midnight = midnight;
	}

	public String getTimeLeft() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
		date.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
		long diff = date.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		return (diff / (1000 * 60)) + " min";
	}
}