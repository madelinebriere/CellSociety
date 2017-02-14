package patch_level;

/**
 * Patch specific to the SugarScape Simulation
 * 
 * @author maddiebriere
 *
 */

public class SugarPatch extends Patch {
	public static final int SUGAR_GROW = 1;
	
	
	private int sugarGrowBackRate; //number sugar added back each step
	private int sugar; //number sugar held by patch
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Patch createCopy() {
		SugarPatch copy = new SugarPatch();
		copy.basicCopy(this);
		//TODO: complete
		return copy;
	}

}
