package patch_level;

import cellular_level.Cell;
import data_structures.PatchName;
import javafx.scene.paint.Color;

public class SlimePatch extends Patch {
	private static final Color MOLD_COLOR = Color.GREEN;
	private static final int EVAPO_RATE= 3; //steps until decrement in chemical
	private static final int DEPOSIT_RATE = 2; //Amount deposited when cell is in patch
	private static final int MAX_SATURATION = 20;
	
	private int camp;
	private int stepsSinceDischarge;
	
	private int evaporate; //Rate of evaporation
	private int depositRate;	//Units of chemical released
	private int maxSaturation;
	
	
	public SlimePatch(){
		this(0,0);
	}
	public SlimePatch(int row, int col){
		this(row,col, MOLD_COLOR);
	}
	
	public SlimePatch(int col, int row, Color color){
		this(row, col, color, EVAPO_RATE, DEPOSIT_RATE, MAX_SATURATION);
	}
	
	public SlimePatch(int row, int col, Color color, int evap, int depo, int max){
		super(row, col, color);
		setCamp(0);
		setStepsSinceDischarge(0);
		setEvaporate(evap);
		setDepositRate(depo);
		setMaxSaturation(max);
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
		copy.setCamp(this.getCamp());
		copy.setEvaporate(this.getEvaporate());
		copy.setMaxSaturation(this.getMaxSaturation());
		copy.setStepsSinceDischarge(this.getStepsSinceDischarge());
		return copy;
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
	
	@Override
	public Patch getTargetPatch(Cell c, Patch[][] patches) {
		Patch toRet = null;
		int maxCamp = 0;
		double minDistance = 1000; //random large number
		for(int i=0; i<patches.length; i++){
			for(int j=0; j<patches[0].length; j++){
				Patch curr = patches[i][j];
				if(greaterCamp(maxCamp, curr) || 
						equalButCloser(maxCamp, minDistance, curr, c)){
					toRet = curr;
					maxCamp = ((SlimePatch)curr).getCamp();
					minDistance = c.getMyLocation().distance(curr.getMyLocation());
				}
			}
		}
		return toRet;
	}
	
	private boolean greaterCamp(int maxCamp, Patch curr){
		return curr.getMyPatchType()==PatchName.SLIME_PATCH &&
				((SlimePatch)curr).getCamp()>maxCamp;
	}
	
	private boolean equalCamp(int maxCamp, Patch curr){
		return curr.getMyPatchType()==PatchName.SLIME_PATCH &&
				((SlimePatch)curr).getCamp()==maxCamp;
	}
	
	private boolean equalButCloser(int maxCamp, double minDistance, Patch curr, Cell c){
		double currDist = c.getMyLocation().distance(curr.getMyLocation());
		return equalCamp(maxCamp, curr)&& currDist<minDistance;
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
	public int getMaxSaturation() {
		return maxSaturation;
	}
	public void setMaxSaturation(int maxSaturation) {
		this.maxSaturation = maxSaturation;
	}

	
	
	

	
}
