/**
 * Class representing dead cells in the Game of Life Simulation
 * Capable of active updates based on surroundings
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import java.util.ArrayList;


import util.Location;

public class DeadCell extends Cell {
	private static int numForLive = 3;
	private static Color deadColor = Color.WHITE;
	
	public DeadCell(){
		super();
		setMyState(deadColor);
	}
	
	public DeadCell(int row, int col){
		super(row,col, deadColor);
	}
	
	/**
	 * @param neighbors Cell neighbors
	 * @param nullCells Cells with no current occupants, stored as nulls
	 * @return ArrayList of Cells for the next generation. Will contain either the current
	 * dead cell or a new live cell in the same location. Never empty.
	 */
	@Override
	public ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<EmptyCell> nullCells, int size) {
		ArrayList<Cell> newGen = new ArrayList<Cell>();
		int numLive = countDiffNeighbors(neighbors);
		if(isReadyToLive(numLive)){
			LiveCell child = new LiveCell();
			child.copyLocation(this);
			newGen.add(child);
		}
		else{
			newGen.add(this);
		}
		return newGen;
		
	}
	
	private boolean isReadyToLive(int numLive){
		return numLive == numForLive;
	}

	public static int getNumForLive() {
		return numForLive;
	}

	public static void setNumForLive(int numForLive) {
		DeadCell.numForLive = numForLive;
	}

	public static Color getDeadColor() {
		return deadColor;
	}

	public static void setDeadColor(Color deadColor) {
		DeadCell.deadColor = deadColor;
	}
	
	
	

}