package com.cse431.marsmen.strategy;

import java.util.Collection;

import com.cse431.marsmen.MarsAgent;

import apltk.interpreter.data.LogicBelief;
import apltk.interpreter.data.Message;
import eis.iilang.*;
//import massim.javaagents.agents.MarsUtil;

public class HandleMessagesStrategy implements Strategy{

	boolean shouldRepair = false;
	String repairNode = "";
	String agentToRepair = "";
	
	public Action execute (MarsAgent agent) {
		handleMessages(agent);
		return null;
	}

	public void handleMessage (MarsAgent m, Message msg) {
		
		LogicBelief belief = (LogicBelief) msg.value;
	
		if (belief.getPredicate().equals("needRepair")) {
			repairNode = belief.getParameters().get(0).toString();
			agentToRepair = belief.getParameters().get(1).toString();
			shouldRepair = true;
			//m.addBelief(new LogicBelief("needRepair", node));
		}
		else if (belief.getPredicate().equals("repairComing")) {
			String disabledAgent = belief.getParameters().get(0).toString();
			String node = belief.getParameters().get(1).toString();
			String agentName = belief.getParameters().get(2).toString();
			//m.removeBeliefs("repairComing", "", agentName);
			m.addBelief(new LogicBelief("repairComing", disabledAgent, node, agentName));
		}
		else if (belief.getPredicate().equals("removeRepair")){
			m.removeBeliefs("removeRepair");
			String disabledAgent = belief.getParameters().get(0).toString();
			String node = belief.getParameters().get(1).toString();
			String agentName = belief.getParameters().get(2).toString();
			m.removeBeliefs("repairComing", disabledAgent, node, agentName);
		}
		else if (belief.getPredicate().equals("edge")) {
			String node1 = belief.getParameters().get(0).toString();
			String node2 = belief.getParameters().get(1).toString();
			String weight = belief.getParameters().get(2).toString();
			// System.err.println("ADD EDGE " + node1 + "__" + node2 + "__" + weight);
			//m.removeBeliefs("edge", node1, node2);
			m.addBelief(new LogicBelief("edge", node1, node2, weight));
		}
		else if (belief.getPredicate().equals("vertex")) {
			String node = belief.getParameters().get(0).toString();
			//String value = belief.getParameters().get(1).toString();
			// System.err.println("ADD NODE " + node + "__" + value);
			//m.removeBeliefs("node", node, "");
			//m.addBelief(new LogicBelief("node", node, value));
			m.addBelief(new LogicBelief("vertex", node));
		}
		else if (belief.getPredicate().equals("position")) {
			// Position (node, agentName, role)
			String node = belief.getParameters().get(0).toString();
			String agentName = belief.getParameters().get(1).toString();
			String role = belief.getParameters().get(2).toString();			
			m.removeBeliefs("position", "", agentName, "");
			m.addBelief(new LogicBelief("position", node, agentName, role));
		}
		else if (belief.getPredicate().equals("visibleAgent")) {
			// VisibleAgent (reporterName, vehicleName, vertex, team, isDisabled)
			String reporterName = belief.getParameters().get(0).toString();
			String vehicleName = belief.getParameters().get(1).toString();
			String vertex = belief.getParameters().get(2).toString();
			String team = belief.getParameters().get(3).toString();
			String isDisabled = belief.getParameters().get(4).toString();
			m.removeBeliefs("visibleAgent", "", vehicleName, "", "", "");
			m.addBelief(new LogicBelief("visibleAgent", reporterName, vehicleName, vertex, team, isDisabled));
		}
	}
	
	public void handleMessages (MarsAgent m) {
		Collection<Message> messages = m.retrieveAllMessages();
		
		
		
		for ( Message msg : messages ) {
			handleMessage(m, msg);
		}
		
		if (shouldRepair && m.getRole().equals("Repairer")){
			boolean shouldGo = true;
			for (LogicBelief l : m.getAllBeliefs("repairComing")){
				System.err.println("checking repairs:" + l.getParameters().get(0) + " " + l.getParameters().get(1) + " " + l.getParameters().get(2) + " disabledAgent: " + repairNode);
				if (l.getParameters().get(0).equals(agentToRepair)){
					shouldGo = false;
					break;
				}
			}
			if (shouldGo){
				System.err.println(agentToRepair + " " + repairNode + " " + m.getName());
				LogicBelief lb = new LogicBelief("repairComing", agentToRepair, repairNode, m.getName());
				m.addBelief(lb);
				m.broadcastBelief(lb);
			}
		}
	}
}
