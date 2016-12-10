package com.cse431.marsmen.strategy;

import java.util.ArrayList;
import java.util.Collections;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;

import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

public class WanderStrategy implements Strategy{

    /* Note that as of now, this Strategy never fails, so any strategy below it
     * does not get executed. Maybe we should add some randomness (ie, fail 1/2 the time)?
     */
    public Action execute (MarsAgent agent) {
        /* If I am disabled, can't explore */
        if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) == 0) {
            return null;
        }
        Util u = new Util(agent);
        /* All neighbor vertices to my vertex */
        ArrayList <String> verticies = u.getNeighborVertexes(agent.getLocation());
        /* Choose a random one to go to */
        Collections.shuffle(verticies);
        System.out.println("Wandering to "+verticies.get(0));
        return MarsUtil.gotoAction(verticies.get(0));
    }
}
