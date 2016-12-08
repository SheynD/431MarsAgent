package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import massim.javaagents.agents.MarsUtil;

import eis.iilang.Action;

public class RechargeStrategy implements Strategy{
    /* To do
     * Check if last action failed due to low energy... then recharge 
     * */

    @Override
    public Action execute (MarsAgent agent) {
    	/* Recharge if my energy is below this threshold */
        if (agent.getEnergy() < 4) {
            return MarsUtil.rechargeAction();
        }
        /* If the last action failed (not buy) because we didn't have enough energy (Untested)*/
        if( !agent.getAllBeliefs("lastAction").getFirst().getParameters().get(0).equals("buy") && 
        		agent.getAllBeliefs("lastActionResult").getFirst().getParameters().get(0).equals("failed_resources")){
        	System.out.println("Recharging because our last action "+
        		agent.getAllBeliefs("lastActionResult").getFirst().getParameters().get(0)
        		+" failed");
        	return MarsUtil.rechargeAction();
        }
        return null;
    }
}
