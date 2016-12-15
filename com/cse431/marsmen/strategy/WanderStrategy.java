package com.cse431.marsmen.strategy;

import java.util.ArrayList;
import java.util.Collections;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

public class WanderStrategy implements Strategy {

    /*
     * Wander, but with a favoring for nodes of higher value
     */
    public Action execute(MarsAgent agent) {
        System.out.println("Start wander");
        /* If I am low on energy can't explore */
        if (agent.getEnergy() == 0) {
            System.out.println("Not wandering... " + agent.getEnergy());
            return null;
        }

        Util u = new Util(agent);
        /* All neighbor vertices to my vertex */
        ArrayList<String> verticies = u.getNeighborVertexes(agent.getLocation());
        Collections.shuffle(verticies);

        /* If I am on the same node as a teammate, choose random node */
        boolean same_node = false;
        for (LogicBelief teammate : agent.getAllBeliefs("visibleEntity", "", "", "", agent.getTeam())) {
            String name = teammate.getParameters().get(1);
            String ver = teammate.getParameters().get(2);
            /* Skip self */
            if(name.equals(agent.getName()))
                continue;
            if (ver.equals(agent.getLocation())){
                System.out.println("On same node as friend!");
                same_node = true;
            }
        }
        if(same_node){
            for (String v : verticies) {
                if (v.equals(agent.getLocation()))
                    continue;
                /* Make sure we have enough energy */
                for (LogicBelief edge : agent.getAllBeliefs("edge")) {
                    String v1 = edge.getParameters().get(0);
                    String v2 = edge.getParameters().get(1);
                    String w = edge.getParameters().get(2);
                    if ((v.equals(v1) && agent.getLocation().equals(v2)) || 
                            (v.equals(v2) && agent.getLocation().equals(v1))) {
                        if (agent.getEnergy() >= Integer.parseInt(w)){
                            System.out.println("Spreading to wander");
                            return MarsUtil.gotoAction(v);
                        }
                    }
                }
            }
        }

            

        /* If I am on a really good vertex, do nothing */
        String here = agent.getAllBeliefs("vertex", agent.getLocation()).getFirst().getParameters().get(1);
        int hereVal = Integer.parseInt(here);
        if(hereVal>5){
            System.out.println("Not Wandering cause good node "+hereVal);
            return null;
        }

        /* If any of these have higher value and are not dominited by us... */
        int maxVal = 0;
        String bestV = agent.getLocation();
        String randomV = agent.getLocation();
        /* For every neighbor vertex */
        for (String v : verticies) {
            if (v.equals(agent.getLocation()))
                continue;
            String thisVstr = agent.getAllBeliefs("vertex", v).getFirst().getParameters().get(1);
            int thisV = Integer.parseInt(thisVstr);/* Value of vertex */
            /* Make sure we have enough energy */
            for (LogicBelief edge : agent.getAllBeliefs("edge")) {
                String v1 = edge.getParameters().get(0);
                String v2 = edge.getParameters().get(1);
                String w = edge.getParameters().get(2);
                if ((v.equals(v1) && agent.getLocation().equals(v2)) || 
                        (v.equals(v2) && agent.getLocation().equals(v1))) {
                    /* Check if node is owned by us */
                    if(!agent.getAllBeliefs("vertexTeam",v,agent.getTeam()).isEmpty()){
                        continue;
                    }
                    /* Update random node to go to */
                    if (agent.getEnergy() >= Integer.parseInt(w)){
                        System.out.println("Adding to list and random");
                        randomV = v;
                        /* Update best node to go to */
                        if (thisV > maxVal){
                            bestV = v;
                            maxVal = thisV;
                        }
                    }
                }
            }
        }
        if (!bestV.equals(agent.getLocation())) {
            System.out.println("Wandering to better node... " + bestV);
            return MarsUtil.gotoAction(bestV);
        } else if (!randomV.equals(agent.getLocation())) {
            System.out.println("Wandering to random node... " + randomV);
            return MarsUtil.gotoAction(randomV);
        }

        return null;
    }
}
