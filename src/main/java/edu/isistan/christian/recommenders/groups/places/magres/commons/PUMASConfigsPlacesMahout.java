package edu.isistan.christian.recommenders.groups.places.magres.commons;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.commons.pumas.PUMASConfigs;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;
import edu.isistan.christian.recommenders.sur.places.mahout.MahoutPlacesSUR;
import edu.isistan.christian.recommenders.sur.places.mahout.MahoutPlacesSURConfigs;

public class PUMASConfigsPlacesMahout extends PUMASConfigs<PlaceItem>{
	
	MahoutPlacesSURConfigs mahoutConfigs;

	public PUMASConfigsPlacesMahout(String configsPath) throws ConfigurationException {
		super(configsPath);
	}
	
	@Override
	protected MahoutPlacesSUR buildSUR(String configsPath) throws ConfigurationException {
		mahoutConfigs = new MahoutPlacesSURConfigs(configsPath);
		
		//Build Movies SUR		
		return new MahoutPlacesSUR(mahoutConfigs.getMahoutRec(), mahoutConfigs.getDSLoader(), mahoutConfigs.getDSLoaderConfig(),
				mahoutConfigs.getMahoutRecConfig());
	}
	
	public MahoutPlacesSUR getSUR(){
		return (MahoutPlacesSUR) this.singleUserRecommender;
	}

}
