package com.cse431.marsmen.strategy;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

import com.cse431.marsmen.MarsAgent;
//import com.cse431.marsmen.Util;

public class NeedRepairStrategy implements Strategy{

    public Action execute(MarsAgent agent) {
        /* If I am disabled, lets broadcast that I need a repair */
        if (agent.getHealth() == 0){
            System.out.println("Broadcasting needRepair: "+agent.getLocation()+","+agent.getName());
            agent.broadcastBelief(new LogicBelief("needRepair", agent.getLocation(), agent.getName()));
            return null;
        }
        return null;

    }

}
