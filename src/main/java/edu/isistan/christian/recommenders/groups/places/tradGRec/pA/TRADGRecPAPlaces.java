package edu.isistan.christian.recommenders.groups.places.tradGRec.pA;

import java.util.List;

import edu.isistan.christian.recommenders.groups.tradGRec.pA.TRADGRecPA;
import edu.isistan.christian.recommenders.groups.tradGRec.pA.TRADGRecPAConfigs;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

public abstract class TRADGRecPAPlaces extends TRADGRecPA<PlaceItem> {

	public TRADGRecPAPlaces(TRADGRecPAConfigs<PlaceItem> configs) {
		super(configs);
	}

	@Override
	public String toString() {		
		return "TRADGRec [singleUserRecommender="
		+ singleUserRecommender.toString()+ ", ratingsAggregationStrategy="
		+ aggregationStrategy.getClass().getSimpleName()+"]";
	}

	public PlaceItem getPlaceByName(String name) throws SURException{
		List<PlaceItem> items = singleUserRecommender.getAllItems();
		boolean stop = false;
		PlaceItem m = null;
		for (int i=0; i<items.size() && !stop;i++){
			stop = items.get(i).getName().equals(name);
			if (stop)
				m = items.get(i);
		}
		return m;
	}

}
