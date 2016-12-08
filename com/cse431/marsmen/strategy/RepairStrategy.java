package com.cse431.marsmen.strategy;

import java.util.ArrayList;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

public class RepairStrategy implements Strategy {
	
	public Action execute(MarsAgent agent) {
        if (!agent.getAllBeliefs("role").getFirst().getParameters().get(0).equals("Repairer")) {
            return null;
        }
        if (agent.getAllBeliefs("repairComing").isEmpty()){
        	return null;
        }
        ArrayList<String> goals = new ArrayList<String>();
        if (!agent.getAllBeliefs("repairComing").isEmpty()){
        	
        	for (LogicBelief l : agent.getAllBeliefs("repairComing")){
        		System.err.println("repairComing: " + l.getParameters().get(0) + " " + l.getParameters().get(1) + " " + l.getParameters().get(2));
        		if (l.getParameters().get(2).equals(agent.getName())){
        			if (agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0).equals(l.getParameters().get(1))){
        				LogicBelief lb = new LogicBelief("removeRepair", l.getParameters().get(0), l.getParameters().get(1), l.getParameters().get(2));
        				agent.addBelief(lb);
        				agent.broadcastBelief(lb);
        				String entity = "";
        				for (LogicBelief l2 : agent.getAllBeliefs("visibleEntity", agent.getName())){
        					if (l2.getParameters().get(2).equals(l.getParameters().get(1)) && agent.getTeam().equals(l2.getParameters().get(3)) && l2.getParameters().get(4).equals("disabled")){
        						entity = l2.getParameters().get(1);
        					}
        				}
        				if(!entity.equals("")){
        					return MarsUtil.repairAction(entity);
        				}
        				
        			}
        			goals.add(l.getParameters().get(0));
        		}
        	}
        	if (goals.size()>0){
        		System.err.println("\n\n\n" + getDir(goals,agent) + "\n\n\n");
        		return MarsUtil.gotoAction(getDir(goals,agent));
        	}
        }
        
        return null;
        
	}
	
	private String getDir(ArrayList <String> goals, MarsAgent agent){
		Util u = new Util(agent);
		ArrayList<String> path = u.getDirection(agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0), goals);
		for (String p : path){
			System.out.println(p);
		}
		if (path.size() > 0 && path.get(0).equals(agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0))) {
			path.remove(0);
		}
		if (path.size() > 0 && !path.get(0).equals("test") && u.getNeighborVertexes(agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0)).contains(path.get(0))) {
            return path.get(0);
        }
		else return u.getNeighborVertexes(agent.getAllBeliefs("position", "", agent.getName()).getFirst().getParameters().get(0)).get(0);
	}
	
}
