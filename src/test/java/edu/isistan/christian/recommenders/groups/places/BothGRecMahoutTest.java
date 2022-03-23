package edu.isistan.christian.recommenders.groups.places;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecRecommendation;
import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecResult;
import edu.isistan.christian.recommenders.groups.places.magres.rA.MAGReSRAPlaces;
import edu.isistan.christian.recommenders.groups.places.magres.rA.mahout.MAGReSRAConfigsPlacesMahout;
import edu.isistan.christian.recommenders.groups.places.magres.rA.mahout.MAGReSRAPlacesMahout;
import edu.isistan.christian.recommenders.groups.places.magres.rA.utils.MAGReSRAPlacesCSVExporter;
import edu.isistan.christian.recommenders.groups.places.tradGRec.pA.TRADGRecPAPlaces;
import edu.isistan.christian.recommenders.groups.places.tradGRec.pA.mahout.TRADGRecPAConfigsPlacesMahout;
import edu.isistan.christian.recommenders.groups.places.tradGRec.pA.mahout.TRADGRecPAPlacesMahout;
import edu.isistan.christian.recommenders.groups.places.utils.PlacesCSVResultsExporter;
import edu.isistan.christian.recommenders.sur.exceptions.SURException;
import edu.isistan.christian.recommenders.sur.exceptions.SURInexistentUserException;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

public class BothGRecMahoutTest {
	private static final Logger logger = LogManager.getLogger(BothGRecMahoutTest.class);
	
	private static final String CONFIGS_PATH_PUMAS = "edu/isistan/christian/recommenders/groups/places/configs/MAGReSRA_MahoutConfigs.properties";
	private static final String CONFIGS_PATH_TRAD = "edu/isistan/christian/recommenders/groups/places/configs/TRADGRecPA_MahoutConfigs.properties";
	
	private static final String RESULTS_EXPORT_PATH = "C:\\Users\\Christian\\Desktop";
	
	private static final boolean FORCE_REINITIALIZE = false;
	
	private static final int ITEMS_PER_REC = 10;
	
	public static void main (String[] args){
		
		try {
			
			//PUMAS
			MAGReSRAConfigsPlacesMahout mahoutPUMASConfigs = new MAGReSRAConfigsPlacesMahout(CONFIGS_PATH_PUMAS);
			MAGReSRAPlaces pumasGRecMahout = new MAGReSRAPlacesMahout(mahoutPUMASConfigs);
			pumasGRecMahout.initialize(FORCE_REINITIALIZE);
			
			//TRADGRec
			TRADGRecPAConfigsPlacesMahout mahoutTRADGRecConfigs = new TRADGRecPAConfigsPlacesMahout(CONFIGS_PATH_TRAD);
			TRADGRecPAPlaces tradGRecMahout = new TRADGRecPAPlacesMahout(mahoutTRADGRecConfigs);
			tradGRecMahout.initialize(FORCE_REINITIALIZE);

			GRecResult<PlaceItem> resultPUMAS;
			GRecResult<PlaceItem> resultGrec;

			try {
				
				GRecGroup group = new GRecGroup();
				group.add(pumasGRecMahout.getUser("658453"));
				group.add(pumasGRecMahout.getUser("311"));
				group.add(pumasGRecMahout.getUser("429"));

//				group.add(pumasGRecMahout.getUser("458575"));
//				group.add(pumasGRecMahout.getUser("213155"));
//				group.add(pumasGRecMahout.getUser("355743"));
				
				resultPUMAS = pumasGRecMahout.recommend(group, ITEMS_PER_REC);
				

				logger.info("-----------------------------------------------------------------------------------------");
				logger.info("PUMASGRec Recommendations for "+group+" ["+resultPUMAS.getRecommendations().size()+" of "+ITEMS_PER_REC+"]");
				logger.info("-----------------------------------------------------------------------------------------");
				for (GRecRecommendation<PlaceItem> r : resultPUMAS.getRecommendations()){
					logger.info(r.toString());	
					logger.info(" >> Stats: "+ resultPUMAS.getRecommendationStats(r));
				}
				logger.info("-----------------------------------------------------------------------------------------");
				
				MAGReSRAPlacesCSVExporter exporterPUMAS = new MAGReSRAPlacesCSVExporter();
				exporterPUMAS.export(resultPUMAS, RESULTS_EXPORT_PATH, true);
				
				
				resultGrec = tradGRecMahout.recommend(group, ITEMS_PER_REC);
				logger.info("-----------------------------------------------------------------------------------------");
				logger.info("TRADGRec Recommendations for "+group+" ["+resultGrec.getRecommendations().size()+" of "+ITEMS_PER_REC+"]");
				logger.info("-----------------------------------------------------------------------------------------");
				for (GRecRecommendation<PlaceItem> r : resultGrec.getRecommendations()){
					logger.info(r.toString());	
					logger.info(" >> Stats: "+ resultGrec.getRecommendationStats(r));
				}
				logger.info("-----------------------------------------------------------------------------------------");
				PlacesCSVResultsExporter exporterTRADGRec = new PlacesCSVResultsExporter();
				exporterTRADGRec.export(resultGrec, RESULTS_EXPORT_PATH, true);
				
			} catch (SURInexistentUserException | IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

		} catch (SURException | ConfigurationException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
}
