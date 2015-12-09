package com.skula.cts.services;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapUtils {

	public static SoapSerializationEnvelope getEnvelope(SoapObject request) {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(request);
		return envelope;
	}

	private static HttpTransportSE getHttpTransportSE(String url) {
		// HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY, url, 60000);
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
		return ht;
	}

	public static String getResponse(String action, String url, SoapSerializationEnvelope envelope) {
		String data = null;
		HttpTransportSE ht = SoapUtils.getHttpTransportSE(url);
		try {
			ht.call(action, envelope);
			// testHttpResponse(ht);
			SoapObject  resultsString = (SoapObject ) envelope.bodyIn;
			
			 SoapObject root = (SoapObject) resultsString.getProperty(0);
	         SoapObject s_deals = (SoapObject) root.getProperty("ListeArret");

	           StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < s_deals.getPropertyCount(); i++) 
				{
				    Object property = s_deals.getProperty(i);
				    if (property instanceof SoapObject)
				    {
				        SoapObject category_list = (SoapObject) property;
				        String label = category_list.getProperty("Libelle").toString();
				        String id = category_list.getProperty("Code").toString();
				        stringBuilder.append
				        (
				             "Row value of: " +(i+1)+"\n"+
				             "Libelle: "+label+"\n"+
				             "Code: "+id+"\n"+
				             "******************************"
				        );                   
				        stringBuilder.append("\n");
				    }
				}
			/*
			 * List COOKIE_HEADER =
			 * (List)ht.getServiceConnection().getResponseProperties();
			 * 
			 * for (int i = 0; i < COOKIE_HEADER.size(); i++) { String key =
			 * COOKIE_HEADER.get(i).getKey(); String value =
			 * COOKIE_HEADER.get(i).getValue();
			 * 
			 * if (key != null && key.equalsIgnoreCase("set-cookie")) {
			 * SoapRequests.SESSION_ID = value.trim(); Log.v("SOAP RETURN",
			 * "Cookie :" + SoapRequests.SESSION_ID); break; } }
			 */
			data = resultsString.toString();
			
			JSONObject jo = new JSONObject(data);

		} catch (SocketTimeoutException t) {
			t.printStackTrace();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (Exception q) {
			q.printStackTrace();
		}

		return data;
	}
}