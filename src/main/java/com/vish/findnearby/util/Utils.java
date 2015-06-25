package com.vish.findnearby.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.vish.findnearby.exception.RateLimitException;
import com.vish.findnearby.geonames.City;
import com.vish.findnearby.geonames.NearbyList;

/**
 * Utils class for misc methods
 * 
 * @author Vish
 *
 */
public class Utils {
	private static Log log = LogFactory.getLog(Utils.class);
	private static String errorMsg = "the daily limit of 30000 credits for demo has been exceeded";

	public static String getHttpResponse (String cityUrl) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		StringBuffer responseBuffer = new StringBuffer();

		try {
			HttpGet httpget = new HttpGet(cityUrl);

			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					BufferedReader in =
							new BufferedReader(new InputStreamReader(instream));
					String inputLine;

					try {
						while ((inputLine = in.readLine()) != null) {
							responseBuffer.append(inputLine);
						}
					} catch (IOException ex) {
						throw ex;
					} finally {
						instream.close();
					}
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return responseBuffer.toString();
	}	

	/**
	 * Get a list of cities given a url and map the XML output to an ArrayList of City objects
	 * @param cityListUrl
	 * @return
	 * @throws RateLimitException 
	 */
	public static List<City> getCityList(String cityListUrl) throws RateLimitException {
		String xmlResponse = null;
		List<City> cityList = new ArrayList<>();

		try {
			xmlResponse = Utils.getHttpResponse(cityListUrl);

			if (xmlResponse.indexOf(errorMsg) != -1) {
				log.error("Daily access limit exceeded, try again tomorrow");
				throw new RateLimitException("Rate Limit Exceeded, try again tomorrow!");
			}
			ObjectMapper xmlMapper = new XmlMapper();
			cityList = xmlMapper.readValue(xmlResponse, new TypeReference<List<City>>() {});
		} catch (IOException e) {
			log.error("Error sending/getting content");
			e.printStackTrace();
		}
		return cityList;

	}

	public static NearbyList getNearbyList(String nearbyUrl) {
		String nearbyResponse;
		NearbyList nearbyList = new NearbyList();
		try {
			nearbyResponse = Utils.getHttpResponse(nearbyUrl);
			ObjectMapper objectMapper = new ObjectMapper();
			nearbyList = objectMapper.readValue(nearbyResponse, new TypeReference<NearbyList>() {});
		} catch (IOException e) {
			log.error("Error sending/getting content");
			e.printStackTrace();
		}
		return nearbyList;
	}
}

