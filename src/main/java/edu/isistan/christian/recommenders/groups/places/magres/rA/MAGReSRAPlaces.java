package edu.isistan.christian.recommenders.groups.places.magres.rA;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.isistan.christian.recommenders.groups.commons.datatypes.GRecGroup;
import edu.isistan.christian.recommenders.groups.commons.pumas.PUMASAgentProfile;
import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRA;
import edu.isistan.christian.recommenders.groups.magres.rA.MAGReSRAConfigs;
import edu.isistan.christian.recommenders.groups.places.magres.rA.commons.MAGReSRAPlacesUserAgent;
import edu.isistan.christian.recommenders.sur.datatypes.SURUser;
import edu.isistan.christian.recommenders.sur.places.PlacesSUR;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;
import edu.isistan.pumas.framework.protocols.commons.userAgents.UserAg;

public abstract class MAGReSRAPlaces extends MAGReSRA<PlaceItem> {
	
	public MAGReSRAPlaces (MAGReSRAConfigs<PlaceItem> configs) {
		super(configs);
	}
	
	@Override
	protected List<UserAg<PlaceItem>> createAgents(GRecGroup group) {
		List<UserAg<PlaceItem>> agents = new ArrayList<>();
		PUMASAgentProfile<PlaceItem> agentsDefaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		for (SURUser u : group){
			agents.add (new MAGReSRAPlacesUserAgent(u, agentsDefaultProfile.getInitPropStrategy(),
					agentsDefaultProfile.getConcessionCriterion(), agentsDefaultProfile.getPropAcceptanceStrategy(),
					(PlacesSUR) configs.getPUMASConfigs().getSUR(),
					agentsDefaultProfile.getUtilityFunction(), agentsDefaultProfile.getARPunishmentStrategy(),
					agentsDefaultProfile.getPPoolMaxProposalsAddedOnRefill(), agentsDefaultProfile.getPPoolMaxRefillsAllowed(),
					agentsDefaultProfile.isPPoolRefillAllowed(), agentsDefaultProfile.isPPoolAllowsRecycling(),
					agentsDefaultProfile.isOptReuseUnusedProposalsEnabled(), agentsDefaultProfile.isOptUtilityCacheEnabled()));
			
			//TODO change this... the utility function and the punishment strategy should be loaded from the configs object
		}
		return agents;
	}
	
	@Override
	protected List<UserAg<PlaceItem>> createAgents(GRecGroup group, HashMap<SURUser, PUMASAgentProfile<PlaceItem>> userAgProfiles) {
		List<UserAg<PlaceItem>> agents = new ArrayList<>();
		
		PUMASAgentProfile<PlaceItem> defaultProfile = this.configs.getPUMASConfigs().getAgentsDefaultProfile();
		
		for (SURUser u : group){
			PUMASAgentProfile<PlaceItem> agProfile = (userAgProfiles.containsKey(u))? userAgProfiles.get(u) : defaultProfile;
			
			agents.add (new MAGReSRAPlacesUserAgent(u, agProfile.getInitPropStrategy(),
					agProfile.getConcessionCriterion(), agProfile.getPropAcceptanceStrategy(),
					(PlacesSUR) configs.getPUMASConfigs().getSUR(),
					agProfile.getUtilityFunction(), agProfile.getARPunishmentStrategy(),
					agProfile.getPPoolMaxProposalsAddedOnRefill(), agProfile.getPPoolMaxRefillsAllowed(),
					agProfile.isPPoolRefillAllowed(), agProfile.isPPoolAllowsRecycling(),
					agProfile.isOptReuseUnusedProposalsEnabled(), agProfile.isOptUtilityCacheEnabled()));
		}
		return agents;
	}
	
	@Override
	protected List<UserAg<PlaceItem>> createAgents(GRecGroup group, HashMap<SURUser, PUMASAgentProfile<PlaceItem>> userAgProfiles, HashMap<SURUser, Double> assertivenessFactors, HashMap<SURUser, Double> cooperativenessFactors, HashMap<SURUser, HashMap<SURUser, Double>> relationshipsFactors) {
		return createAgents(group, userAgProfiles);
	}
	
	@Override
	public String toString() {
		return "PUMASGRecMovies [singleUserRecommender="
				+ singleUserRecommender + ", groupRatingEstimationStrategy="
				+ groupRatingEstimationStrategy + ", configs=" + configs + "]";
	}
	
}
