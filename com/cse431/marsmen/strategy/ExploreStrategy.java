package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;
import java.util.ArrayList;
import java.util.Collections;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;


public class ExploreStrategy implements Strategy{
    public Action execute(MarsAgent agent) {
        /* Only run if I am an explorer */
        if (!agent.getRole().equals("Explorer")) {
            return null;
        }
        /* If I am disabled, can't explore */
        if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) == 0) {
            return null;
        }
        /* If I am on a probed vertex */
        if (agent.containsBelief(new LogicBelief("probedVertex", agent.getLocation()))){
            /* Go to a different vertex */
            Util u = new Util(agent);
            ArrayList <LogicBelief> edges = new ArrayList<>();
            for (LogicBelief l : agent.getAllBeliefs("edge")){
                String vertex1 = l.getParameters().get(0);
                String vertex2 = l.getParameters().get(1);
                String weight = l.getParameters().get(2);
                /* If it is an edge we are next to, and its weigth is not unknown (11) */
                if ((vertex1.equals(agent.getLocation()) || vertex2.equals(agent.getLocation())) 
                        && !weight.equals("11")){
                    edges.add(l);
                }
            }
            /* Don't know any edge weights, we should survey */
            if (edges.isEmpty()){
                return MarsUtil.surveyAction();
            }
            /* Randomly choose an unprobed vertex to traverse, and go to it */
            else{
                ArrayList <String> verticies = u.getNeighborVertexes(agent.getLocation());
                Collections.shuffle(verticies);
                for (String v : verticies){
                    if (agent.containsBelief(new LogicBelief("probedVertex", v))){
                        continue;
                    }
                    else {
                        System.out.println("Going to vertex "+v+" to go to an unprobed vertex");
                        return MarsUtil.gotoAction(v);
                    }
                }
                return null;
            }
        }
        else {
            return MarsUtil.probeAction();
        }

    }
}
