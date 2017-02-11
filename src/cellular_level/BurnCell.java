/**
 * Burning tree cell in the forest fire CA simulation
 * Burns for a set number of steps before dying
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import util.CellData;
import util.Location;

public class BurnCell extends Cell {
	private static final Color BURN_COLOR = Color.RED;
	private static final int STEPS_TO_BURN=1;
	
	private static int stepsToBurn;
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
		else{
			generateBurntCell(nextGen);
		}
		return nextGen;
	}
	
	private void generateBurntCell(ArrayList <Cell> nextGen){
		EmptyCell burned = new EmptyCell(this);
		nextGen.add(burned);
	}
	
	private boolean isBurnt(){
		return mySteps>=stepsToBurn;
	}

	public static int getStepsToBurn() {
		return stepsToBurn;
	}

	public static void setStepsToBurn(int stepsToBurn) {
		BurnCell.stepsToBurn = stepsToBurn;
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
