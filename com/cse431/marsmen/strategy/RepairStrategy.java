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
                String location = l.getParameters().get(0);
                String disabledAgent = l.getParameters().get(1);
                String repairAgent = l.getParameters().get(2);
                System.out.println("repairComing: " + location + " " + disabledAgent+" "+repairAgent);
                /* If it was me that said I am coming to repair */
                if (repairAgent.equals(agent.getName())){
                    /* And we are at the same vertex */
                    if (agent.getLocation().equals(location)){
                        agent.removeBeliefs("repairComing");
                        String entity = "";
                        /* For all visible entities */
                        for (LogicBelief l2 : agent.getAllBeliefs("visibleEntity", agent.getName())){
                            /* Double check this is the right agent (by location, team, and disabled */
                            if (l2.getParameters().get(2).equals(location) && 
                                    agent.getTeam().equals(l2.getParameters().get(3)) && 
                                    l2.getParameters().get(4).equals("disabled")){
                                entity = l2.getParameters().get(1);
                            }
                        }
                        /* Repair this agent! Also broadcast that the agent is repaired */
                        if(!entity.equals("")){
                            agent.broadcastBelief(new LogicBelief("removeRepair",location,disabledAgent,repairAgent));
                            System.out.println("I am repairing "+entity);
                            return MarsUtil.repairAction(entity);
                        }

                    }
                    /* We should keep going towards this agent to repair it */
                    goals.add(l.getParameters().get(0));
                }
            }
            /* We have an agent to repair */
            if (goals.size()>0){
                String dir = getDir(goals,agent);
                for (LogicBelief edge : agent.getAllBeliefs("edge")){
    				if ((dir.equals(edge.getParameters().get(0)) && agent.getLocation().equals(edge.getParameters().get(1))) || (dir.equals(edge.getParameters().get(1)) && agent.getLocation().equals(edge.getParameters().get(0)))){    					
    	                if (agent.getEnergy() >= Integer.parseInt(edge.getParameters().get(2))){
    	                	System.out.println("\nGoing to repair, next step:" + dir + "\n");
        	                return MarsUtil.gotoAction(dir);
    					}
    				}
    			}
                
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
            	String dir = getDir(goals,agent);
                for (LogicBelief edge : agent.getAllBeliefs("edge")){
    				if ((dir.equals(edge.getParameters().get(0)) && agent.getLocation().equals(edge.getParameters().get(1))) || (dir.equals(edge.getParameters().get(1)) && agent.getLocation().equals(edge.getParameters().get(0)))){   					
    	                if (agent.getEnergy() >= Integer.parseInt(edge.getParameters().get(2))){
    	                	System.out.println("\nMoving towards agent that needs repair at " + getDir(goals,agent) + "\n");
        	                return MarsUtil.gotoAction(dir);
    					}
    				}
    			}
                
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
