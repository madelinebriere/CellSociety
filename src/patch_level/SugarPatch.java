package patch_level;

import javafx.scene.paint.Color;

/**
 * Patch specific to the SugarScape Simulation
 * 
 * @author maddiebriere
 *
 */

public class SugarPatch extends Patch {
	private static final Color SUGAR_COLOR = Color.ORANGE;
	private static final int SUGAR_GROW = 1;

	private int sugarGrowBackRate; // number sugar added back each step
	
	public SugarPatch(){
		this(0,0);
	}
	
	public SugarPatch(int row, int col){
		this(row, col, SUGAR_COLOR);
	}
	
	public SugarPatch(int row, int col, Color color){
		this(row, col, color, SUGAR_GROW);
	}
	
	public SugarPatch(int row, int col, Color color, int grow){
		super(row,col,color);
		setSugarGrowBackRate(grow);
	}
	
	@Override
	public void update() {
		incrementConcentration(sugarGrowBackRate);
	}

	@Override
	public Patch createCopy() {
		SugarPatch copy = new SugarPatch();
		copy.basicCopy(this);
		copy.setSugarGrowBackRate(this.getSugarGrowBackRate());
		return copy;
	}

	public int getSugarGrowBackRate() {
		return sugarGrowBackRate;
	}

	public void setSugarGrowBackRate(int sugarGrowBackRate) {
		this.sugarGrowBackRate = sugarGrowBackRate;
	}


}
