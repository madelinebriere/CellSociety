/**
 * Burning tree cell in the forest fire CA simulation
 * Burns for a set number of steps before dying
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import java.util.ArrayList;

import util.Location;

public class BurnCell extends Cell {
	private static Color burnColor = Color.RED;
	private static int stepsToBurn = 1;
	private int mySteps;


	public BurnCell(){
		super();
		setMyState(burnColor);
		setMySteps(0);
	}
	
	public BurnCell(int row, int col){
		super(row, col, burnColor);
		setMySteps(0);
	}
	
	@Override
	public Cell createCopy(){
		BurnCell copy = new BurnCell();
		copy.setMyLocation(this.getMyLocation());
		copy.setMyState(this.getMyState());
		copy.setMySteps(this.getMySteps());
		return copy;
	}
	
	/**
	 * @param neighbors Cell neighbors
	 * @param nullCells Cells with no current occupants, stored as nulls
	 * @return An ArrayList of Cells for the next generation of Cells. This ArrayList will
	 * be empty unless the tree IS NOT burnt (in which case, the cell remains for the 
	 * next generation). Otherwise, an empty List is returned.
	 */
	@Override
	public ArrayList<Cell> update(ArrayList<Cell> currentCells, int size) {
		ArrayList<Cell>nextGen = new ArrayList<Cell>();
		mySteps++;
		if(!isBurnt()){
			nextGen.add(this); //nextGen only filled if this is satisfied
		}
		else{
			EmptyCell burned = new EmptyCell(this);
			nextGen.add(burned);
		}
		return nextGen;
	}
	
	private boolean isBurnt(){
		return mySteps>=stepsToBurn;
	}

	public static Color getBurnColor() {
		return burnColor;
	}

	public static void setBurnColor(Color burnColor) {
		BurnCell.burnColor = burnColor;
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

}
