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
import java.util.Collection;
import java.util.Random;

import javafx.scene.paint.Color;

public class SharkCell extends WaterWorldCell {
	private static Color sharkColor = Color.YELLOW;
	private static int stepsToStarve = 3;
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
	public Collection<Cell> update(Collection<Cell> currentCells, Collection<EmptyCell> available, int size) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		eatOrMove(currentCells, available, size);
		
		available=getOpenCells(neighbors(currentCells, size), available);
		breedOrDie(nextGen,currentCells, available, size);
		return nextGen;
	}
	
	@Override
	public Collection<Cell> neighbors(Collection<Cell> currentCells, int size){
		return getWrappedNeighbors(getCardinalNeighbors(currentCells),size);
	}
	
	private void eatOrMove(Collection<Cell> currentCells, Collection<EmptyCell> available, int size){
		Collection<Cell>possibleFood = neighbors(currentCells, size);
		FishCell food = getRandomFish(possibleFood);
		if(food!=null){
			eatFish(food);
		}
		else{
			stepsSinceEat++;
			move(currentCells, available, size);
		}
	}
	
	private void breedOrDie(Collection<Cell> nextGen, Collection<Cell> currentCells, Collection<EmptyCell> available, int size){
		if(!isStarved()){
			nextGen.add(this);
			if(timeToBreed()){
				stepsSinceBreed=0;
				Collection<Cell>neighbors = neighbors(currentCells, size);
				SharkCell baby = breed(neighbors, available, size);
				if(baby!=null){
					nextGen.add(baby);
					System.out.println("HERE");
				}
			}
			else{
				stepsSinceBreed++;
			}
		}
	}
	
	private void eatFish(FishCell food){
		food.setEaten(true);
		setStepsSinceEat(0);
	}
	
	private SharkCell breed(Collection<Cell> nearbyCells, Collection<EmptyCell> available, int size){
		EmptyCell breedSpot = getBreedSpot(nearbyCells, available, size);
		if(breedSpot == null){return null;}
		SharkCell baby = new SharkCell(breedSpot.getMyRow(), breedSpot.getMyCol());
		return baby;
	}
	
	private FishCell getRandomFish(Collection<Cell> neighbors){
		ArrayList<FishCell> possibleFood = locateFishCells(neighbors);
		if(possibleFood != null && possibleFood.size()>0){
			int fishIndex = randy.nextInt(possibleFood.size());
			return possibleFood.get(fishIndex);
		}
		else{
			return null;
		}	
	}
	
	private ArrayList<FishCell> locateFishCells(Collection<Cell>neighbors){
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
