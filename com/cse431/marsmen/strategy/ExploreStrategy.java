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
		if (!agent.getAllBeliefs("role").getFirst().getParameters().get(0).equals("Explorer")) {
            return null;
        }
        if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) == 0) {
            return null;
        }
        if (agent.containsBelief(new LogicBelief("probedVertex", agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0)))){
        	//goto a different vertex
        	Util u = new Util(agent);
        	ArrayList <LogicBelief> edges = new ArrayList<>();
        	for (LogicBelief l : agent.getAllBeliefs("edge")){
        		if ((l.getParameters().get(0).equals( agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0)) || l.getParameters().get(1).equals( agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0))) && !l.getParameters().get(2).equals("11")){
        			edges.add(l);
        		}
        	}
        	if (edges.isEmpty()){
        		return MarsUtil.surveyAction();
        	}
        	else{
        		ArrayList <String> verticies = u.getNeighborVertexes(agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0));
        		Collections.shuffle(verticies);
        		for (String v : verticies){
        			if (agent.containsBelief(new LogicBelief("probedVertex", v))){
        				continue;
        			}
        			else {
        				return MarsUtil.gotoAction(v);
        			}
        		}
        		return MarsUtil.gotoAction(verticies.get(0));
        	}
        }
        else {
        	return MarsUtil.probeAction();
        }
        
	}
}
