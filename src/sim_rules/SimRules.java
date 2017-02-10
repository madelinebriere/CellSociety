package sim_rules;

import societal_level.CellSociety;

public abstract class SimRules {
	private String simulationName;
	
	public SimRules(String s){
		simulationName = s;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}
	
	
}
