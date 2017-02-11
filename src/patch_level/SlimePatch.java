package patch_level;

import javafx.scene.paint.Color;

public class SlimePatch extends Patch {
	private static final Color MOLD_COLOR = Color.BLACK;
	private static final int STEPS_TIL_DISCHARGE = 5;
	
	private int camp;
	private int stepsSinceDischarge;
	
	public SlimePatch(){
		this(0,0);
	}
	public SlimePatch(int col, int row){
		this(col,row, MOLD_COLOR);
	}
	public SlimePatch(int col, int row, Color color){
		super(col, row, color);
		setCamp(0);
		setStepsSinceDischarge(0);
	}
	@Override
	public void update() {
		incrementSteps();
		checkDischarge();
		layCampTrail();
	}
	
	private void incrementSteps(){
		stepsSinceDischarge++;
	}
	
	private void checkDischarge(){
		if(stepsSinceDischarge>=STEPS_TIL_DISCHARGE){
			setStepsSinceDischarge(0);
			decrementCamp();
		}
	}
	private void decrementCamp(){
		camp--;
	}
	
	private void incrementCamp(){
		camp++;
	}
	
	private void layCampTrail(){
		if(getMyCell()!=null){
			incrementCamp();
		}
	}
	
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
		this.camp = camp;
	}
	
	
	public int getStepsSinceDischarge() {
		return stepsSinceDischarge;
	}
	public void setStepsSinceDischarge(int stepsSinceDischarge) {
		this.stepsSinceDischarge = stepsSinceDischarge;
	}

	
}
