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
        if (agent.getEnergy() < 4) {
            return MarsUtil.rechargeAction();
        }
        return null;
    }
}
