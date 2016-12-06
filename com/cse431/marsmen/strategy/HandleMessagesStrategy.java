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
	
		if (belief.getPredicate().equals("needRepair")) {
			String node = belief.getParameters().get(0).toString();
			m.addBelief(new LogicBelief("needRepair", node));
		}
		else if (belief.getPredicate().equals("repairComing")) {
			String node = belief.getParameters().get(0).toString();
			String agentName = belief.getParameters().get(0).toString();
			m.removeBeliefs("repairComing", "", agentName);
			m.addBelief(new LogicBelief("repairComing", node, agentName));
		}
	}
	
	public void handleMessages (MarsAgent m) {
		Collection<Message> messages = m.retrieveAllMessages();
		
		for ( Message msg : messages ) {
			handleMessage(m, msg);
		}
	}
}
