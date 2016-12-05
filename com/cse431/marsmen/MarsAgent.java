package com.cse431.marsmen;

import eis.iilang.*;
import massim.javaagents.Agent;
import massim.javaagents.agents.MarsUtil;
import apltk.interpreter.data.LogicBelief;
import apltk.interpreter.data.LogicGoal;
import com.cse431.marsmen.strategy.*;

import java.util.*;

public class MarsAgent extends Agent {
    /* Member variables */

	private String lastAction;
	private String lastActionResult;
	private String lastActionParams;
	
	private ArrayList<Strategy> strategies;

    /* Constructor */
	public MarsAgent(String name, String team) {
		super(name, team);
        lastAction = "";
        lastActionResult = "";
        lastActionParams = "";
        
        // Set subsumption hierarchy
        strategies = new ArrayList<Strategy>();
        strategies.add(new HandlePerceptStrategy());
        strategies.add(new NeedRepairStrategy());
        strategies.add(new RepairStrategy());
        strategies.add(new RechargeStrategy());
        strategies.add(new SaboteurStrategy());
        strategies.add(new ZoningStrategy());
        strategies.add(new ExploreStrategy());
        strategies.add(new SurveyStrategy());
        strategies.add(new WanderStrategy());
        strategies.add(new SkipStrategy());
	}

    /* What to do at this time step */
	public Action step() {
		
		Action action = null;
		for (Strategy strat : strategies) {
			action = strat.execute(this);
			if (action != null) break;
		}
		
		return action;
	}

	@Override
	public void handlePercept(Percept p) {}
}
