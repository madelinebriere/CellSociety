package sim_rules;

import data_structures.SimulationName;

public class PopSimRules extends SimRules {
	private static final double DEFAULT_THRESH = .5;
	
	private double satisfiedThreshold;
	

	public PopSimRules() {
		this(DEFAULT_THRESH);
	}
	
	public PopSimRules(double s){
		super(SimulationName.POP_SOCIETY);
		satisfiedThreshold = s;
	}

	public double getSatisfiedThreshold() {
		return satisfiedThreshold;
	}

	public void setSatisfiedThreshold(double satisfiedThreshold) {
		this.satisfiedThreshold = satisfiedThreshold;
	}

}
