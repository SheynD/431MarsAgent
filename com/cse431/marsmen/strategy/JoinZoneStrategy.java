package com.cse431.marsmen.strategy;

import com.cse431.marsmen.MarsAgent;
import com.cse431.marsmen.Util;
import massim.javaagents.agents.MarsUtil;
import apltk.interpreter.data.LogicBelief;
import java.util.*;

import eis.iilang.Action;

public class JoinZoneStrategy implements Strategy{
    @Override
    public Action execute (MarsAgent agent) {
    	/* If in a zone, don't do anything */
        String myZoneScoreStr = agent.getAllBeliefs("zoneScore").getFirst().getParameters().get(0);
        int myZoneScore = Integer.parseInt(myZoneScoreStr);
        if(myZoneScore > 5)
            return null;
        /* If low on energy */
        if(agent.getEnergy() ==0)
            return null;
        /* Find largest zone */
        int largestZoneVal = 0;
        for(LogicBelief inZ : agent.getAllBeliefs("inZone")){
            String location = inZ.getParameters().get(0).toString();
            String zoneScoreStr = inZ.getParameters().get(1).toString();
            int zoneScore = Integer.parseInt(zoneScoreStr);
            if(zoneScore > largestZoneVal)
                largestZoneVal = zoneScore;
        }
        /* No known zones */
        if(largestZoneVal==0)
            return null;
        /* Now add all known vertices of this zone to a list */
        ArrayList<String> goals = new ArrayList<String>();
        for(LogicBelief inZ : agent.getAllBeliefs("inZone")){
            String location = inZ.getParameters().get(0).toString();
            String zoneScoreStr = inZ.getParameters().get(1).toString();
            int zoneScore = Integer.parseInt(zoneScoreStr);
            if(zoneScore==largestZoneVal)
                goals.add(location);
        }
        /* Now, lets go there */
        Util u = new Util(agent);
        String dir = u.getDir(goals);
        System.out.println("\nGoing to join zone, next step:" + dir + "\n");
        return MarsUtil.gotoAction(dir);
    }
}
