package com.cse431.marsmen.strategy;

//import java.util.ArrayList;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

public class NaiveRepairStrategy implements Strategy {
	
	public Action execute(MarsAgent agent) {
		/* Only runs for Repairers */
        if (!agent.getAllBeliefs("role").getFirst().getParameters().get(0).equals("Repairer")) {
            return null;
        }
        Util u = new Util(agent);
        
        String entity = "";
        /* Loop thru all visible entities */
		for (LogicBelief visibleEntity : agent.getAllBeliefs("visibleEntity", agent.getName())){
			if (agent.getTeam().equals(visibleEntity.getParameters().get(3)) && /* If they are on out team */
					visibleEntity.getParameters().get(4).equals("disabled") &&  /* And they are disabled */
					u.getNeighborVertexes(agent.getAllBeliefs("position").getFirst().getParameters().get(0))
					.contains(visibleEntity.getParameters().get(2))){ /* And they are a neighbor */
				entity = visibleEntity.getParameters().get(1);
				/* Shouldn't we just wait for the next round of percepts to update our beliefs? */
				agent.removeBeliefs("visibleEntity", visibleEntity.getParameters().get(0), 
						visibleEntity.getParameters().get(1), 
						visibleEntity.getParameters().get(2), 
						visibleEntity.getParameters().get(3), 
						visibleEntity.getParameters().get(4));
				agent.addBelief(new LogicBelief("visibleEntity", visibleEntity.getParameters().get(0), 
						visibleEntity.getParameters().get(1), 
						visibleEntity.getParameters().get(2), 
						visibleEntity.getParameters().get(3), "normal"));
				/* Repair this team mate */
				return MarsUtil.repairAction(entity);
			}
		}
		return null;

        
	}
	

	
}
