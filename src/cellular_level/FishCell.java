/**
 * Fish cell in Water World simulation
 * 
 * May merge with SharkCell to create superclass
 * because of duplicate code
 * 
 * @author maddiebriere
 */
package cellular_level;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import util.CellData;
import util.Location;
public class FishCell extends WaterWorldCell {
	private static final int STEPS_TO_BREED=5;
	private static final Color FISH_COLOR = Color.GREEN;
	
	
	private int stepsToBreed;
	private int stepsSinceBreed;
	private boolean isEaten; //used as an indicator in WaterSociety
	public FishCell(){
		this(0,0);
	}
	
	public FishCell(int row, int col){
		this(row,col,FISH_COLOR);
	}
	
	public FishCell(int row, int col, Color color){
		this(row, col, color, STEPS_TO_BREED);
	}
	
	public FishCell(int row, int col, Color color, int steps){
		super(row,col,color);
		setStepsToBreed(steps);
		setEaten(false);
		setStepsSinceBreed(0);
	}
	
	@Override
	public Cell createCopy(){
		FishCell copy = new FishCell();
		copy.basicCopy(this);
		copy.setStepsSinceBreed(this.getStepsSinceBreed());
		copy.setEaten(this.isEaten());
		return copy;
	}
	
	/**
	 * Update method for FishCell, returns relevant updated cells
	 * @param data CellData object providing Cell information and access
	 * @return ArrayList of new updated cells, including current cell or moved current cell,
	 *  and a baby fish cell if the current cell is ready to breed
	 */
	@Override
	public List<Cell> update(CellData data) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		if(!isEaten()){
			changeState(data,nextGen);
		}
		return nextGen;
	}
	
	private void changeState(CellData data, ArrayList<Cell> nextGen){
		move(data);
		if(timeToBreed()){
			breed(data, nextGen);
		}else{
			incrementStepsSinceBreed();
		}
		nextGen.add(this);
	}
	
	private void breed(CellData data, ArrayList<Cell> nextGen){
		stepsSinceBreed=0;
		FishCell baby = getBabyFish(data);
		if(baby!=null){
			nextGen.add(baby);
		}
	}
	
	private FishCell getBabyFish(CellData data){
		Location breedSpot = getBreedSpot(data);
		if(breedSpot!=null){
			FishCell baby = new FishCell();
			baby.copyLocation(breedSpot);
			return baby;
		}
		return null;
	}
	
	private boolean timeToBreed(){
		return stepsSinceBreed>=stepsToBreed;
	}
	
	public int getStepsToBreed() {
		return stepsToBreed;
	}
	public void setStepsToBreed(int stepsToBreed) {
		this.stepsToBreed = stepsToBreed;
	}
	public void incrementStepsSinceBreed(){
		stepsSinceBreed++;
	}
	
	public int getStepsSinceBreed() {
		return stepsSinceBreed;
	}
	public void setStepsSinceBreed(int stepsSinceBreed) {
		this.stepsSinceBreed = stepsSinceBreed;
	}
	public boolean isEaten() {
		return isEaten;
	}
	public void setEaten(boolean isEaten) {
		this.isEaten = isEaten;
	}
	
	
}