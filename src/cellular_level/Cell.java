/**
 * 
 * Because we will have different cells of interest, we need 
 * to pass the entire grid!!
 * 
 * @author maddiebriere
 */


package cellular_level;
import util.CellData;
import util.Location;

import java.util.Collection;

import javafx.scene.paint.Color;

public abstract class Cell implements Comparable{
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
	
	public boolean positionEquals(Object o){
		if(o == this){
			return true;
		}
		if(!(o instanceof Cell)){
			return false;
		}
		return getMyLocation().equals(((Cell)o).getMyLocation());
	}
	
	@Override
	public int compareTo(Object o) {
		if(o == null) {return -1;}
		if(this==o){return 0;}
		return this.getClass().getName().compareTo(o.getClass().getName());
		
	}
	
	public <T extends Cell>boolean locationIn(Collection<T>cells){
		for(Cell c: cells){
			if(positionEquals(c)){
				return true;
			}
		}
		return false;
	}
	
	public abstract Cell createCopy();
	
	public abstract Collection<Cell> update(CellData data);
	
	public void copyLocation(Cell copyFrom){
		this.setMyCol(copyFrom.getMyCol());
		this.setMyRow(copyFrom.getMyRow());
	}
	
	public void copyLocation(Location copyFrom){
		this.setMyCol(copyFrom.getMyCol());
		this.setMyRow(copyFrom.getMyRow());
	}
	
	public void basicCopy(Cell copyFrom){
		this.setMyState(copyFrom.getMyState());
		this.copyLocation(copyFrom);
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
