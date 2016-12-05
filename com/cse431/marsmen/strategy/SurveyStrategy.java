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
		if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) == 0) {
            return null;
        }
		ArrayList <LogicBelief> edges = new ArrayList<>();
    	for (LogicBelief l : agent.getAllBeliefs("surveyedEdge")){
    		if (l.getParameters().get(0).equals( agent.getAllBeliefs("position").getFirst().getParameters().get(0)) || l.getParameters().get(1).equals( agent.getAllBeliefs("position").getFirst().getParameters().get(0)) ){
    			edges.add(l);
    		}
    	}
    	if (edges.isEmpty()){
    		return MarsUtil.surveyAction();
    	}
    	else {
    		return null;
    	}
	}
	
}
