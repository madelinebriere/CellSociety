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
		copy.setMyLocation(this.getMyLocation());
		copy.setMyState(this.getMyState());
		copy.setStepsSinceBreed(this.getStepsSinceBreed());
		copy.setEaten(this.isEaten());
		return copy;
	}
	
	/**
	 * Like SharkCell, FishCell also requires second degree neighbors because it 
	 * can move and then breed, placing a new FishCell in an adjacent Cell
	 */
	@Override
	public Collection<Cell> update(CellData data) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		if(!isEaten()){
			move(data);
			if(timeToBreed()){
				stepsSinceBreed=0;
				FishCell baby = breed(data);
				if(baby!=null){
					nextGen.add(baby);
				}
			}else{
				stepsSinceBreed++;
			}
			nextGen.add(this);
		}
		return nextGen;
	}
	
	private FishCell breed(CellData data){
		Location breedSpot = getBreedSpot(data);
		if(breedSpot!=null){
			FishCell baby = new FishCell(breedSpot.getMyRow(), breedSpot.getMyCol());
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
