package com.skula.cts.services;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

public class CTSService {
	private static final String NAMESPACE = "http://www.cts-strasbourg.fr/";
	private static final String MAIN_REQUEST_URL = "http://opendata.cts-strasbourg.fr/webservice_v4/Service.asmx";
	
	private static final String SOAP_ACTION_STOPS = "http://www.cts-strasbourg.fr/rechercherCodesArretsDepuisLibelle";
	private static final String SOAP_ACTION_SCHEDULE = "http://www.cts-strasbourg.fr/rechercheProchainesArriveesWebResponse";

	private static final String METHODE_SEARCH_BUSSTOP = "rechercherCodesArretsDepuisLibelle";
	private static final String METHODE_NEXT_TIME = "rechercheProchainesArriveesWeb";

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

	public static String getBusStopsRequest(String stopName) {
		SoapObject request = new SoapObject(NAMESPACE, METHODE_SEARCH_BUSSTOP);
		request.addProperty("Saisie", stopName);
		request.addProperty("NoPage", "1");

		SoapSerializationEnvelope envelope = SoapUtils.getEnvelope(request);
		envelope.headerOut = new Element[1];
		envelope.headerOut[0] = buildHeader();

		String response = SoapUtils.getResponse(SOAP_ACTION_STOPS, MAIN_REQUEST_URL, envelope);
		return response += "";
	}

	public static void getTime(String time, String stopId, String nbTimes) {
		SoapObject request = new SoapObject(NAMESPACE, METHODE_NEXT_TIME);
		request.addProperty("CodeArret", stopId);
		request.addProperty("Mode", "0");
		request.addProperty("Heure", time);
		request.addProperty("NbHoraires", nbTimes);

		SoapSerializationEnvelope envelope = SoapUtils.getEnvelope(request);
		envelope.headerOut = new Element[1];
		envelope.headerOut[0] = buildHeader();

		String response = SoapUtils.getResponse(SOAP_ACTION_SCHEDULE, MAIN_REQUEST_URL, envelope);
		
		
		StringBuilder stringBuilder = new StringBuilder();

		
		response += "";
	}
}