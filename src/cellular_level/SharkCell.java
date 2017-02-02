/**
 * Shark cell in Water World Simulation
 * 
 * May need abstract super class because of duplicate
 * code with FishCell
 * 
 * @author maddiebriere
 */

package cellular_level;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class SharkCell extends WaterWorldCell {
	private static Color sharkColor = Color.YELLOW;
	private static int stepsToStarve = 5;
	private static int stepsToBreed = 20;
	
	private int stepsSinceEat;
	private int stepsSinceBreed;
	private Random randy = new Random();
	
	
	public SharkCell(){
		this(0,0);
	}
	
	public SharkCell(int row, int col){
		super(row, col, sharkColor);
		setStepsSinceEat(0);
		setStepsSinceBreed(0);
	}
	
	@Override
	public Cell createCopy(){
		SharkCell copy = new SharkCell();
		copy.setMyLocation(this.getMyLocation());
		copy.setMyState(this.getMyState());
		copy.setStepsSinceBreed(this.getStepsSinceBreed());
		copy.setStepsSinceEat(this.getStepsSinceEat());
		return copy;
	}


	/**
	 * Unlike many of the other types of cells, this cell requires 2nd order neighbors 
	 * as well as 1st order neighbors. This is because of possible mobility.
	 * 
	 * Check for nulls in other neighbors
	 */
	@Override
	public ArrayList<Cell> update(ArrayList<Cell> currentCells, ArrayList<EmptyCell> available, int size) {
		ArrayList<Cell> nearbyCells = getSecondNeighbors(currentCells);
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		eatOrMove(nextGen, nearbyCells, size);
		breedOrDie(nextGen, nearbyCells, size);
		return nextGen;
	}
	
	private void eatOrMove(ArrayList<Cell> nextGen, ArrayList<Cell> nearbyCells, int size){
		ArrayList<Cell> firstOrderNeighbors = getFirstOrderNeighbors(nearbyCells, size);
		FishCell food = getRandomFish(firstOrderNeighbors);
		if(food!=null){
			eatFish(food);
		}
		else{
			move(firstOrderNeighbors);
		}
	}
	
	private void breedOrDie(ArrayList<Cell> nextGen, ArrayList<Cell> nearbyCells, int size){
		if(!isStarved()){
			nextGen.add(this);
			if(timeToBreed()){
				SharkCell baby = breed(nearbyCells, size);
				if(baby!=null){
					nextGen.add(baby);
				}
			}
		}
	}
	
	private void eatFish(FishCell food){
		food.setEaten(true);
		setStepsSinceEat(0);
	}
	
	private SharkCell breed(ArrayList<Cell> nearbyCells, int size){
		EmptyCell breedSpot = getBreedSpot(nearbyCells, size);
		SharkCell baby = new SharkCell(breedSpot.getMyRow(), breedSpot.getMyCol());
		return baby;
	}
	
	
	private FishCell getRandomFish(ArrayList<Cell> neighbors){
		ArrayList<FishCell> possibleFood = locateFishCells(neighbors);
		if(possibleFood != null && possibleFood.size()>0){
			int fishIndex = randy.nextInt(possibleFood.size());
			return possibleFood.get(fishIndex);
		}
		else{
			return null;
		}
		
	}
	
	private ArrayList<FishCell> locateFishCells(ArrayList<Cell>neighbors){
		ArrayList<FishCell> possibleFood = new ArrayList<FishCell>();
		for(Cell c: neighbors){
			if(c!=null && c instanceof FishCell && !((FishCell)c).isEaten()){
				possibleFood.add((FishCell)c);
			}
		}
		return possibleFood;
	}
	
	private boolean timeToBreed(){
		return stepsSinceBreed>=stepsToBreed;
	}
	
	private boolean isStarved(){
		return stepsSinceEat>=stepsToStarve;
	}
	
	public static Color getSharkColor() {
		return sharkColor;
	}

	public static void setSharkColor(Color sharkColor) {
		SharkCell.sharkColor = sharkColor;
	}

	public static int getStepsToStarve() {
		return stepsToStarve;
	}

	public static void setStepsToStarve(int stepsToStarve) {
		SharkCell.stepsToStarve = stepsToStarve;
	}

	public static int getStepsToBreed() {
		return stepsToBreed;
	}

	public static void setStepsToBreed(int stepsToBreed) {
		SharkCell.stepsToBreed = stepsToBreed;
	}

	public int getStepsSinceEat() {
		return stepsSinceEat;
	}

	public void setStepsSinceEat(int stepsSinceEat) {
		this.stepsSinceEat = stepsSinceEat;
	}

	public int getStepsSinceBreed() {
		return stepsSinceBreed;
	}

	public void setStepsSinceBreed(int stepsSinceBreed) {
		this.stepsSinceBreed = stepsSinceBreed;
	}


}
