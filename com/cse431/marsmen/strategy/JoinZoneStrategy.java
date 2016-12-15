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
        int totalZonesScore = Integer.parseInt(agent.getAllBeliefs("zonesScore").getFirst().getParameters().get(0));
        int myZoneScore = Integer.parseInt(myZoneScoreStr);
        /* If disabled */
        if(agent.getHealth()==0)
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
        /* If we are in the biggest zone  or no known zones */
        if(myZoneScore == largestZoneVal || largestZoneVal==0 || totalZonesScore == largestZoneVal)
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
        for (LogicBelief edge : agent.getAllBeliefs("edge")){
            String v1 = edge.getParameters().get(0);
            String v2 = edge.getParameters().get(1);
            String w = edge.getParameters().get(2);
            if ((dir.equals(v1) && agent.getLocation().equals(v2)) || (dir.equals(v2) && agent.getLocation().equals(v1))){				
                if (agent.getEnergy() >= Integer.parseInt(w)){
                    System.out.println("\nGoing to join zone, next step:" + dir + "\n");
                    return MarsUtil.gotoAction(dir);
                }
            }
        }
        return null;
    }
}
