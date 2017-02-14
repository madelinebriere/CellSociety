
package cellular_level;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import data_structures.CellData;

/**
 * Burning tree cell in the forest fire CA simulation
 * Burns for a set number of steps before dying
 * 
 * @author maddiebriere
 */

public class BurnCell extends Cell {
	private static final Color BURN_COLOR = Color.RED;
	public static final int STEPS_TO_BURN=1;
	
	private int stepsToBurn;
	private int mySteps;
	
	public BurnCell(){
		this(0,0);
	}
	
	public BurnCell(int row, int col){
		this(row,col,BURN_COLOR);
	}
	
	public BurnCell(int row, int col, Color color){
		super(row, col, color);
		setMySteps(0);
		setStepsToBurn(STEPS_TO_BURN);
	}
	
	@Override
	public Cell createCopy(){
		BurnCell copy = new BurnCell();
		copy.basicCopy(this);
		copy.setMySteps(this.getMySteps());
		return copy;
	}
	
	/**
	 * @param CellData holding information about board and allowing access to Cell's neighbors
	 * @return An ArrayList of Cells for the next generation of Cells. This ArrayList will either 
	 * hold a burnt cell or the same burning cell.
	 */
	@Override
	public List<Cell> update(CellData data) {
		ArrayList<Cell>nextGen = new ArrayList<Cell>();
		incrementSteps();
		if(!isBurnt()){
			nextGen.add(this); 
		}
		return nextGen;
	}
	
	private boolean isBurnt(){
		return mySteps>=stepsToBurn;
	}
	public int getStepsToBurn() {
		return stepsToBurn;
	}
	public void setStepsToBurn(int stepsToBurn) {
		this.stepsToBurn = stepsToBurn;
	}
	public int getMySteps() {
		return mySteps;
	}
	public void setMySteps(int mySteps) {
		this.mySteps = mySteps;
	}
	public void incrementSteps(){
		mySteps++;
	}
}