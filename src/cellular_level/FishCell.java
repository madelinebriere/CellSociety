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
import java.util.Collection;
import java.util.Random;

import javafx.scene.paint.Color;
import util.CellData;
import util.Location;

public class FishCell extends WaterWorldCell {
	private static int stepsToBreed=5;
	private static Color fishColor = Color.GREEN;
	
	private Random randy = new Random();
	private int stepsSinceBreed;
	private boolean isEaten; //used as an indicator in WaterSociety

	public FishCell(){
		this(0,0);
	}
	
	public FishCell(int row, int col){
		super(row,col, fishColor);
		setStepsSinceBreed(0);
		setEaten(false);
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
	public Collection<Cell> update(CellData data) {
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
	

	public static int getStepsToBreed() {
		return stepsToBreed;
	}

	public static void setStepsToBreed(int stepsToBreed) {
		FishCell.stepsToBreed = stepsToBreed;
	}

	public void incrementStepsSinceBreed(){
		stepsSinceBreed++;
	}
	
	public static Color getFishColor() {
		return fishColor;
	}

	public static void setFishColor(Color fishColor) {
		FishCell.fishColor = fishColor;
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
