package cellular_level;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import util.Location;

public class SharkCell extends Cell {
	private static Color sharkColor = Color.YELLOW;
	private static int stepsToStarve = 5;
	private static int stepsToBreed = 20;
	
	private int stepsSinceEat;
	private int stepsSinceBreed;
	private Random randy;
	
	
	public SharkCell(){
		this(0,0);
	}
	
	public SharkCell(int row, int col){
		super(row, col, sharkColor);
		setStepsSinceEat(0);
		setStepsSinceBreed(0);
	}


	/**
	 * Possible concern: Is it okay to choose to eat this fish without "talking"
	 * to other SharkCells?
	 * 
	 * Check for nulls in other neighbors
	 */
	@Override
	protected ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<Location> nullCells) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		FishCell food = getRandomFish(neighbors);
		if(food!=null){
			eatFish(food);
		}
		else{
			move(neighbors, nullCells);
		}
		
		if(!isStarved()){
			nextGen.add(this);
			if(timeToBreed()){
				SharkCell baby = breed(nullCells);
				nextGen.add(baby);
			}
		}
		return nextGen;
	}
	
	private void move(ArrayList<Cell> neighbors, ArrayList<Location> nullCells){
		ArrayList<Cell> openSpots = getOpenAdjacentCells(neighbors, nullCells);
		int index = randy.nextInt(openSpots.size());
		this.copyLocation(openSpots.get(index));
	}
	
	private ArrayList<Cell> getOpenAdjacentCells(ArrayList<Cell> neighbors, ArrayList<Location> nullCells){
		ArrayList<Cell> toRet = new ArrayList<Cell>();
		for(Cell c: neighbors){
			if(c!=null && nullCells.contains(c.getMyLocation())){
				toRet.add(c);
			}
		}
		return toRet;
	}
	
	private void eatFish(FishCell food){
		food.setEaten(true);
		setStepsSinceEat(0);
	}
	
	private SharkCell breed(ArrayList<Location> nullCells){
		ArrayList<Location> possibleBreedSpots = new ArrayList<Location>();
		for(Location loc : nullCells){
			if(isAdjacent(loc)){
				possibleBreedSpots.add(loc);
			}
		}
		int index = randy.nextInt(possibleBreedSpots.size());
		Location breedSpot = possibleBreedSpots.get(index);
		SharkCell baby = new SharkCell(breedSpot.getMyRow(), breedSpot.getMyCol());
		return baby;
	}
	
	private boolean timeToBreed(){
		return stepsSinceBreed<=stepsToBreed;
	}
	
	private boolean isStarved(){
		return stepsSinceEat<=stepsSinceEat;
	}
	
	private FishCell getRandomFish(ArrayList<Cell> neighbors){
		ArrayList<FishCell> possibleFood = locateFishCells(neighbors);
		if(possibleFood.size()>0){
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
