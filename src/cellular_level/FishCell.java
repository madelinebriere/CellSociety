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
import java.util.Random;

import javafx.scene.paint.Color;

public class FishCell extends WaterWorldCell {
	private static int stepsToBreed=1;
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
	
	/**
	 * Like SharkCell, FishCell also requires second degree neighbors because it 
	 * can move and then breed, placing a new FishCell in an adjacent Cell
	 */
	@Override
	public ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<EmptyCell> nullCells, int size) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		if(!isEaten()){
			move(neighbors);
			FishCell baby = breed(neighbors, size);
			if(baby!=null){
				nextGen.add(baby);
			}
			nextGen.add(this);
		}
		return nextGen;
	}
	
	private FishCell breed(ArrayList<Cell> nearbyCells, int size){
		EmptyCell breedSpot = getBreedSpot(nearbyCells, size);
		FishCell baby = new FishCell(breedSpot.getMyRow(), breedSpot.getMyCol());
		return baby;
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
