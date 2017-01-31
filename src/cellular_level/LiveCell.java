package cellular_level;

import java.awt.Color;
import java.util.ArrayList;


import util.Location;

public class LiveCell extends Cell {
	private static int underpopulation = 2;
	private static int overpopulation = 3;
	private static Color liveColor = Color.BLACK;
	
	public LiveCell(){
		super();
		setMyState(liveColor);
	}
	
	public LiveCell(int row, int col){
		super(row,col, liveColor);
	}
	
	@Override
	protected ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<Location> nullCells) {
		ArrayList<Cell> newGen = new ArrayList<Cell>();
		int numLive = countSameNeighbors(neighbors);
		if(isOverpopulated(numLive)||isUnderpopulated(numLive)){
			DeadCell child = new DeadCell();
			child.copyLocation(this);
			newGen.add(child);
		}
		else{
			newGen.add(this);
		}
		return newGen;
		
	}
	
	private boolean isOverpopulated(int numLive){
		return numLive>overpopulation;
	}
	
	private boolean isUnderpopulated(int numLive){
		return numLive<underpopulation;
	}
	
	

}
