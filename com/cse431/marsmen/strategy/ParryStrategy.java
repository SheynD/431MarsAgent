package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.*;
import massim.javaagents.agents.MarsUtil;




public class ParryStrategy implements Strategy{

    /* To Do
     * Use the distance to the opponent sabetuer. If far away, don't parry 
     */
    @Override
    public Action execute (MarsAgent agent) {
        /* Only if the agent can parry */
        if (!agent.getRole().equals("Saboteur") && !agent.getRole().equals("Repairer") && !agent.getRole().equals("Sentinel")) {
            return null;
        }
        /* Loop thru all visible agents, not on our team */
        if (!agent.getAllBeliefs("visibleEntity", agent.getName()).isEmpty()) {
            for (LogicBelief b : agent.getAllBeliefs("visibleEntity", agent.getName())) {
                String enemyName = b.getParameters().get(1);
                String team = b.getParameters().get(3);
                String disabled = b.getParameters().get(4);
                /* Different team and not disabled */
                if (!team.equals(agent.getTeam()) 
                        && disabled.equals("normal")) {
                    if (!agent.getAllBeliefs("enemySaboteur").isEmpty()){
                        for (LogicBelief en : agent.getAllBeliefs("enemySaboteur")){
                            String saboteurName = en.getParameters().get(0);
                            if (saboteurName.equals(enemyName)){
                                System.out.println("Parrying ");
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
