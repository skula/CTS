package com.skula.cts.models;

import org.ksoap2.serialization.SoapObject;

public class BusStop {
	private String label;
	private String id;

	public BusStop() {
	}

	public BusStop(String label, String id) {
		this.label = label;
		this.id = id;
	}

	public BusStop(SoapObject obj) {
		this.label = obj.getProperty("Libelle").toString();
		this.id = obj.getProperty("Code").toString();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}