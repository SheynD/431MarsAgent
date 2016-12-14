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
        String myZoneScore = agent.getAllBeliefs("zoneScore").getFirst().getParameters().get(0);
        if(!myZoneScore.equals("0"))
            return null;
        /* If disabled or low on energy */
        if(agent.getHealth()==0 || agent.getEnergy() < 4)
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
        for (LogicBelief edge : agent.getAllBeliefs("edge")){
			if ((dir.equals(edge.getParameters().get(0)) && agent.getLocation().equals(edge.getParameters().get(1))) || (dir.equals(edge.getParameters().get(1)) && agent.getLocation().equals(edge.getParameters().get(0)))){				
                if (agent.getEnergy() >= Integer.parseInt(edge.getParameters().get(2))){
                	System.out.println("\nGoing to join zone, next step:" + dir + "\n");
                    return MarsUtil.gotoAction(dir);
				}
			}
		}
        return null;
    }
}
