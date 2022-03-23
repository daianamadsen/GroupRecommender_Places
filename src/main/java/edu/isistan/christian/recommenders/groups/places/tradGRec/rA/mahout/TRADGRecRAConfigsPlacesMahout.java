package edu.isistan.christian.recommenders.groups.places.tradGRec.rA.mahout;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.tradGRec.rA.TRADGRecRAConfigs;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;
import edu.isistan.christian.recommenders.sur.places.mahout.MahoutPlacesSUR;
import edu.isistan.christian.recommenders.sur.places.mahout.MahoutPlacesSURConfigs;

public class TRADGRecRAConfigsPlacesMahout extends TRADGRecRAConfigs<PlaceItem>{
	
	MahoutPlacesSURConfigs mahoutConfigs;
	
	public TRADGRecRAConfigsPlacesMahout(String configsPath)
			throws ConfigurationException {
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
	
	@Override
	public List<String> getRequiredProperties() {
		List<String> rProp = super.getRequiredProperties();
		rProp.addAll(mahoutConfigs.getRequiredProperties());
		
		return rProp;
	}
}
