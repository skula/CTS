package com.skula.cts.models;

public class Schedule {
	private String mode;
	private String id;
	private String direction;
	private String time;

	public Schedule() {
	}

	public Schedule(String mode, String id, String direction, String time) {
		this.mode = mode;
		this.id = id;
		this.direction = direction;
		this.time = time;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}