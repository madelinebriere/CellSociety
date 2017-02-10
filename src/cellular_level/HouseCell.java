/**
 * Cell used in the segregation simulation.
 * Classified solely by color (state).
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import data_structures.CellData;
import util.Location;

public class HouseCell extends Cell{
	public static double satisfiedThreshold = .40;
	
	public HouseCell(){
		super();
	}
	
	public HouseCell(Color c){
		super(c);
	}
	
	public HouseCell(int row, int col, Color state){
		super(row, col, state);
	}
	
	@Override
	public Cell createCopy(){
		HouseCell copy = new HouseCell();
		copy.basicCopy(this);
		return copy;
	}
	
	/**
	 * Update method for "people" in segregation simulation (HouseCells)
	 * @param data CellData object holding information for HouseCell use
	 * @return ArrayList of next generation Cells, either contains the current Cell in the
	 * same location, or the current Cell in a new location (if it was not satisfied)
	 */
	@Override
	public List<Cell> update(CellData data) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		double percentSame = percentSame(data);
		if(!isSatisfied(percentSame)){
			relocate(data, nextGen);
		}
		else{
			stayInPlace(nextGen);
		}
		return nextGen;
	}
	
	private void relocate(CellData data, ArrayList<Cell>nextGen){
		Location newSpot = data.getCopyAvailableLocation();
		if(newSpot!=null){
			HouseCell relocatedCell = new HouseCell(0, 0, this.getMyState());
			relocatedCell.copyLocation(newSpot);
			nextGen.add(relocatedCell);
		}else{
			stayInPlace(nextGen);
		}
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
	
	private void stayInPlace(ArrayList<Cell>nextGen){
		nextGen.add(this);
	}

	
	public static double getSatisfiedThreshold() {
		return satisfiedThreshold;
	}

	public static void setSatisfiedThreshold(double satisfiedThreshold) {
		HouseCell.satisfiedThreshold = satisfiedThreshold;
	}
	
}
