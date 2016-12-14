package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.*;
import massim.javaagents.agents.MarsUtil;


/* To do
 * - Attack agents based on distance to other agent and the Sabeturs range (default to 1)
 * - Maybe add strategy to buy("sensor") - for range, or buy("dsabotageDevice") - strength of attack
 */

public class SaboteurStrategy implements Strategy{

    @Override
    public Action execute (MarsAgent agent) {
        /* Only if the agent is a sabetuer */
        if (!agent.getRole().equals("Saboteur")) {
            return null;
        }
        /* If agent does not have enough health, can't attack */
        if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) < 2) {
            return null;
        }
        /* Loop thru all visible agents, not on our team */
        if (!agent.getAllBeliefs("visibleEntity", agent.getName()).isEmpty()) {
            for (LogicBelief b : agent.getAllBeliefs("visibleEntity", agent.getName())) {
            	/* Different team and not already disabled */
            	if (!b.getParameters().get(3).equals(agent.getTeam()) 
                        && b.getParameters().get(4).equals("normal")) {
                    String blocation = b.getParameters().get(2) ;
                    String mylocation = agent.getLocation();
                    /* On same vertex*/
                    if(mylocation.equals(blocation)){
                        System.out.println("Attacking opponent on same node as me - "+blocation+","+mylocation);
                        return MarsUtil.attackAction(b.getParameters().get(1));
                    }
                    /* neighbor vertex */
                    else {
                    	Util u = new Util(agent);
                    	for (String vert : u.getNeighborVertexes(mylocation)){
                    		if (blocation.equals(vert)){
                    			System.out.println("Attacking opponent on neighboring node - "+blocation+","+mylocation);
                                return MarsUtil.attackAction(b.getParameters().get(1));
                    		}
                    	}
                    }
                }
            }
        }

        return null;
    }
}
