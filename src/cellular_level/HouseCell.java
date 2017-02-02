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
	public static double satisfiedThreshold = .80;
	
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
	public ArrayList<Cell> update(ArrayList<Cell> currentCells, ArrayList<EmptyCell> available, int size) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		ArrayList<Cell> neighbors = getFirstNeighbors(currentCells);
		double percentSame = percentSame(neighbors);
		if(!isSatisfied(percentSame)){
			Location newSpot = getOpenSpot(available);
			if(newSpot!=null){
				HouseCell relocatedCell = new HouseCell(newSpot.getMyRow(), newSpot.getMyCol(), this.getMyState());
				nextGen.add(relocatedCell);
			}else{
				nextGen.add(this);
			}
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
		int total = countNonEmptyNeighbors(neighbors);
		double percentSame = ((double)same)/(total);
		System.out.println(same + " " + total);
		return percentSame;
	}
	
	private int countNonEmptyNeighbors(ArrayList<Cell> neighbors){
		int count=0;
		for(Cell c: neighbors){
			if(!(c instanceof EmptyCell)){
				count++;
			}
		}
		return count;
	}
	
	private Location getOpenSpot(ArrayList<EmptyCell> nullCells){
		if(nullCells==null || nullCells.size()==0){return null;}
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
