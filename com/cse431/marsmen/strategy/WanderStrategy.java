package com.cse431.marsmen.strategy;

import java.util.ArrayList;
import java.util.Collections;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

public class WanderStrategy implements Strategy{

     /* Wander, but with a favoring for nodes of higher value
     */
    public Action execute (MarsAgent agent) {
        /* If I am low on energy can't explore */
        if (agent.getEnergy() ==0 ) {
            System.out.println("Not wandering... "+agent.getEnergy());
            return null;
        }

        /* All neighbor vertices to my vertex */
        Util u = new Util(agent);
        ArrayList<String> verticies = u.getNeighborVertexes(agent.getLocation());

        /* If any of these have higher value and are not dominited by us... */
        int maxVal = 0;
        String bestV = agent.getLocation();
        for (String v : verticies){
            if(v.equals(agent.getLocation()))
                continue;
            String thisVstr = agent.getAllBeliefs("vertex",v).getFirst().getParameters().get(1);
            int thisV = Integer.parseInt(thisVstr);
            /* Make sure we have enough energy */
            for (LogicBelief edge : agent.getAllBeliefs("edge")){
                if ((v.equals(edge.getParameters().get(0)) && agent.getLocation().equals(edge.getParameters().get(1))) || (v.equals(edge.getParameters().get(1)) && agent.getLocation().equals(edge.getParameters().get(0)))){					
                    if (agent.getEnergy() < Integer.parseInt(edge.getParameters().get(2))){
                        continue;
                    }
                }
            }
            if(thisV>maxVal)
                bestV = v;
        }
        if(!bestV.equals(agent.getLocation())){
            System.out.println("Wandering to better node... "+bestV);
            return MarsUtil.gotoAction(bestV);
        }
        return null;
    }
}
