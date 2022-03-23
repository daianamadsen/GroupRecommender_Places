package edu.isistan.christian.recommenders.groups.places.magres.rA.commons;

import edu.isistan.christian.recommenders.sur.datatypes.SURUser;
import edu.isistan.christian.recommenders.sur.places.PlacesSUR;
import edu.isistan.christian.recommenders.sur.places.datatypes.PlaceItem;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import edu.isistan.pumas.framework.protocols.commons.exceptions.CacheRefillingException;
import edu.isistan.pumas.framework.protocols.commons.userAgents.UserAgRecommenderBased;
import edu.isistan.pumas.framework.protocols.commons.userAgents.arPunishmentStrategy.AlreadyRatedPunishmentStrategy;
import edu.isistan.pumas.framework.protocols.commons.userAgents.concessionCriterion.ConcessionCriterion;
import edu.isistan.pumas.framework.protocols.commons.userAgents.initialProposalStrategy.InitialProposalStrategy;
import edu.isistan.pumas.framework.protocols.commons.userAgents.proposalAcceptanceStrategy.ProposalAcceptanceStrategy;
import edu.isistan.pumas.framework.protocols.commons.userAgents.utilityFunction.UtilityFunction;

public class MAGReSRAPlacesUserAgent extends UserAgRecommenderBased<PlaceItem> {

//	private static final Logger logger = LogManager.getLogger(MoviesUserAg.class);

	public boolean lastCacheRefillSuccess = true;
		
	public MAGReSRAPlacesUserAgent(SURUser myUser,	InitialProposalStrategy<PlaceItem> initialProposalStrategy,
			ConcessionCriterion<PlaceItem> concessionCriterion, ProposalAcceptanceStrategy<PlaceItem> proposalAcceptStrategy, 
			PlacesSUR itemRecSys,
			UtilityFunction<PlaceItem> utilityFunction, AlreadyRatedPunishmentStrategy<PlaceItem> arPunishmentStrategy,
			boolean proposalsPoolAllowsRecycling, boolean optReuseNotUsedProposalsEnabled, boolean optUtilityCacheEnabled) {
		super(myUser, initialProposalStrategy, concessionCriterion, proposalAcceptStrategy, itemRecSys, 
				utilityFunction, arPunishmentStrategy, proposalsPoolAllowsRecycling, optReuseNotUsedProposalsEnabled, 
				optUtilityCacheEnabled);
	}



	public MAGReSRAPlacesUserAgent(SURUser myUser, InitialProposalStrategy<PlaceItem> initialProposalStrategy,
			ConcessionCriterion<PlaceItem> concessionCriterion, ProposalAcceptanceStrategy<PlaceItem> proposalAcceptStrategy, 
			PlacesSUR itemRecSys,
			UtilityFunction<PlaceItem> utilityFunction, AlreadyRatedPunishmentStrategy<PlaceItem> arPunishmentStrategy,
			int maxProposalsAddedOnRefill, int maxProposalsPoolRefillsAllowed,	boolean proposalsPoolIsRefillAllowed,
			boolean proposalsPoolAllowsRecycling, boolean optReuseNotUsedProposalsEnabled, boolean optUtilityCacheEnabled) {
		super(myUser, initialProposalStrategy, concessionCriterion, proposalAcceptStrategy, itemRecSys,
				utilityFunction, arPunishmentStrategy, maxProposalsAddedOnRefill, maxProposalsPoolRefillsAllowed, proposalsPoolIsRefillAllowed,
				proposalsPoolAllowsRecycling, optReuseNotUsedProposalsEnabled, optUtilityCacheEnabled);
	}



	@Override
	protected void refillProposalsPool() throws CacheRefillingException{
		if (lastCacheRefillSuccess){
			//Attempt to refill
			try{
				super.refillProposalsPool();
			} catch (CacheRefillingException e){
				//if refill failed => change the boolean and then throw the exception
				lastCacheRefillSuccess = false;
				throw e;
			}
		}
		else
			throw new CacheRefillingException();
	}

	
	public void reset(boolean fullReset){
		lastCacheRefillSuccess = true;
		super.reset(fullReset);
	}
	
	
}
