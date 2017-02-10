/**
 * Class representing dead cells in the Game of Life Simulation
 * Capable of active updates based on surroundings
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import java.util.ArrayList;

import data_structures.CellData;

public class DeadCell extends LifeSimCell {
	private int numForLive;
	
	public DeadCell(){
		this(0,0);
	}
	
	public DeadCell(int row, int col){
		super(row,col, Color.WHITE);
		setNumForLive(3);
	}

	protected void changeState(CellData data, ArrayList<Cell> newGen){
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

	public int getNumForLive() {
		return numForLive;
	}

	public void setNumForLive(int numForLive) {
		this.numForLive = numForLive;
	}

}