package com.cse431.marsmen.strategy;

import java.util.ArrayList;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

public class NaiveRepairStrategy implements Strategy {
	
	public Action execute(MarsAgent agent) {
        if (!agent.getAllBeliefs("role").getFirst().getParameters().get(0).equals("Repairer")) {
            return null;
        }
        Util u = new Util(agent);
        
        String entity = "";
		for (LogicBelief l2 : agent.getAllBeliefs("visibleEntity", agent.getName())){
			if (agent.getTeam().equals(l2.getParameters().get(3)) && l2.getParameters().get(4).equals("disabled") && u.getNeighborVertexes(agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0)).contains(l2.getParameters().get(2))){
				entity = l2.getParameters().get(1);
				agent.removeBeliefs("visibleEntity", l2.getParameters().get(0), l2.getParameters().get(1), l2.getParameters().get(2), l2.getParameters().get(3), l2.getParameters().get(4));
				agent.addBelief(new LogicBelief("visibleEntity", l2.getParameters().get(0), l2.getParameters().get(1), l2.getParameters().get(2), l2.getParameters().get(3), "normal"));
				return MarsUtil.repairAction(entity);
			}
		}
		return null;

        
	}
	

	
}
