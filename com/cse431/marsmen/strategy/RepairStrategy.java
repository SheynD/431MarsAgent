package com.cse431.marsmen.strategy;

import java.util.ArrayList;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

public class RepairStrategy implements Strategy {

    public Action execute(MarsAgent agent) {
        /* Only run if we are a repairer */
        if (!agent.getRole().equals("Repairer")) 
            return null;
        /* Only run if someone needs a repair and no repair is on its way */
        if (agent.getAllBeliefs("needRepair").isEmpty() && agent.getAllBeliefs("repairComing").isEmpty())
            return null;
        /* Make sure I have enough energy */
        System.out.println("Energy: "+agent.getEnergy()+" Health: "+agent.getHealth());
        if ((agent.getHealth()<=0 && agent.getEnergy()<3) ||  agent.getEnergy() < 2){
            System.out.println("Not repairing not enough energy");
            return null;
        }

        ArrayList<String> goals = new ArrayList<String>();
        Util u = new Util(agent);
        /* For every repair coming message */
        if (!agent.getAllBeliefs("repairComing").isEmpty()){
            /* For every repair that is incoming */
            for (LogicBelief l : agent.getAllBeliefs("repairComing")){
                String location = l.getParameters().get(0);
                String disabledAgent = l.getParameters().get(1);
                String repairAgent = l.getParameters().get(2);
                System.out.println("repairComing: " + location + " " + disabledAgent+" "+repairAgent);
                /* If it was me that said I am coming to repair, and I am not repairing myself */
                if (repairAgent.equals(agent.getName()) && !disabledAgent.equals(agent.getName())){
                    /* And we are at the same vertex */
                    if (agent.getLocation().equals(location)){
                        agent.removeBeliefs("repairComing");
                        /* Repair this agent! Also broadcast that the agent is repaired */
                        if(!disabledAgent.equals("")){
                            agent.broadcastBelief(new LogicBelief("removeRepair",location,disabledAgent,repairAgent));
                            System.out.println("I am repairing "+disabledAgent);
                            return MarsUtil.repairAction(disabledAgent);
                        }

                    }
                    /* We should keep going towards this agent to repair it */
                    goals.add(location);
                }
            }
            /* We have an agent to repair */
            if (goals.size()>0){
                String dir = u.getDir(goals);
                System.out.println("\nGoing to repair, next step:" + dir + "\n");
                return MarsUtil.gotoAction(dir);
            }
        }
        /* See if there are other agents that need a repair */
        if (!agent.getAllBeliefs("needRepair").isEmpty()){
            /* This will claim the first agent that needs a repair, and say it is coming */
            for (LogicBelief l : agent.getAllBeliefs("needRepair")){
                String location = l.getParameters().get(0);
                String agentName = l.getParameters().get(1);
                /* Can't repair our selves! */
                if(!agentName.equals(agent.getName())){
                    goals.add(l.getParameters().get(0));
                    LogicBelief lb = new LogicBelief("repairComing", location, agentName, agent.getName());
                    agent.addBelief(lb);
                    agent.broadcastBelief(lb);
                    break;
                }
            }
            /* Should we just wait until the next percept to remove this? */
            agent.removeBeliefs("needRepair");
            /* Start going towards this agent */
            if (goals.size()>0){
                System.out.println("\nMoving towards agent that needs repair at " + u.getDir(goals) + "\n");
                return MarsUtil.gotoAction(u.getDir(goals));
            }
        }

        return null;

    }


}
