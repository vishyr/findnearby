package com.vish.findnearby.main;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vish.findnearby.exception.RateLimitException;
import com.vish.findnearby.geonames.City;
import com.vish.findnearby.geonames.Nearby;
import com.vish.findnearby.geonames.NearbyList;
import com.vish.findnearby.geonames.PointOfInterest;
import com.vish.findnearby.util.Utils;

/**
 * This program makes a http call to get a list of cities matching the input string
 * and the lat/long fields from the response is used to fetch nearby points of interest 
 * 
 * @author Vish
 *
 */
public class FindNearby {

	private static final String CITY_LIST_URI = "http://api.geonames.org/wikipediaSearch";
	private static final String NEARBY_URI = "http://api.geonames.org/findNearbyJSON";
	private static final String USERNAME = "foobar";


	private static Log log = LogFactory.getLog(FindNearby.class);

	public static void main(String[] args) {

		// Check how many arguments were passed in
		if(args.length != 1) {
			System.out.println("No arguments found");
			System.out.println("Proper usage is: java FindNearby cityName");
			System.exit(0);
		}	    
		String inputCity = args[0].trim();		
		log.info("Getting city list for input " + inputCity);
		try {			
			String cityListUrl = CITY_LIST_URI + "?q=" + inputCity + "&maxRows=10&username=" + USERNAME + "&style=full";			
			// get city list
			List<City> cityList = Utils.getCityList(cityListUrl);	
			if (!cityList.isEmpty()) {
				// iterate thru city list to get nearby points of interest
				for (City city : cityList) {
					String latitude = city.getLat();
					String longitude = city.getLng();
					// create url to make nearby call 
					String nearbyUrl = NEARBY_URI + "?lat=" + latitude + "&lng=" + longitude + "&username=" + USERNAME;
					log.info("For the city of " + city.getTitle() + ", we have the following nearby points of interest");

					NearbyList nearbyList = Utils.getNearbyList(nearbyUrl);
					if (null != nearbyList && !nearbyList.getGeonames().isEmpty()) {
						// map to PointsOfInterest output object
						for (Nearby nearby : nearbyList.getGeonames()) {
							// map to PointsOfInterest object
							PointOfInterest poi = mapToPointsOfInterest(city, nearby);
							// convert to json
							ObjectMapper outpuMapper = new ObjectMapper();
							System.out.println(outpuMapper.writerWithDefaultPrettyPrinter().writeValueAsString(poi));
						}
					} else {
						log.info("No points of interest!");
					}
				}
			} else { 
				log.info("No cities to process!");
			}
			log.info("Done!");
		} catch (RateLimitException rle) {
			log.error(rle.getMessage());
			rle.printStackTrace();
		} catch (JsonProcessingException e) {
			log.error("Error processing json");
			e.printStackTrace();
		}
	}

	/**
	 * Create output object PointOfInterest
	 * @param city
	 * @param nearby
	 * @return PointOfInterest object
	 */
	private static PointOfInterest mapToPointsOfInterest (City city, Nearby nearby) {
		PointOfInterest poi = new PointOfInterest();
		poi.setTitle(city.getTitle());
		poi.setLng(nearby.getLng());
		poi.setLat(nearby.getLat());
		poi.setFcode(nearby.getFcode());
		poi.setCountryName(nearby.getCountryName());
		poi.setToponymName(nearby.getToponymName());
		poi.setDistance(nearby.getDistance());
		poi.setLang(city.getLang());
		poi.setCountryCode(city.getCountryCode());
		poi.setFeature(city.getFeature());

		return poi;
	}
}
