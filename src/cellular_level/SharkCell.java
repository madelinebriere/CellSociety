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
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import data_structures.CellData;

import util.Location;
public class SharkCell extends WaterWorldCell {
	private static Color sharkColor = Color.YELLOW;
	private static int stepsToStarve = 3;
	private static int stepsToBreed = 5;
	
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
		copy.basicCopy(this);
		copy.setStepsSinceBreed(this.getStepsSinceBreed());
		copy.setStepsSinceEat(this.getStepsSinceEat());
		return copy;
	}
	/**
	 * Update for SharkCell, returns new Cells
	 * @param data CellData provided Cell with necessary access and information
	 * @return List of updated cells, including current shark (same spot or moved) or not 
	 * (if it has starved), and baby shark if the shark has bred
	 */
	@Override
	public List<Cell> update(CellData data) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		eatOrMove(nextGen, data);
		breedOrDie(nextGen, data);
		return nextGen;
	}
	
	private void eatOrMove(List <Cell> nextGen, CellData data){
		FishCell food = getRandomFish(data);
		if(food!=null){
			eatFish(food);
		}
		else{
			move(data);
			incrementStepsSinceEat();
		}
	}
	
	private void breedOrDie(List <Cell> nextGen, CellData data){
		if(!isStarved()){
			stayAlive(nextGen);
			if(timeToBreed()){
				setStepsSinceBreed(0);
				breed(data,nextGen);
			}
			else{
				incrementStepsSinceBreed();
			}
		}
	}
	
	private void eatFish(FishCell food){
		food.setEaten(true);
		setStepsSinceEat(0);
	}
	
	private void breed(CellData data, List<Cell> nextGen){
		SharkCell baby = getBabyShark(data);
		if(baby!=null){
			nextGen.add(baby);
		}
	}
	
	private SharkCell getBabyShark(CellData data){
		Location breedSpot = getBreedSpot(data);
		if(breedSpot != null){
			SharkCell baby = new SharkCell();
			baby.copyLocation(breedSpot);
			return baby;
		}
		return null;
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
	
	private ArrayList<FishCell> locateFishCells(List<Cell>neighbors){
		ArrayList<FishCell> possibleFood = new ArrayList<FishCell>();
		for(Cell c: neighbors){
			if(c!=null && c instanceof FishCell && !((FishCell)c).isEaten()){
				possibleFood.add((FishCell)c);
			}
		}
		return possibleFood;
	}
	
	private void stayAlive(List <Cell>nextGen){
		nextGen.add(this);
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
	
	public void incrementStepsSinceEat(){
		stepsSinceEat++;
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
	
	public void incrementStepsSinceBreed(){
		stepsSinceBreed++;
	}
}