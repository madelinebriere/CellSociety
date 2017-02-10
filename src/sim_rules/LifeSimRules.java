package sim_rules;

public class LifeSimRules extends SimRules{
	private static final String LIFE_NAME = "Life Society";
	private static final int NUM_FOR_LIVE = 3;
	private static final int OVER_POP = 3;
	private static final int UNDER_POP=2;
	
	private int numForLive;
	private int overPop;
	private int underPop;

	public LifeSimRules() {
		this(NUM_FOR_LIVE, OVER_POP, UNDER_POP);
	}

	public LifeSimRules(int numForLive, int overPop, int underPop) {
		super(LIFE_NAME);
		this.numForLive = numForLive;
		this.overPop = overPop;
		this.underPop = underPop;
	}

	public int getNumForLive() {
		return numForLive;
	}

	public void setNumForLive(int numForLive) {
		this.numForLive = numForLive;
	}

	public int getOverPop() {
		return overPop;
	}

	public void setOverPop(int overPop) {
		this.overPop = overPop;
	}

	public int getUnderPop() {
		return underPop;
	}

	public void setUnderPop(int underPop) {
		this.underPop = underPop;
	}
	
	

}
