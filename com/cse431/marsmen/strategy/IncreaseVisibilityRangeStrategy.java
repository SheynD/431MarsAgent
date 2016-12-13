package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import massim.javaagents.agents.MarsUtil;

import eis.iilang.Action;

public class IncreaseVisibilityRangeStrategy implements Strategy{
    /* 
     * Check if last action failed due to in_range or out_of_range... then buy sensor to increase visibility range 
     * */

    @Override
    public Action execute (MarsAgent agent) {
        /* If diasbled, do nothing */
        if(agent.getHealth()==0)
            return null;
        /* If last action failed for these reasons and we have enough money, increase sensor */
        String lastActionResult = agent.getAllBeliefs("lastActionResult").getFirst().getParameters().get(0);
        int money = Integer.parseInt(agent.getAllBeliefs("money").getFirst().getParameters().get(0));
        if((lastActionResult.equals("failed_in_range") || lastActionResult.equals("failed_out_of_range")) && 
                money>10){
            System.err.println("Buying sensor... "+
                    agent.getAllBeliefs("lastAction").getFirst().getParameters().get(0)
                    +" Role: " + agent.getRole() + " money: " + agent.getAllBeliefs("money").getFirst().getParameters().get(0));
            return MarsUtil.buyAction("sensor");
        }
        return null;
    }
}
