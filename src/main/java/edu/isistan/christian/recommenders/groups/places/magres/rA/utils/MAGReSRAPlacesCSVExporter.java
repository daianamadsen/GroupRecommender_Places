package edu.isistan.christian.recommenders.groups.places.magres.rA.utils;

import java.util.List;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRARecStats;
import edu.isistan.christian.recommenders.groups.places.utils.PlacesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.datatypes.SURUser;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

public class MAGReSRAPlacesCSVExporter extends PlacesCSVResultsExporter{

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

		userHeaderData.add(EMPTY_COLUMN);
		
		//Concessions Made
		for (SURUser member : result.getGroup()){
			userHeaderData.add("USER [ID= "+member.getID()+"] #ConcessionsMade");
		}

		return userHeaderData;
	}

	protected List<String> getUserRowData(GRecRecommendation<PlaceItem> rec, GRecResult<PlaceItem> result){
		List<String> userRowData = super.getUserRowData(rec, result);

		userRowData.add(EMPTY_COLUMN);

		MAGReSRARecStats stats = (MAGReSRARecStats) result.getRecommendationStats(rec);

		//InfoLeak - Proposals Columns
		for (SURUser member : result.getGroup()){
			userRowData.add(getStringFrom(stats.getProposalsRevealedPercentage(member.getID())));
		}

		userRowData.add(EMPTY_COLUMN);

		//InfoLeak - UtilityFunction Columns
		for (SURUser member : result.getGroup()){
			userRowData.add(getStringFrom(stats.getUtilitiesRevealedPercentage(member.getID())));
		}

		userRowData.add(EMPTY_COLUMN);
		//Movie Already Watched Columns
		for (SURUser member : result.getGroup()){
			userRowData.add((stats.hadRatedItem(member.getID())== true)? "1":"0");
		}
		
		userRowData.add(EMPTY_COLUMN);
		//Concessions made Columns
		for (SURUser member : result.getGroup()){
			userRowData.add(getStringFrom(stats.getConcessionsMade(member.getID())));
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
			
			summaryRowData.add(EMPTY_COLUMN);
			//Concessions made Columns
			for (int i=0; i<result.getGroup().size(); i++){
				summaryRowData.add(this.getStringFrom(0.0));
			}

			return summaryRowData;
		}

		summaryRowData.add(EMPTY_COLUMN);
		
		//Compute average "infoLeak_proposals" for every member of the group
		for (SURUser member : result.getGroup()){
			double avgInfoLeakProposals = 0.0;
			for (GRecRecommendation<PlaceItem> rec : result.getRecommendations()){
				MAGReSRARecStats stats = (MAGReSRARecStats) result.getRecommendationStats(rec);
				avgInfoLeakProposals+= stats.getProposalsRevealedPercentage(member.getID());
			}
			avgInfoLeakProposals /= recsCount;
			summaryRowData.add(this.getStringFrom(avgInfoLeakProposals));
		}

		summaryRowData.add(EMPTY_COLUMN);

		//Compute average "infoLeak_UtilityFuction" for every member of the group
		for (SURUser member : result.getGroup()){
			double avgInfoLeakUtilityFuction = 0.0;
			for (GRecRecommendation<PlaceItem> rec : result.getRecommendations()){
				MAGReSRARecStats stats = (MAGReSRARecStats) result.getRecommendationStats(rec);
				avgInfoLeakUtilityFuction+= stats.getUtilitiesRevealedPercentage(member.getID());
			}
			avgInfoLeakUtilityFuction /= recsCount;
			summaryRowData.add(this.getStringFrom(avgInfoLeakUtilityFuction));
		}

		summaryRowData.add(EMPTY_COLUMN);
		
		//Compute the proportion of "Movie Already Watched" for every member of the group
		for (SURUser member : result.getGroup()){
			double avgAlreadyWatched = 0.0;
			for (GRecRecommendation<PlaceItem> rec : result.getRecommendations()){
				MAGReSRARecStats stats = (MAGReSRARecStats) result.getRecommendationStats(rec);
				if (stats.getItemAlreadyRatedBy().contains(member.getID()))
					avgAlreadyWatched++;
			}
			avgAlreadyWatched /= recsCount;
			summaryRowData.add(this.getStringFrom(avgAlreadyWatched));
		}
		
		summaryRowData.add(EMPTY_COLUMN);
		
		//Compute the total amount of "ConcessionsMade" for every member of the group
		for (SURUser member : result.getGroup()){
			double avgConcessionsMade = 0.0;
			for (GRecRecommendation<PlaceItem> rec : result.getRecommendations()){
				MAGReSRARecStats stats = (MAGReSRARecStats) result.getRecommendationStats(rec);
				avgConcessionsMade+= stats.getConcessionsMade(member.getID());
			}
			summaryRowData.add(this.getStringFrom(avgConcessionsMade));
		}

		return summaryRowData;
	}


}
