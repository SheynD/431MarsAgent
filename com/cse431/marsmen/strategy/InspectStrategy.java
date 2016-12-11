package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.*;
import massim.javaagents.agents.MarsUtil;




public class InspectStrategy implements Strategy{

    @Override
    public Action execute (MarsAgent agent) {
        /* Only if the agent is a inspector */
        if (!agent.getRole().equals("Inspector")) {
            return null;
        }
        /* Loop thru all visible agents, not on our team */
		Util u = new Util(agent);
        if (!agent.getAllBeliefs("visibleEntity", agent.getName()).isEmpty()) {
            for (LogicBelief b : agent.getAllBeliefs("visibleEntity", agent.getName())) {
            	/* Different team and is neighbor */
            	if (!b.getParameters().get(3).equals(agent.getTeam())
						&& u.getNeighborVertexes(agent.getLocation()).contains(b.getParameters().get(2))
						&& agent.getAllBeliefs("inspectedEntity", b.getParameters().get(1)).isEmpty()) {
					return MarsUtil.inspectAction();
				}
            }
        }

        return null;
    }
}
