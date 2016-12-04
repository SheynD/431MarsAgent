
import java.util.ArrayList;

import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;

public class RepairStrategy {
	public Action execute(MarsAgent agent) {
        if (!agent.getAllBeliefs("role").getFirst().getParameters().get(0).equals("Repairer")) {
            return null;
        }
        if (agent.getAllBeliefs("needRepair").isEmpty() && agent.getAllBeliefs("repairComing").isEmpty()){
        	return null;
        }
        ArrayList<String> goals = new ArrayList<String>();
        if (!agent.getAllBeliefs("repairComing").isEmpty()){
        	
        	for (LogicBelief l : agent.getAllBeliefs("repairComing")){
        		if (l.getParameters().get(1).equals(agent.getName())){
        			if (agent.getAllBeliefs("position").getFirst().getParameters().get(0).equals(l.getParameters().get(0))){
        				agent.removeBeliefs("repairComing");
        				String entity = "";
        				for (LogicBelief l2 : agent.getAllBeliefs("visibleEntity")){
        					if (l2.getParameters().get(0).equals(l.getParameters().get(0))){
        						entity = l2.getParameters().get(1);
        					}
        				}
        				if(!entity.equals("")){
        					return MarsUtil.repairAction(entity);
        				}
        				
        			}
        			goals.add(l.getParameters().get(0));
        		}
        	}
        	if (goals.size()>0){
        		return MarsUtil.gotoAction(getDir(goals,agent));
        	}
        }
        if (!agent.getAllBeliefs("needRepair").isEmpty()){
        	for (LogicBelief l : agent.getAllBeliefs("needRepair")){
        		goals.add(l.getParameters().get(0));
        		agent.addBelief(new LogicBelief("repairComing", l.getParameters().get(0), agent.getName()));
        		break;
        	}
        	agent.removeBeliefs("needRepair");
        	if (goals.size()>0){
        		return MarsUtil.gotoAction(getDir(goals,agent));
        	}
        }
        
        return null;
        
	}
	
	private String getDir(ArrayList <String> goals, MarsAgent agent){
		Util u = new Util(agent);
		ArrayList<String> path = u.getDirection(agent.getAllBeliefs("position").getFirst().getParameters().get(0), goals);
		if (path.size() > 0 && !path.get(0).equals("test") && u.getNeighborVertexes(agent.getAllBeliefs("position").getFirst().getParameters().get(0)).contains(path.get(0))) {
            return path.get(0);
        }
		else return "";
	}
	
}
