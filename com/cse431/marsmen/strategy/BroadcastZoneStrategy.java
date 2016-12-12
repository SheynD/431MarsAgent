package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import massim.javaagents.agents.MarsUtil;
import apltk.interpreter.data.LogicBelief;

import eis.iilang.Action;

public class BroadcastZoneStrategy implements Strategy{
    @Override
    public Action execute (MarsAgent agent) {
    	/* If not in a zone, don't broadcast anything */
        String zoneScore = agent.getAllBeliefs("zoneScore").getFirst().getParameters().get(0);
        if(zoneScore.equals("0"))
            return null;
        System.out.println("Zone Score: "+zoneScore);
        agent.broadcastBelief(new LogicBelief("inZone",agent.getLocation(),zoneScore));
        return null;
    }
}
