package com.vish.findnearby.geonames;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class to create the nearby list
 * 
 * @author Vish
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NearbyList {
	List<Nearby> geonames;

	public List<Nearby> getGeonames() {
		return geonames;
	}

	public void setGeonames(List<Nearby> geonames) {
		this.geonames = geonames;
	}
}
