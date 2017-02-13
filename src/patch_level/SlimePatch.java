package patch_level;

import javafx.scene.paint.Color;

public class SlimePatch extends Patch {
	private static final Color MOLD_COLOR = Color.GREEN;
	private static final int EVAPO_RATE= 3; //steps until decrement in chemical
	private static final int DEPOSIT_RATE = 2; //Amount deposited when cell is in patch
	
	private int stepsSinceDischarge;
	
	private int evaporate; //Rate of evaporation
	private int depositRate;	//Units of chemical released
	
	
	public SlimePatch(){
		this(0,0);
	}
	public SlimePatch(int row, int col){
		this(row,col, MOLD_COLOR);
	}
	
	public SlimePatch(int col, int row, Color color){
		this(row, col, color, EVAPO_RATE, DEPOSIT_RATE);
	}
	
	public SlimePatch(int row, int col, Color color, int evap, int depo){
		super(row, col, color);
		setStepsSinceDischarge(0);
		setEvaporate(evap);
		setDepositRate(depo);
	}
	
	@Override
	public void update() {
		incrementSteps();
		checkDischarge();
		layCampTrail();
	}
	
	@Override
	public Patch createCopy() {
		SlimePatch copy = new SlimePatch();
		copy.basicCopy(this);
		copy.setDepositRate(this.getDepositRate());
		copy.setEvaporate(this.getEvaporate());
		copy.setStepsSinceDischarge(this.getStepsSinceDischarge());
		return copy;
	}
	
	private void incrementSteps(){
		stepsSinceDischarge++;
	}
	
	private void checkDischarge(){
		if(stepsSinceDischarge>=evaporate){
			setStepsSinceDischarge(0);
			decrementConcentration();
		}
	}
	private void layCampTrail(){
		if(getMyCell()!=null){
			incrementConcentration(depositRate);
		}
	}
	
	public int getStepsSinceDischarge() {
		return stepsSinceDischarge;
	}
	public void setStepsSinceDischarge(int stepsSinceDischarge) {
		this.stepsSinceDischarge = stepsSinceDischarge;
	}
	public int getEvaporate() {
		return evaporate;
	}
	public void setEvaporate(int evaporate) {
		this.evaporate = evaporate;
	}
	public int getDepositRate() {
		return depositRate;
	}
	public void setDepositRate(int depositRate) {
		this.depositRate = depositRate;
	}
	
	
}
