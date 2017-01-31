package cellular_level;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import util.Location;

public class HouseCell extends Cell{
	public static double satisfiedThreshold;
	
	public HouseCell(){
		super();
	}
	
	public HouseCell(int row, int col, Color state){
		super(row, col, state);
	}
	
	@Override
	protected ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<Location> nullCells) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		double percentSame = percentSame(neighbors);
		if(!isSatisfied(percentSame)){
			Location newSpot = getOpenSpot(nullCells);
			setMyLocation(newSpot); //change location
		}
		nextGen.add(this);
		return nextGen;
	}

	private boolean isSatisfied(double percentSame){
		return percentSame >= satisfiedThreshold;
	}
	
	private double percentSame(ArrayList<Cell> neighbors){
		int same = countSameNeighbors(neighbors);
		double percentSame = ((double)same)/(neighbors.size());
		return percentSame;
	}
	
	private Location getOpenSpot(ArrayList<Location> nullCells){
		Random randy = new Random();
		int emptyIndex = randy.nextInt(nullCells.size());
		return nullCells.get(emptyIndex);
	}

	
	public static double getSatisfiedThreshold() {
		return satisfiedThreshold;
	}

	public static void setSatisfiedThreshold(double satisfiedThreshold) {
		HouseCell.satisfiedThreshold = satisfiedThreshold;
	}
	
}
