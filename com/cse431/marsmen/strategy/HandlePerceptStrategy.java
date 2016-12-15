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
            case "achievement": // Don't need
                break;
            case "bye": // Don't need
                break;
            case "deadline": // Don't need
                break;
            case "edges":
                agent.removeBeliefs("edges");
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
            case "id": // Don't Need
                break;
            case "lastAction":
                agent.removeBeliefs("lastAction");
                String lastAction = p.getParameters().get(0).accept(paramTrans,"").toString();
                agent.addBelief(new LogicBelief("lastAction", lastAction));
                break;
            case "lastActionParam": // Don't need
                break;
            case "lastActionResult":
                agent.removeBeliefs("lastActionResult");
                String lastActionResult = p.getParameters().get(0).accept(paramTrans,"").toString();
                agent.addBelief(new LogicBelief("lastActionResult", lastActionResult));
                break;
            case "lastStepScore":
                agent.removeBeliefs("lastStepScore");
                String lastStepScore = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("lastStepScore", lastStepScore));
                break;
            case "maxEnergy":
                agent.removeBeliefs("maxEnergy");
                String maxEnergy = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief("maxEnergy", maxEnergy));
                break;
            case "maxEnergyDisabled":
                agent.removeBeliefs("maxEnergyDisabled");
                LogicBelief newBeliefMED = new LogicBelief("maxEnergy", Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,"")));
                agent.addBelief(newBeliefMED);
                break;
            case "maxHealth":
                agent.removeBeliefs("maxHealth");
                LogicBelief newBeliefMH = new LogicBelief("maxHealth", Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,"")));
                agent.addBelief(newBeliefMH);
                break;
            case "money":
                agent.removeBeliefs("money");
                String money = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("money", money));
                break;
            case "position": 
                // Position: (node, agentName, role)
                agent.removeBeliefs("position", "", agent.getName());
                String pos = (String)perceptParams.get(0).accept(paramTrans,"");
                LogicBelief posi = new LogicBelief("position", pos, agent.getName(), agent.getRole());
                agent.addBelief(posi);
                agent.broadcastBelief(posi);
                break;
            case "probedVertex":
                String vertex = (String)p.getParameters().get(0).accept(paramTrans,"");
                String value = Integer.toString((Integer)p.getParameters().get(1).accept(paramTrans,""));
                LogicBelief prob = new LogicBelief("probedVertex", vertex);
                LogicBelief vert = new LogicBelief("vertex", vertex, value);
                agent.addBelief(prob);
                agent.broadcastBelief(prob);
                agent.removeBeliefs("vertex",vertex,"0");
                agent.addBelief(vert);
                break;
            case "ranking": // Don't need
                break;  
            case "requestAction": // Don't need
                break;
            case "role":
                agent.addBelief(new LogicBelief("role", (String)p.getParameters().get(0).accept(paramTrans,"")));
                break;
            case "score":
                agent.removeBeliefs("score");
                String score = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("score", score));
                break;
            case "simEnd": // Don't need
                break;
            case "simStart": // Don't need
                break;
            case "step":
                agent.removeBeliefs("step");
                String step = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("step", step));
                break;
            case "steps":
                agent.removeBeliefs("steps");
                String steps = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("steps", steps));
                break;
            case "strength":
                agent.removeBeliefs("strength");
                String strength = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("strength", strength));
                break;
            case "surveyedEdge":
                String vertex1 = (String)p.getParameters().get(0).accept(paramTrans,"");
                String vertex2 = (String)p.getParameters().get(1).accept(paramTrans,"");
                String weight = Integer.toString((Integer)p.getParameters().get(2).accept(paramTrans,""));
                LogicBelief surv = new LogicBelief("edge", vertex1, vertex2, weight);
                if (!agent.containsBelief(surv)){
                	agent.addBelief(surv);
                	agent.broadcastBelief(surv);
                }
                break;
            case "timestamp": // Don't need
                break;
            case "vertices":
                agent.removeBeliefs("numVertices");
                String numVertices = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("numVertices", numVertices));
                break;
            case "visRange":
                agent.removeBeliefs("visRange");
                String visRange = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("visRange", visRange));
                break;
            case "visibleEdge":
                vertex1 = (String)p.getParameters().get(0).accept(paramTrans,"");
                vertex2 = (String)p.getParameters().get(1).accept(paramTrans,"");
                LogicBelief vis = new LogicBelief("edge", vertex1, vertex2, "11");
                if (!agent.containsBelief(vis)){
                	agent.addBelief(vis);
                	agent.broadcastBelief(vis);
                }
                break;
            case "visibleEntity":
                String vehicle_name = (String)perceptParams.get(0).accept(paramTrans,"");
                String vertex_ = (String)perceptParams.get(1).accept(paramTrans,"");
                String team = (String)perceptParams.get(2).accept(paramTrans,"");
                String disabled = (String)perceptParams.get(3).accept(paramTrans,"");
                /* Store enemy team, unnecessary? */
                if (agent.getAllBeliefs("enemyTeam").isEmpty() && !team.equals(agent.getTeam())) {
                    LogicBelief belief_enemey_team = new LogicBelief("enemyTeam", team);
                    agent.addBelief(belief_enemey_team);
                    agent.broadcastBelief(belief_enemey_team);
                }
                /* Save visible entity to belief */
                LogicBelief ve = new LogicBelief("visibleEntity", agent.getName(),vehicle_name,vertex_,team,disabled);
                agent.addBelief(ve);
                break;
            case "inspectedEntity":
                String name = (String)p.getParameters().get(0).accept(paramTrans,"");
                String role = (String)p.getParameters().get(2).accept(paramTrans,"");
                /* First, add to known inspected */
                agent.addBelief(new LogicBelief("inspectedEntity",name));
                /* Now, if it is a saboteur, add to known saboteurs */
            	if (role.equals("Saboteur")){
            		System.out.println("Found Saboteur: " + role + " name: " + name);
            		LogicBelief newBelief = new LogicBelief("enemySaboteur", name);
            		agent.addBelief(newBelief);
                	agent.broadcastBelief(newBelief);
            	}
            	
            case "visibleVertex":
                String vertex_name = (String)p.getParameters().get(0).accept(paramTrans,"");
                String team_occupied = (String)p.getParameters().get(1).accept(paramTrans,"");
                LogicBelief visV = new LogicBelief("vertex", vertex_name,"0");
                agent.addBelief(visV);
                agent.broadcastBelief(visV);
                break;
            case "zoneScore":
                agent.removeBeliefs("zoneScore");
                String zoneScore = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("zoneScore", zoneScore));
                break;
            case "zonesScore":
                agent.removeBeliefs("zonesScore");
                String zonesScore = Integer.toString((Integer)p.getParameters().get(0).accept(paramTrans,""));
                agent.addBelief(new LogicBelief ("zonesScore", zonesScore));
                break;
        }   
    }

    /* Handle all percepts, using the handlePercept function */
    private void handlePercepts(MarsAgent m){
        Collection<Percept> percepts = m.retrieveAllPercepts();
        /* Remove 'visible' entities because they may change step to step? */
        m.removeBeliefs("visibleEntity", m.getName());
        m.removeBeliefs("inZone", m.getName());

        /* Process each percept */
        for ( Percept p : percepts ) {
            handlePercept(m,p);
        }
    }
}
