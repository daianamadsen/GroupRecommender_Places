package edu.isistan.christian.recommenders.groups.places.utils;

import java.util.ArrayList;
import java.util.List;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.utils.CSVResultsExporter;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

public class PlacesCSVResultsExporter extends CSVResultsExporter<PlaceItem> {

	protected List<String> getItemHeaderData(GRecResult<PlaceItem> result){
		List<String> itemHeaderData = new ArrayList<>();
		itemHeaderData.add("ID");
		itemHeaderData.add("Name");
		itemHeaderData.add("Address");
		
		return itemHeaderData;
	}
	
	protected List<String> getItemRowData(GRecRecommendation<PlaceItem> rec){
		List<String> itemRowData = new ArrayList<>();
		itemRowData.add(rec.getRecommendedItem().getID());
		itemRowData.add(rec.getRecommendedItem().getName());
		itemRowData.add(rec.getRecommendedItem().getFullAddress());
		return itemRowData;
	}
	
	protected List<String> getUserRowData(GRecRecommendation<PlaceItem> rec, GRecResult<PlaceItem> result){
		return super.getUserRowData(rec, result);
	}
	
}
