package sim_rules;

import java.util.List;

import cellular_level.Cell;
import data_structures.SimulationName;
import societal_level.CellSociety;

public abstract class SimRules {
	private SimulationName name;
	
	public SimRules(SimulationName n){
		name=n;
	}

	public SimulationName getSimulationName() {
		return name;
	}

	public void setSimulationName(SimulationName simulationName) {
		this.name = simulationName;
	}
	
	public abstract CellSociety applyCurrentRules(List<Cell> cells);
	
	
}
