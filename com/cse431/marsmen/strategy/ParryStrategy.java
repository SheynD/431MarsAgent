package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.*;
import massim.javaagents.agents.MarsUtil;




public class ParryStrategy implements Strategy{

    @Override
    public Action execute (MarsAgent agent) {
        /* Only if the agent can parry */
        if (!agent.getRole().equals("Saboteur") || !agent.getRole().equals("Repairer") || !agent.getRole().equals("Sentinel")) {
            return null;
        }
        /* Loop thru all visible agents, not on our team */
        if (!agent.getAllBeliefs("visibleEntity", agent.getName()).isEmpty()) {
            for (LogicBelief b : agent.getAllBeliefs("visibleEntity", agent.getName())) {
            	/* Different team and not disabled */
            	if (!b.getParameters().get(3).equals(agent.getTeam()) 
                        && b.getParameters().get(4).equals("normal")) {
            		if (!agent.getAllBeliefs("enemySaboteur").isEmpty()){
            			for (LogicBelief en : agent.getAllBeliefs("enemySaboteur")){
            				if (en.getParameters().get(0).equals(b.getParameters().get(1))){
            					return MarsUtil.parryAction();
            				}
            			}
            		}
                    
                }
            }
        }

        return null;
    }
}