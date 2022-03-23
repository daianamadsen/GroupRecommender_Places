package edu.isistan.christian.recommenders.groups.places.tradGRec.rA;

import java.util.List;

import edu.isistan.christian.recommenders.groups.tradGRec.rA.TRADGRecRA;
import edu.isistan.christian.recommenders.groups.tradGRec.rA.TRADGRecRAConfigs;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

public abstract class TRADGRecRAPlaces extends TRADGRecRA<PlaceItem> {

	public TRADGRecRAPlaces(TRADGRecRAConfigs<PlaceItem> configs) {
		super(configs);
	}

	@Override
	public String toString() {
		return "TRADGRecRAPlaces [singleUserRecommender=" + singleUserRecommender + ", recAggregationStrategy="
				+ recAggregationStrategy + ", groupRatingEstimationStrategy=" + groupRatingEstimationStrategy + "]";
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
