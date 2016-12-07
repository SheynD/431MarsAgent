package com.cse431.marsmen.strategy;

import java.util.Collection;

import com.cse431.marsmen.MarsAgent;

import apltk.interpreter.data.LogicBelief;
import apltk.interpreter.data.Message;
import eis.iilang.*;
//import massim.javaagents.agents.MarsUtil;

public class HandleMessagesStrategy implements Strategy{

	public Action execute (MarsAgent agent) {
		handleMessages(agent);
		return null;
	}

	public void handleMessage (MarsAgent m, Message msg) {
		
		LogicBelief belief = (LogicBelief) msg.value;
	
		if (belief.getPredicate().equals("needsRepair")) {
			String node = belief.getParameters().get(0).toString();
			m.addBelief(new LogicBelief("needsRepair", node));
		}
		else if (belief.getPredicate().equals("repairComing")) {
			String node = belief.getParameters().get(0).toString();
			String agentName = belief.getParameters().get(1).toString();
			m.removeBeliefs("repairComing", "", agentName);
			m.addBelief(new LogicBelief("repairComing", node, agentName));
		}
		else if (belief.getPredicate().equals("edge")) {
			String node1 = belief.getParameters().get(0).toString();
			String node2 = belief.getParameters().get(1).toString();
			String weight = belief.getParameters().get(2).toString();
			// System.err.println("ADD EDGE " + node1 + "__" + node2 + "__" + weight);
			m.removeBeliefs("edge", node1, node2);
			m.addBelief(new LogicBelief("edge", node1, node2, weight));
		}
		else if (belief.getPredicate().equals("node")) {
			String node = belief.getParameters().get(0).toString();
			String value = belief.getParameters().get(1).toString();
			// System.err.println("ADD NODE " + node + "__" + value);
			m.removeBeliefs("node", node, "");
			m.addBelief(new LogicBelief("node", node, value));
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
	}
}
