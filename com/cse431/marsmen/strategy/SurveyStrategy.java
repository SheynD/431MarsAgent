package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
//import com.cse431.marsmen.Util;
import java.util.ArrayList;
//import java.util.Collections;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

public class SurveyStrategy implements Strategy{

    public Action execute(MarsAgent agent) {
        /* Only if I am not disabled */
        if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) == 0) {
            return null;
        }
        ArrayList <LogicBelief> unserveyed = new ArrayList<>();
        int num_edges = 0;
        for (LogicBelief l : agent.getAllBeliefs("edge")){
            String v1 = l.getParameters().get(0);
            String v2 = l.getParameters().get(1);
            String w = l.getParameters().get(2);
            /* If this edge is from or to my vertex and the weight is not 11 (unknown), mark as edge*/
            if ((v1.equals(agent.getLocation()) || v2.equals(agent.getLocation())) && w.equals("11")){
                System.out.println("Adding unsurveyed edge"+v1+"-"+v2+":"+w);
                unserveyed.add(l);
            }
        }
        /* If we know no edges around us, survey 
         * I am not sure if one survey is guaranteed to get all edges around my location or not.
         * Survey is a ranged action, so there is some randomness, depending on the agents range, right?*/
        if (unserveyed.size()>0){
            return MarsUtil.surveyAction();
        }
        return null;
    }

}
