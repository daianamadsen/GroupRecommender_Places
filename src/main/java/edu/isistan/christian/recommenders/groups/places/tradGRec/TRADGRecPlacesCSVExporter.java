package edu.isistan.christian.recommenders.groups.places.tradGRec;

import java.util.List;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendationStats;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.places.utils.PlacesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.datatypes.SURUser;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

public class TRADGRecPlacesCSVExporter extends PlacesCSVResultsExporter{

	protected List<String> getUserHeaderData(GRecResult<PlaceItem> result){
		List<String> userHeaderData = super.getUserHeaderData(result);
		//add header for the additional columns
		userHeaderData.add(EMPTY_COLUMN); //for the emtpy column

		//InfoLeak - Proposals Columns
		for (SURUser member : result.getGroup()){
			userHeaderData.add("USER [ID= "+member.getID()+"] %InfoLeak-Proposals");
		}

		userHeaderData.add(EMPTY_COLUMN); //for the emtpy column

		//InfoLeak - Proposals Columns
		for (SURUser member : result.getGroup()){
			userHeaderData.add("USER [ID= "+member.getID()+"] %InfoLeak-UtilityFunction");
		}

		userHeaderData.add(EMPTY_COLUMN); //for the emtpy column

		//Movie Already Watched Columns
		for (SURUser member : result.getGroup()){
			userHeaderData.add("USER [ID= "+member.getID()+"] RATED the place?");
		}

		return userHeaderData;
	}

	protected List<String> getUserRowData(GRecRecommendation<PlaceItem> rec, GRecResult<PlaceItem> result){
		List<String> userRowData = super.getUserRowData(rec, result);

		userRowData.add(EMPTY_COLUMN);

		GRecRecommendationStats stats = result.getRecommendationStats(rec);

		//InfoLeak - Proposals Columns
		for (int i=0; i<result.getGroup().size(); i++){
			userRowData.add(getStringFrom(1.0d)); //1.0d = 1.0 (forces a double)
		}

		userRowData.add(EMPTY_COLUMN);

		//InfoLeak - UtilityFunction Columns
		for (int i=0; i<result.getGroup().size(); i++){
			userRowData.add(getStringFrom(1.0d));
		}

		userRowData.add(EMPTY_COLUMN);
		
		//Movie Already Watched Columns
		for (SURUser member : result.getGroup()){
			userRowData.add((stats.hadRatedItem(member.getID())== true)? "1":"0");
		}

		return userRowData;
	}

	protected List<String> getSummaryRowData (GRecResult<PlaceItem> result){
		List<String> summaryRowData = super.getSummaryRowData(result);

		//Add rows for the additional columns added by this class
		int recsCount = result.getRecommendations().size(); 
		if (recsCount == 0){
			summaryRowData.add(EMPTY_COLUMN);
			
			//InfoLeak - Proposals Columns
			for (int i=0; i<result.getGroup().size(); i++){ //avg infoLeak for every member is 0
				summaryRowData.add(this.getStringFrom(0.0));
			}

			summaryRowData.add(EMPTY_COLUMN);

			//InfoLeak - UtilityFunction Columns
			for (int i=0; i<result.getGroup().size(); i++){ //avg infoLeak for every member is 0
				summaryRowData.add(this.getStringFrom(0.0));
			}
			
			summaryRowData.add(EMPTY_COLUMN);

			//Proportion of "Movie Already Watched" for every member of the group
			for (int i=0; i<result.getGroup().size(); i++){ //the avg of movies watched is 0
				summaryRowData.add(this.getStringFrom(0.0));
			}

			return summaryRowData;
		}

		summaryRowData.add(EMPTY_COLUMN);
		
		//Compute average "infoLeak_proposals" for every member of the group
		for (int i=0; i<result.getGroup().size(); i++){
			summaryRowData.add(getStringFrom(1.0d)); //because for each member we forced a 1.0d for this column in the "getUserRowData" method
		}

		summaryRowData.add(EMPTY_COLUMN);

		//Compute average "infoLeak_UtilityFuction" for every member of the group
		for (int i=0; i<result.getGroup().size(); i++){
			summaryRowData.add(getStringFrom(1.0d)); //because for each member we forced a 1.0d for this column in the "getUserRowData" method
		}
		
		summaryRowData.add(EMPTY_COLUMN);

		//Compute the proportion of "Movie Already Watched" for every member of the group
		for (SURUser member : result.getGroup()){
			double avgAlreadyWatched = 0.0;
			for (GRecRecommendation<PlaceItem> rec : result.getRecommendations()){
				GRecRecommendationStats stats = result.getRecommendationStats(rec);
				if (stats.getItemAlreadyRatedBy().contains(member.getID()))
					avgAlreadyWatched++;
			}
			avgAlreadyWatched /= recsCount;
			summaryRowData.add(this.getStringFrom(avgAlreadyWatched));
		}

		return summaryRowData;
	}


}
