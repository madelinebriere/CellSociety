
package cellular_level;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import data_structures.CellData;
import util.Location;

/**
 * Cell used in the segregation simulation.
 * Classified solely by color (state).
 * 
 * NOTE: This classification has caused problems because
 * all other Cells are defined by class type, and different
 * colored HouseCells cannot be distinguished in this manner
 * 
 * @author maddiebriere
 */

public class HouseCell extends Cell{
	public static final Color DEFAULT_COLOR = Color.BLUE;
	public static final double SATISFIED_THRESH = .40;
	
	public double satisfiedThresh;
	
	public HouseCell(){
		this(0,0);
	}
	
	public HouseCell(Color color){
		this(0,0, color);
	}
	
	public HouseCell(int row, int col){
		this(row, row, DEFAULT_COLOR);
	}
	
	public HouseCell(int row, int col, Color state){
		this(row, col, state, SATISFIED_THRESH);
	}
	
	public HouseCell(int row, int col, Color state, double thresh){
		super(row, col, state);
		setSatisfiedThreshold(thresh);
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
		Location newSpot = data.getAvailableSpot();
		if(newSpot!=null){
			HouseCell relocatedCell = new HouseCell(0, 0, this.getMyState());
			relocatedCell.copyLocation(newSpot);
			nextGen.add(relocatedCell);
		}else{
			stayInPlace(nextGen);
		}
	}
	
	private boolean isSatisfied(double percentSame){
		return percentSame >= satisfiedThresh;
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
	
	public double getSatisfiedThreshold() {
		return satisfiedThresh;
	}
	public void setSatisfiedThreshold(double satisfiedThreshold) {
		this.satisfiedThresh = satisfiedThreshold;
	}
	
}