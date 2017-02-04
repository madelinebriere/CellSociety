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

import util.CellData;
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
		copy.basicCopy(this);
		return copy;
	}
	
	/**
	 *@param data CellData object holding accessible information for Cell
	 * @return ArrayList of Cells for the next generation. Will contain either the current
	 * dead cell or a new live cell in the same location. Never empty.
	 */
	@Override
	public Collection<Cell> update(CellData data) {
		ArrayList<Cell> newGen = new ArrayList<Cell>();
		changeState(data, newGen);
		return newGen;
		
	}
	
	private void changeState(CellData data, ArrayList<Cell> newGen){
		int numLive = data.countDiffNeighbors(this);
		if(isReadyToLive(numLive)){
			generateLiveCell(newGen);
		}
		else{
			stayDead(newGen);
		}
	}
	
	private void generateLiveCell(ArrayList<Cell>newGen){
		LiveCell child = new LiveCell();
		child.copyLocation(this);
		newGen.add(child);
	}
	
	private void stayDead(ArrayList<Cell> newGen){
		newGen.add(this);
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