package com.cse431.marsmen.strategy;

import java.util.Collection;

import com.cse431.marsmen.MarsAgent;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import eis.iilang.ParameterList;
import eis.iilang.Percept;
import eis.iilang.XMLVisitor;
import massim.javaagents.agents.MarsUtil;

public class HandlePerceptStrategy implements Strategy{

	public Action execute (MarsAgent agent) {
		return null;
	}

	public void handlePercept(Percept p) {
  
		
	}

    /* Handle all percepts, using the handlePercept function */
    private void handlePercepts(){
		Collection<Percept> percepts = getAllPercepts();
        /* Remove 'visible' entities because they may change step to step? */
		removeBeliefs("visibleEdge");
		removeBeliefs("visibleEntity");
		removeBeliefs("visibleVertex");

        /* Process each percept */
		for ( Percept p : percepts ) {
            handlePercept(p);
        }

    }
}