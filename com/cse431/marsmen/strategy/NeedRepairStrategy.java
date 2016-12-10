package com.cse431.marsmen.strategy;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

import com.cse431.marsmen.MarsAgent;
//import com.cse431.marsmen.Util;

public class NeedRepairStrategy implements Strategy{

    public Action execute(MarsAgent agent) {
        /* We must know our health and location */
        if (agent.getAllBeliefs("health").getFirst().getParameters().get(0).isEmpty() ||
                agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0).isEmpty()){
            return null;
        }

        /* If I am disabled, lets broadcast that I need a repair */
        if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) == 0){
            agent.broadcastBelief(new LogicBelief("needRepair", agent.getLocation(), agent.getName()));
            return null;
        }
        return null;

    }

}
