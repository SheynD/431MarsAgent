import apltk.interpreter.data.LogicBelief;
import eis.iilang.Action;
import massim.javaagents.agents.MarsUtil;



public class NeedRepairStrategy {

	public Action execute(MarsAgent agent) {
		
		if (agent.getAllBeliefs("health").getFirst().getParameters().get(0).isEmpty()) {
            return null;
        }
		if (agent.getAllBeliefs("position").getFirst().getParameters().get(0).isEmpty()){
			return null;
		}
		if (agent.getAllBeliefs("repairComing").getFirst().getParameters().get(0).equals(agent.getAllBeliefs("position").getFirst().getParameters().get(0))){
			return null;
		}
		if (Integer.parseInt(agent.getAllBeliefs("health").getFirst().getParameters().get(0)) == 0){
			agent.broadcastBelief(new LogicBelief("needRepair", agent.getAllBeliefs("position").getFirst().getParameters().get(0)));
			return MarsUtil.skipAction();
		}
		return null;
		
	}
	
}
