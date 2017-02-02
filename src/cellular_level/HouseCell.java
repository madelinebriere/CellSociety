/**
 * Cell used in the segregation simulation.
 * Classified solely by color (state).
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
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
	public Cell createCopy(){
		HouseCell copy = new HouseCell();
		copy.setMyLocation(this.getMyLocation());
		copy.setMyState(this.getMyState());
		return copy;
	}
	
	/**
	 * @param neighbors Cell neighbors
	 * @param nullCells Cells with no current occupants, stored as nulls
	 * @return ArrayList of next generation Cells, either contains the current Cell in the
	 * same location, or the current Cell in a new location (if it was not satisfied)
	 */
	@Override
	public ArrayList<Cell> update(ArrayList<Cell> currentCells, int size) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		ArrayList<Cell> neighbors = getFirstNeighbors(currentCells);
		ArrayList<EmptyCell> nullCells = getEmptyCells(currentCells);
		double percentSame = percentSame(neighbors);
		if(!isSatisfied(percentSame)){
			Location newSpot = getOpenSpot(nullCells);
			HouseCell relocatedCell = new HouseCell(newSpot.getMyRow(), newSpot.getMyRow(), this.getMyState());
			nextGen.add(relocatedCell);
			
			EmptyCell leftover = new EmptyCell(this);
			nextGen.add(leftover);
		}
		else{
			nextGen.add(this);
		}
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
	
	private Location getOpenSpot(ArrayList<EmptyCell> nullCells){
		Random randy = new Random();
		int emptyIndex = randy.nextInt(nullCells.size());
		return nullCells.get(emptyIndex).getMyLocation();
	}

	
	public static double getSatisfiedThreshold() {
		return satisfiedThreshold;
	}

	public static void setSatisfiedThreshold(double satisfiedThreshold) {
		HouseCell.satisfiedThreshold = satisfiedThreshold;
	}
	
}
