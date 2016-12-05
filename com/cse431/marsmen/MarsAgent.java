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
	
	private ArrayList<Strategy> strategies;

    /* Constructor */
	public MarsAgent(String name, String team) {
		super(name, team);
        
        // Set subsumption hierarchy
        strategies = new ArrayList<Strategy>();
        strategies.add(new HandlePerceptStrategy());
        //strategies.add(new NeedRepairStrategy());
        //strategies.add(new RepairStrategy());
        strategies.add(new RechargeStrategy());
        strategies.add(new SaboteurStrategy());
        strategies.add(new ZoningStrategy());
        strategies.add(new ExploreStrategy());
        //strategies.add(new SurveyStrategy());
        strategies.add(new WanderStrategy());
        strategies.add(new SkipStrategy());
	}

    /* What to do at this time step */
	public Action step() {
		
		println("Agent : "+getName());
		Action action = null;
		for (Strategy strat : strategies) {
			action = strat.execute(this);
			if (action != null) break;
		}
		
		return action;
	}

    /* Return location of current agent */
    public String getLocation(){
        return getAllBeliefs("position", "", getName()).getFirst().getParameters().get(0);
    }

    /* Return integer level of energy */
	public int getEnergy() {
		if (getAllBeliefs("energy").isEmpty()) {
			return -1;
		}
		return Integer.parseInt(getAllBeliefs("energy").getFirst().getParameters().get(0));
	}

    /* Override the default All Beliefs to accept multiple predicates */
	public LinkedList<LogicBelief> getAllBeliefs(String predicate1, String... otherpredicates) {
		LinkedList<LogicBelief> beliefs = null;
        /* Use superclass method to get original list */
        if(!predicate1.equals(""))
            beliefs = getAllBeliefs(predicate1);
        else
            beliefs = new LinkedList<LogicBelief>(getBeliefBase());
        for( int i = 0; i<otherpredicates.length; i++){
            String thispredicate = otherpredicates[i];
            /* Ignore empty predicates */
            if(thispredicate.equals(""))
                continue;
            for(Iterator<LogicBelief> it = beliefs.iterator(); it.hasNext();){
                LogicBelief lb = it.next();
                /* Remove this logic belief if it is not consistent with given predicate */
                if(!lb.getParameters().get(i).equals(thispredicate))
                    it.remove();
            }
		}
		return beliefs;
	}
    
    public String getRole(){
		if (getAllBeliefs("role").isEmpty()) {
			return null;
		}
		return getAllBeliefs("role").getFirst().getParameters().get(0);
    }

    /* Remove a belief, overloaded to allow mutliple predicates */
	public void removeBeliefs(String predicate, String... otherpredicates) {

		LinkedList<LogicBelief> remove = new LinkedList<LogicBelief>();

		for (LogicBelief b : beliefs) {
            boolean satisfied = true;
            /* Check predicate equal or empty */
            if (b.getPredicate().equals(predicate) || predicate.equals("")){
                for( int i = 0; i<otherpredicates.length; i++){
                    String thispredicate = otherpredicates[i];
                    /* If a predicate is not satisfied (and not empty), it is not this one... */
                    if(!thispredicate.equals("") && !b.getParameters().get(i).equals(thispredicate)){
                        satisfied = false;
                        break;
                    }
                }
                if(satisfied)
                    remove.add(b);
            }
		}

		beliefs.removeAll(remove);

	}

    /* Make percepts public so strategies can get them */
	public Collection<Percept> retrieveAllPercepts(){
		return getAllPercepts();
	}

	@Override
	public void handlePercept(Percept p) {}
}
