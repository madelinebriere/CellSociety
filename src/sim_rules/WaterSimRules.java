package sim_rules;

import data_structures.SimulationName;

public class WaterSimRules extends SimRules{
	private static final String WATER_NAME = "Wa-Tor Society";
	private static int DEFAULT_FISH_BREED = 3;
	private static int DEFAULT_SHARK_BREED = 20; 
	private static int DEFAULT_SHARK_STARVE= 3;
	
	private int fishBreed;
	private int sharkBreed;
	private int sharkStarve;

	public WaterSimRules() {
		this(DEFAULT_FISH_BREED, DEFAULT_SHARK_BREED, DEFAULT_SHARK_STARVE);
	}

	public WaterSimRules(int fishBreed, int sharkBreed, int sharkStarve) {
		super(SimulationName.WATER_SOCIETY);
		this.fishBreed = fishBreed;
		this.sharkBreed = sharkBreed;
		this.sharkStarve = sharkStarve;
	}

	public int getFishBreed() {
		return fishBreed;
	}

	public void setFishBreed(int fishBreed) {
		this.fishBreed = fishBreed;
	}

	public int getSharkBreed() {
		return sharkBreed;
	}

	public void setSharkBreed(int sharkBreed) {
		this.sharkBreed = sharkBreed;
	}

	public int getSharkStarve() {
		return sharkStarve;
	}

	public void setSharkStarve(int sharkStarve) {
		this.sharkStarve = sharkStarve;
	}
	
	

}
