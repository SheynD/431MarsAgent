package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import eis.iilang.Action;

public class WanderStrategy implements Strategy{

	public Action execute (MarsAgent agent) {
		Util u = new Util(agent);
		ArrayList <String> verticies = u.getNeighborVertexes(agent.getAllBeliefs("position").getFirst().getParameters().get(0));
		Collections.shuffle(verticies);
		return MarsUtil.gotoAction(verticies.get(0));
	}
}
