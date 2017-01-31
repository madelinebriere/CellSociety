package cellular_level;

import java.awt.Color;
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
	
	@Override
	protected ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<Location> nullCells) {
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
	
	

}