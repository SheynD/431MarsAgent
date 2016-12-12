package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import massim.javaagents.agents.MarsUtil;

import eis.iilang.Action;

public class IncreaseVisibilityRangeStrategy implements Strategy{
    /* 
     * Check if last action failed due to in_range... then buy sensor 
     * */

    @Override
    public Action execute (MarsAgent agent) {
        if(agent.getAllBeliefs("lastActionResult").getFirst().getParameters().get(0).equals("failed_in_range") && Integer.parseInt(agent.getAllBeliefs("money").getFirst().getParameters().get(0))>10){
        	System.err.println("Agent name "+
        		agent.getAllBeliefs("lastAction").getFirst().getParameters().get(0)
        		+" Role: " + agent.getRole() + " money: " + agent.getAllBeliefs("money").getFirst().getParameters().get(0));
        	return MarsUtil.buyAction("sensor");
        }
        return null;
    }
}