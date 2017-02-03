/**
 * Cell used in the segregation simulation.
 * Classified solely by color (state).
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import util.CellData;
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
	public Collection<Cell> update(CellData data) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		double percentSame = percentSame(data);
		if(!isSatisfied(percentSame)){
			Location newSpot = data.getCopyAvailableLocation();
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
	
	private double percentSame(CellData data){
		int same = data.countSameNeighbors(this);
		int total = data.countNonEmptyNeighbors(this);
		double percentSame = ((double)same)/(total);
		return percentSame;
	}

	
	public static double getSatisfiedThreshold() {
		return satisfiedThreshold;
	}

	public static void setSatisfiedThreshold(double satisfiedThreshold) {
		HouseCell.satisfiedThreshold = satisfiedThreshold;
	}
	
}
