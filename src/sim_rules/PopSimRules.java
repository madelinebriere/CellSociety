package sim_rules;

public class PopSimRules extends SimRules {
	private static final String POP_NAME = "Segregation Society";
	private static final double DEFAULT_THRESH = .5;
	
	private double satisfiedThreshold;
	

	public PopSimRules() {
		this(DEFAULT_THRESH);
	}
	
	public PopSimRules(double s){
		super(POP_NAME);
		satisfiedThreshold = s;
	}

	public double getSatisfiedThreshold() {
		return satisfiedThreshold;
	}

	public void setSatisfiedThreshold(double satisfiedThreshold) {
		this.satisfiedThreshold = satisfiedThreshold;
	}

}
