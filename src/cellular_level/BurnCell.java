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

import data_structures.CellData;
import util.Location;

public class BurnCell extends Cell {
	private int stepsToBurn;
	private int mySteps;


	public BurnCell(){
		this(0,0);
	}
	
	public BurnCell(int row, int col){
		super(row, col, Color.RED);
		setMySteps(0);
		setStepsToBurn(1);
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
