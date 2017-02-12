package patch_level;

import javafx.scene.paint.Color;

public class SlimePatch extends Patch {
	private static final Color MOLD_COLOR = Color.BLACK;
	private static final int EVAPO_RATE= 3; //steps until decrement in chemical
	private static final int DEPOSIT_RATE = 2; //Amount deposited when cell is in patch
	private static final int MAX_SATURATION = 20;
	
	private int camp;
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
		setCamp(0);
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
	
	private void incrementSteps(){
		stepsSinceDischarge++;
	}
	
	private void checkDischarge(){
		if(stepsSinceDischarge>=EVAPO_RATE){
			setStepsSinceDischarge(0);
			decrementCamp();
		}
	}
	private void decrementCamp(){
		if(camp-evaporate>=0)
			camp-= evaporate;
	}
	
	private void incrementCamp(){
		if(camp <=MAX_SATURATION)
			camp+= depositRate;
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
