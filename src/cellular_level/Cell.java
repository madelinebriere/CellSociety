package cellular_level;
import util.Location;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public abstract class Cell {
	private Location myLocation;
	private Color myState;
	
	public Cell(){
		myLocation = new Location(0,0);
		myState = Color.WHITE;
	}
	
	public Cell(int row, int col, Color state){
		myLocation = new Location(row, col);
		myState=state;
	}
	
	protected abstract ArrayList<Cell> update(ArrayList<Cell>neighbors, ArrayList<Location>nullCells);

	protected int countSameNeighbors(ArrayList<Cell>neighbors){
		int sameCount = 0;
		for(Cell c: neighbors){
			if(c.getMyState()==getMyState()){
				sameCount++;
			}
		}
		return sameCount;
	}
	
	protected int countDiffNeighbors(ArrayList<Cell>neighbors){
		int diffCount = 0;
		for(Cell c: neighbors){
			if(c.getMyState()!=getMyState()){
				diffCount++;
			}
		}
		return diffCount;
	}
	
	protected void copyLocation(Cell copyFrom){
		this.setMyLocation(copyFrom.getMyLocation());
	}
	
	public Location getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	public int getMyRow() {
		return myLocation.getMyRow();
	}

	public void setMyRow(int row) {
		myLocation.setMyRow(row);
	}

	public int getMyCol() {
		return myLocation.getMyCol();
	}

	public void setMyCol(int col) {
		myLocation.setMyCol(col);
	}

	public Color getMyState() {
		return myState;
	}

	public void setMyState(Color myState) {
		this.myState = myState;
	}
	
}
