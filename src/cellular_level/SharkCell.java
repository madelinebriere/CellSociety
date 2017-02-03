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
import util.CellData;
import util.Location;

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
	public Collection<Cell> update(CellData data) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		eatOrMove(nextGen, data);
		breedOrDie(nextGen, data);
		return nextGen;
	}
	
	private void eatOrMove(Collection <Cell> nextGen, CellData data){
		FishCell food = getRandomFish(data);
		if(food!=null){
			eatFish(food);
		}
		else{
			stepsSinceEat++;
			move(data);
		}
	}
	
	private void breedOrDie(Collection <Cell> nextGen, CellData data){
		if(!isStarved()){
			nextGen.add(this);
			if(timeToBreed()){
				stepsSinceBreed=0;
				SharkCell baby = breed(data);
				if(baby!=null){
					nextGen.add(baby);
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
	
	private SharkCell breed(CellData data){
		Location breedSpot = getBreedSpot(data);
		if(breedSpot == null){return null;}
		SharkCell baby = new SharkCell(breedSpot.getMyRow(), breedSpot.getMyCol());
		return baby;
	}
	
	private FishCell getRandomFish(CellData data){
		ArrayList<FishCell> possibleFood = locateFishCells(data.getNeighbors(this));
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
