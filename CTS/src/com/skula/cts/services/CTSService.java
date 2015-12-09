package com.skula.cts.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import com.skula.cts.models.BusStop;
import com.skula.cts.models.Schedule;

public class CTSService {
	private static final String NAMESPACE = "http://www.cts-strasbourg.fr/";
	private static final String MAIN_REQUEST_URL = "http://opendata.cts-strasbourg.fr/webservice_v4/Service.asmx";
	
	private static final String SOAP_ACTION_BUSSTOPS = "http://www.cts-strasbourg.fr/rechercherCodesArretsDepuisLibelle";
	private static final String SOAP_ACTION_SCHEDULE = "http://www.cts-strasbourg.fr/rechercheProchainesArriveesWeb";

	private static final String METHODE_SEARCH_BUSSTOP = "rechercherCodesArretsDepuisLibelle";
	private static final String METHODE_SCHEDULE = "rechercheProchainesArriveesWeb";

	private static Element buildHeader() {
		Element id = new Element().createElement(NAMESPACE, "ID");
		id.addChild(Node.TEXT, "1419");
		Element mdp = new Element().createElement(NAMESPACE, "MDP");
		mdp.addChild(Node.TEXT, "edcvbn");
		Element h = new Element().createElement(NAMESPACE, "CredentialHeader");
		h.addChild(Node.ELEMENT, id);
		h.addChild(Node.ELEMENT, mdp);
		return h;
	}

	public static List<BusStop> getBusStops(String stopName) {
		List<BusStop> res = new ArrayList<BusStop>();
		
		SoapObject request = new SoapObject(NAMESPACE, METHODE_SEARCH_BUSSTOP);
		request.addProperty("Saisie", stopName);
		request.addProperty("NoPage", "1");

		SoapSerializationEnvelope envelope = SoapUtils.getEnvelope(request);
		envelope.headerOut = new Element[1];
		envelope.headerOut[0] = buildHeader();

		SoapObject response = SoapUtils.getResponse(SOAP_ACTION_BUSSTOPS, MAIN_REQUEST_URL, envelope);
		SoapObject root = (SoapObject) response.getProperty(0);
		SoapObject s_deals = (SoapObject) root.getProperty("ListeArret");

		for (int i = 0; i < s_deals.getPropertyCount(); i++) 
		{
			Object property = s_deals.getProperty(i);
			if (property instanceof SoapObject)
			{
				res.add(new BusStop((SoapObject)property));				
			}
		}
		
		return res;
	}

	public static List<Schedule> getSchedules(String time, String stopId, String nbTimes) {
		List<Schedule> res = new ArrayList<Schedule>();
		
		SoapObject request = new SoapObject(NAMESPACE, METHODE_SCHEDULE);
		request.addProperty("CodeArret", stopId);
		request.addProperty("Mode", "0");
		request.addProperty("Heure", time);
		request.addProperty("NbHoraires", nbTimes);

		SoapSerializationEnvelope envelope = SoapUtils.getEnvelope(request);
		envelope.headerOut = new Element[1];
		envelope.headerOut[0] = buildHeader();

		SoapObject response = SoapUtils.getResponse(SOAP_ACTION_SCHEDULE, MAIN_REQUEST_URL, envelope);
		SoapObject root = (SoapObject) response.getProperty(0);
		SoapObject s_deals = (SoapObject) root.getProperty("ListeArrivee");

		for (int i = 0; i < s_deals.getPropertyCount(); i++) 
		{
			Object property = s_deals.getProperty(i);
			if (property instanceof SoapObject)
			{
				try {
					res.add(new Schedule((SoapObject)property));
				} catch (ParseException e) {
					e.printStackTrace();
				}				
			}
		}
		
		return res;
	}
}