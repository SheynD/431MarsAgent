package com.cse431.marsmen.strategy;

import java.util.Collection;

import com.cse431.marsmen.MarsAgent;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.*;
//import massim.javaagents.agents.MarsUtil;

public class HandlePerceptStrategy implements Strategy{

    public Action execute (MarsAgent agent) {
        handlePercepts(agent);
        return null;
    }

    public void handlePercept(MarsAgent agent, Percept p) {
        /* Kind of percept */
        String perceptName = p.getName();
        /* Translate percept into belief */
        //LogicBelief belief = MarsUtil.perceptToBelief(p);

        /* Translate Percept params */
        PrologVisitor paramTrans = new PrologVisitor();

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
                agent.removeBeliefs("energy");
                String energy = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief("energy", energy));
                break;
            case "health":
                agent.removeBeliefs("health");
                String health = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief("health", health));
                break;
            case "id":
                break;
            case "lastAction":
                break;
            case "lastActionParam":
                break;
            case "lastActionResult":
                break;
            case "lastStepScore":
                break;
            case "maxEnergy":
                LogicBelief newBelief = new LogicBelief("maxEnergy", (String)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(newBelief);
                break;
            case "maxEnergyDisabled":
                break;
            case "maxHealth":
                LogicBelief newBelief_ = new LogicBelief("maxHealth", (String)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(newBelief_);
                break;
            case "money":
                break;
            case "position":
                agent.removeBeliefs("position", "", agent.getName());
                String pos = (String)perceptParams.get(0).accept(paramTrans,"");
                System.out.println("Adding position: "+pos);
                LogicBelief posi = new LogicBelief("position", pos, agent.getName(), agent.getRole());
                agent.addBelief(posi);
                agent.broadcastBelief(posi);
                /* Add later
                   if (agent.getAllBeliefs("vertex", p.getParameters().getFirst().toString()).isEmpty()) {
                   agent.addBelief(new LogicBelief("vertex", p.getParameters().getFirst().toString(), "-1"));
                   }
                   */
                break;
            case "probedVertex":
                LogicBelief prob = new LogicBelief("probedVertex", (String)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(prob);
                agent.broadcastBelief(prob);
                break;
            case "ranking":
                break;
            case "requestAction":
                break;
            case "role":
                agent.addBelief(new LogicBelief("role", (String)p.getParameters().get(0).accept(paramTrans,"")));
                System.out.println("Adding belief of role:"+(String)p.getParameters().get(0).accept(paramTrans,""));
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
                LogicBelief surv = new LogicBelief("edge", (String)p.getParameters().get(0).accept(paramTrans,""), (String)p.getParameters().get(1).accept(paramTrans,""), (String)p.getParameters().get(2).accept(paramTrans,""));
                agent.addBelief(surv);
                agent.broadcastBelief(surv);
                break;
            case "timestamp":
                break;
            case "vertices":
                break;
            case "visRange":
                break;
            case "visibleEdge":
                LogicBelief vis = new LogicBelief("edge", (String)p.getParameters().get(0).accept(paramTrans,""), (String)p.getParameters().get(1).accept(paramTrans,""), "11");
                agent.addBelief(vis);
                agent.broadcastBelief(vis);
                break;
            case "visibleEntity":
                String vehicle_name = (String)perceptParams.get(0).accept(paramTrans,"");
                String vertex = (String)perceptParams.get(1).accept(paramTrans,"");
                String team = (String)perceptParams.get(2).accept(paramTrans,"");
                String disabled = (String)perceptParams.get(3).accept(paramTrans,"");
                /* Store enemy team, unnecessary? */
                if (agent.getAllBeliefs("enemyTeam").isEmpty() && !team.equals(agent.getTeam())) {
                    LogicBelief belief_enemey_team = new LogicBelief("enemyTeam", team);
                    agent.addBelief(belief_enemey_team);
                    agent.broadcastBelief(belief_enemey_team);
                }
                /* Save visible entity to belief */
                LogicBelief ve = new LogicBelief("visibleEntity", agent.getName(),vehicle_name,vertex,team,disabled);
                agent.addBelief(ve);
                break;
            case "visibleVertex":
                LogicBelief visV = new LogicBelief("vertex", (String)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(visV);
                agent.broadcastBelief(visV);
                break;
            case "zoneScore":
                break;
            case "zonesScore":
                break;
        }

    }

    /* Handle all percepts, using the handlePercept function */
    private void handlePercepts(MarsAgent m){
        Collection<Percept> percepts = m.retrieveAllPercepts();
        /* Remove 'visible' entities because they may change step to step? */
        //m.removeBeliefs("visibleEdge");
        //m.removeBeliefs("visibleEntity");
        //m.removeBeliefs("visibleVertex");

        /* Process each percept */
        for ( Percept p : percepts ) {
            handlePercept(m,p);
        }

    }
}
