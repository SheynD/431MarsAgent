package com.cse431.marsmen.strategy;

import java.util.Collection;
import java.util.LinkedList;

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
		
	}
	
	public void handleMessages (MarsAgent m) {
		Collection<Message> messages = m.retrieveAllMessages();
		
		for ( Message msg : messages ) {
			handleMessage(m, msg);
		}
	}
}
