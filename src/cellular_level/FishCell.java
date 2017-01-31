package cellular_level;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import util.Location;

public class FishCell extends Cell {
	private static int stepsToBreed=1;
	private static Color fishColor = Color.GREEN;
	
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
	protected ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<Location> nullCells) {
		
		return null;
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
