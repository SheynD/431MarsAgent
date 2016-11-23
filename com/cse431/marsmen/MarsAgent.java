package com.cse431.marsmen;

import eis.iilang.Action;
import eis.iilang.Percept;
import massim.javaagents.Agent;

public class MarsAgent extends Agent {
	public MarsAgent(String name, String team) {
		super(name, team);
		//TODO add anything if needed
	}

	public Action step() {
		//TODO deliberate and return an action
		return new Action("recharge");
	}

	@Override
	public void handlePercept(Percept p) {
		// TODO Auto-generated method stub
		
	}
}