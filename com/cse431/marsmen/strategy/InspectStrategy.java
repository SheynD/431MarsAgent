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
                String enemyName = b.getParameters().get(1);
                String location = b.getParameters().get(2);
                String team = b.getParameters().get(3);
                String disabled = b.getParameters().get(4);
                /* Different team, is neighbor, and is not inspected */
                if (!team.equals(agent.getTeam())
                        && u.getNeighborVertexes(agent.getLocation()).contains(location)
                        && agent.getAllBeliefs("inspectedEntity", enemyName).isEmpty()) {
                    System.out.println("I am inspecting!");
                    System.out.println(enemyName+" "+location+" "+team+" "+disabled);
                    return MarsUtil.inspectAction();
                }
            }
        }

        return null;
    }
}
