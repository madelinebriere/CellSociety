package sim_rules;

public class FireSimRules extends SimRules{
	private static final String FIRE_NAME = "Fire Society";
	private static final double DEFAULT_PROBABILITY = .5;
	private static final int DEFAULT_STEPS = 1;
	
	private double probability;
	private int steps;
	
	public FireSimRules(){
		this(DEFAULT_PROBABILITY, DEFAULT_STEPS);
	}
	
	public FireSimRules(double p, int s){
		super(FIRE_NAME);
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
	
	

}
