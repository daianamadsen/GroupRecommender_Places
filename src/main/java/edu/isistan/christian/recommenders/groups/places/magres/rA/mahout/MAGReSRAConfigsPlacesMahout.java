package edu.isistan.christian.recommenders.groups.places.magres.rA.mahout;

import org.apache.commons.configuration.ConfigurationException;

import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRAConfigs;
import edu.isistan.christian.recommenders.groups.places.magres.commons.PUMASConfigsPlacesMahout;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

public class MAGReSRAConfigsPlacesMahout extends MAGReSRAConfigs<PlaceItem>{
		
	public MAGReSRAConfigsPlacesMahout(String configsPath)
			throws ConfigurationException {
		super(configsPath, new PUMASConfigsPlacesMahout(configsPath));
	}
	
}
