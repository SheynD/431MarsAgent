package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.*;
import massim.javaagents.agents.MarsUtil;


/* To do
 * - Attack agents based on distance to other agent and the Sabeturs range (default to 1)
 * - Maybe add strategy to buy("sensor") - for range, or buy("dsabotageDevice") - strength of attack
 */

public class SaboteurStrategy implements Strategy{

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
				if (!b.getParameters().get(3).equals(agent.getTeam()) /* Different team */
						&& b.getParameters().get(4).equals("normal")) { /* Not already disabled */
                    String blocation = b.getParameters().get(2) ;
                    String mylocation = agent.getLocation();
                    /* On the same vertex (fix later use range)*/
                    if(mylocation.equals(blocation)){
                        return MarsUtil.attackAction(b.getParameters().get(1));
                    }
				}
			}
		}

		return null;
	}
}
