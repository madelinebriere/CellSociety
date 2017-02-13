package patch_level;

import javafx.scene.paint.Color;

/**
 * Extension of Patch specific to the Slime Society
 * simulation. These patches have memory in that they store a certain 
 * concentration (here, of cAMP). They discharge this concentration at 
 * a certain evaporation rate, and have deposited a certain amount of 
 * chemical in the patch if there is a Slime Cell in the patch. 
 * 
 * NOTE: This patch has a very active role -- it defines its concentration
 * by the Cell in it without the Cell having any role in the action. This
 * is because Patches are higher on the hierarchical chain and therefore
 * take on more responsibility.
 * 
 * @author maddiebriere
 *
 */

public class SlimePatch extends Patch {
	private static final Color MOLD_COLOR = Color.GREEN;
	public static final int EVAPO_RATE= 3; //steps until decrement in chemical
	public static final int DEPOSIT = 2; //Amount deposited when cell is in patch
	
	private int stepsSinceDischarge;
	
	private int evaporate; //Rate of evaporation
	private int depositInc;	//Units of chemical released
	
	
	public SlimePatch(){
		this(0,0);
	}
	public SlimePatch(int row, int col){
		this(row,col, MOLD_COLOR);
	}
	
	public SlimePatch(int col, int row, Color color){
		this(row, col, color, EVAPO_RATE, DEPOSIT);
	}
	
	public SlimePatch(int row, int col, Color color, int evap, int depo){
		super(row, col, color);
		setStepsSinceDischarge(0);
		setEvaporate(evap);
		setDeposit(depo);
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
		copy.setDeposit(this.getDeposit());
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
			incrementConcentration(depositInc);
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
	public int getDeposit() {
		return depositInc;
	}
	public void setDeposit(int depositRate) {
		this.depositInc = depositRate;
	}
	
	
}