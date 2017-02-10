package sim_rules;

import java.util.List;

import cellular_level.Cell;
import data_structures.SimulationName;
import societal_level.CellSociety;

public class FireSimRules extends SimRules{
	private static final double DEFAULT_PROBABILITY = .5;
	private static final int DEFAULT_STEPS = 1;
	
	private double probability;
	private int steps;
	
	public FireSimRules(){
		this(DEFAULT_PROBABILITY, DEFAULT_STEPS);
	}
	
	public FireSimRules(double p, int s){
		super(SimulationName.FIRE_SOCIETY);
		probability=p;
		steps = s;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	@Override
	public CellSociety applyCurrentRules(List<Cell> cells) {
	
	}
	
	

}
