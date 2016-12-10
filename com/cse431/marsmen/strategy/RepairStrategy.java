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
        if (!agent.getRole().equals("Repairer")) {
            return null;
        }
        /* Only run if someone needs a repair and no repair is on its way */
        if (agent.getAllBeliefs("needRepair").isEmpty() && agent.getAllBeliefs("repairComing").isEmpty()){
            return null;
        }

        ArrayList<String> goals = new ArrayList<String>();
        /* For every repair coming message */
        if (!agent.getAllBeliefs("repairComing").isEmpty()){
            /* For every repair that is incoming */
            for (LogicBelief l : agent.getAllBeliefs("repairComing")){
                System.out.println("repairComing: " + l.getParameters().get(0) + " " + l.getParameters().get(1));
                /* If it was me that said I am coming to repair */
                if (l.getParameters().get(1).equals(agent.getName())){
                    /* And we are at the same vertex */
                    if (agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0).equals(l.getParameters().get(0))){
                        agent.removeBeliefs("repairComing");
                        String entity = "";
                        /* For all visible entities */
                        for (LogicBelief l2 : agent.getAllBeliefs("visibleEntity", agent.getName())){
                            /* Double check this is the right agent (buy name, team, and disabled */
                            if (l2.getParameters().get(2).equals(l.getParameters().get(0)) && 
                                    agent.getTeam().equals(l2.getParameters().get(3)) && 
                                    l2.getParameters().get(4).equals("disabled")){
                                entity = l2.getParameters().get(1);
                            }
                        }
                        /* Repair this agent! */
                        if(!entity.equals("")){
                            return MarsUtil.repairAction(entity);
                        }

                    }
                    /* We should keep going towards this agent to repair it */
                    goals.add(l.getParameters().get(0));
                }
            }
            /* We have an agent to repair */
            if (goals.size()>0){
                System.err.println("\n\n\nGoing to repair, next step:" + getDir(goals,agent) + "\n\n\n");
                return MarsUtil.gotoAction(getDir(goals,agent));
            }
        }
        /* See if there are other agents that need a repair */
        if (!agent.getAllBeliefs("needRepair").isEmpty()){
            /* This will claim the first agent that needs a repair, and say it is coming */
            for (LogicBelief l : agent.getAllBeliefs("needRepair")){
                goals.add(l.getParameters().get(0));
                agent.addBelief(new LogicBelief("repairComing", l.getParameters().get(0), agent.getName()));
                agent.broadcastBelief(new LogicBelief("repairComing", l.getParameters().get(0), agent.getName()));
                break;
            }
            /* Should we just wait until the next percept to remove this? */
            agent.removeBeliefs("needRepair");
            /* Start going towards this agent */
            if (goals.size()>0){
                System.out.println("\n\n\n" + getDir(goals,agent) + "\n\n\n");
                return MarsUtil.gotoAction(getDir(goals,agent));
            }
        }

        return null;

    }

    private String getDir(ArrayList <String> goals, MarsAgent agent){
        Util u = new Util(agent);
        String position = agent.getLocation();
        /* Use Djikstra's to get the path */
        ArrayList<String> path = u.getDirection(position, goals);
        System.out.println("Path from "+position+" to "+goals.get(0)+":");
        for (String p : path){
            System.out.print(p+",");
        }System.out.println();
        /* If the path is not empty, and the first node is my current location */
        if (path.size() > 0 && path.get(0).equals(position)) {
            path.remove(0);
        }
        /* If path is not empty, a valid path was found, and the first step is a neighbor */
        if (path.size() > 0 && !path.get(0).equals("novalidpath") && u.getNeighborVertexes(position).contains(path.get(0))) {
            System.out.println("Valid direction found... moving from "+position+" to "+path.get(0));
            return path.get(0);
        }
        /* Otherwise, go to my first neighbor vertex */
        else {
            return u.getNeighborVertexes(position).get(0);
        }
    }

}
