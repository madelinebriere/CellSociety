/**
 * Class representing dead cells in the Game of Life Simulation
 * Capable of active updates based on surroundings
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collection;

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
	
	@Override
	public Cell createCopy(){
		DeadCell copy = new DeadCell();
		copy.setMyLocation(this.getMyLocation());
		copy.setMyState(this.getMyState());
		return copy;
	}
	
	/**
	 * @param neighbors Cell neighbors
	 * @param nullCells Cells with no current occupants, stored as nulls
	 * @return ArrayList of Cells for the next generation. Will contain either the current
	 * dead cell or a new live cell in the same location. Never empty.
	 */
	@Override
	public Collection<Cell> update(Collection<Cell> currentCells, Collection<EmptyCell> available, int size) {
		Collection<Cell> neighbors = neighbors(currentCells, size);
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
	
	@Override
	public Collection<Cell> neighbors(Collection<Cell> currentCells, int size){
		return getNeighbors(currentCells);
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