package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

public class SkipStrategy implements Strategy{

    public Action execute (MarsAgent agent) {
        /* Default to recharge */
        System.out.println("Defaulting to recharge/skip");
        return MarsUtil.rechargeAction();
    }
}
