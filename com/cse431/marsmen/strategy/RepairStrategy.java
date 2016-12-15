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
        ArrayList<String> near = u.getNeighborVertexes(agent.getLocation());
        /* If I am on or next to any agent that needs a repair, repair it */
        for(LogicBelief teammate : agent.getAllBeliefs("visibleEntity","","","",agent.getTeam(),"disabled")){
            String vehicleName = teammate.getParameters().get(1);
            String vertex = teammate.getParameters().get(2);
            System.out.println("Someone needs a repair!: "+vehicleName+" at "+vertex);
            if(near.contains(vertex) && !agent.getName().equals(vehicleName)){
                System.out.println("Repairing neighbor!: "+vehicleName+" at "+vertex);
                agent.broadcastBelief(new LogicBelief("removeRepair",vertex,vehicleName,""));
                return MarsUtil.repairAction(vehicleName);
            }
        }



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
                for (LogicBelief edge : agent.getAllBeliefs("edge")){
                    /* Make sure it is a neighbor */
                    if ((dir.equals(edge.getParameters().get(0)) && agent.getLocation().equals(edge.getParameters().get(1))) || 
                            (dir.equals(edge.getParameters().get(1)) && agent.getLocation().equals(edge.getParameters().get(0)))){                                          
                        /* Make sure we have enough energy */
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
                String dir = u.getDir(goals);
                for (LogicBelief edge : agent.getAllBeliefs("edge")){
                    if ((dir.equals(edge.getParameters().get(0)) && agent.getLocation().equals(edge.getParameters().get(1))) || (dir.equals(edge.getParameters().get(1)) && agent.getLocation().equals(edge.getParameters().get(0)))){                                          
                        if (agent.getEnergy() >= Integer.parseInt(edge.getParameters().get(2))){
                            System.out.println("\nMoving towards agent that needs repair at " + u.getDir(goals) + "\n");
                            return MarsUtil.gotoAction(dir);
                        }
                    }
                }

            }
        }

        return null;

    }
}
