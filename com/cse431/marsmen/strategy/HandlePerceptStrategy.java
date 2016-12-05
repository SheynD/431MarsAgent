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
        /* Kind of percept */
        String perceptName = p.getName();
        /* Translate percept into belief */
        LogicBelief belief = MarsUtil.perceptToBelief(p);

        /* Translate Percept params */
        XMLVisitor paramTrans = new XMLVisitor();

        ParameterList perceptParams = new ParameterList(p.getParameters());


        /* Switch statement to deal with all possible percepts */
        switch(perceptName){
            case "achievement":
                break;
            case "bye":
                break;
            case "deadline":
                break;
            case "edges":
                break;
            case "energy":
                break;
            case "health":
                break;
            case "id":
                break;
            case "lastAction":
				lastAction = (String)paramTrans.visit(perceptParams,"");
                break;
            case "lastActionParam":
				lastActionParams = (String)paramTrans.visit(perceptParams,"");
                break;
            case "lastActionResult":
				lastActionResult = (String)paramTrans.visit(perceptParams,"");
                break;
            case "lastStepScore":
                break;
            case "maxEnergy":
                break;
            case "maxEnergyDisabled":
                break;
            case "maxHealth":
                break;
            case "money":
                break;
            case "position":
                break;
            case "probedVertex":
                break;
            case "ranking":
                break;
            case "requestAction":
                break;
            case "role":
                break;
            case "score":
                break;
            case "simEnd":
                break;
            case "simStart":
                break;
            case "step":
                break;
            case "steps":
                break;
            case "strength":
                break;
            case "surveyedEdge":
                break;
            case "timestamp":
                break;
            case "vertices":
                break;
            case "visRange":
                break;
            case "visibleEdge":
                break;
            case "visibleEntity":
                break;
            case "visibleVertex":
                break;
            case "zoneScore":
                break;
            case "zonesScore":
                break;
        }
		
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