/**
 * 
 * Because we will have different cells of interest, we need 
 * to pass the entire grid!!
 * 
 * @author maddiebriere
 */






package cellular_level;
import util.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collection;
import java.util.HashSet;

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
	
	public boolean positionEquals(Object o){
		if(o == this){
			return true;
		}
		if(!(o instanceof Cell)){
			return false;
		}
		return getMyLocation().equals(((Cell)o).getMyLocation());
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
	
	public abstract Collection<Cell> update(Collection<Cell>currentCells, Collection<EmptyCell>available, int size);
	
	public abstract Collection<Cell> neighbors(Collection<Cell>currentCells, int size);
	
	/**
	 * Get different types of neighbors
	 */
	
	protected Collection<Cell> getNeighbors(Collection<Cell> currentCells){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(Cell possible: currentCells){
			if(isAdjacent(possible)){
				neighbors.add(possible);
			}
		}
		return neighbors;
	}
	
	protected Collection<Cell> getWrappedNeighbors(Collection<Cell> currentCells, int size){
		ArrayList<Cell> close = new ArrayList<Cell>();
		for(Cell c: currentCells){
			if(isAnyAdjacent(c, size)){
				close.add(c);
			}
		}
		return close;
	}
	
	protected Collection<Cell> getCardinalNeighbors(Collection<Cell> currentCells){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(Cell possible: currentCells){
			if(isAdjacent(possible)){
				neighbors.add(possible);
			}
		}
		return neighbors;
	}
	
	/**
	 * Get open cells for different types of neighbors
	 */
	
	protected Collection<EmptyCell> getOpenNeighbors(Collection<Cell>curr, Collection<EmptyCell> available){
		return getOpenCells(getNeighbors(curr), available);
	}
	
	protected Collection<EmptyCell> getOpenWrappedNeighbors(Collection<Cell>curr, Collection<EmptyCell> available, int size){
		return getOpenCells(getWrappedNeighbors(curr, size), available);
	}
	
	protected Collection<EmptyCell> getOpenCardinalNeighbors(Collection<Cell>curr, Collection<EmptyCell> available){
		return getOpenCells(getCardinalNeighbors(curr), available);
	}
	
	/**
	 * Get open cells from given options
	 */
	protected Collection<EmptyCell> getOpenCells(Collection<Cell> possibleCells, Collection<EmptyCell> available){
		ArrayList<EmptyCell> close = new ArrayList<EmptyCell>();
		for(Cell c: possibleCells){
			if(c.locationIn(available) && c instanceof EmptyCell){
				close.add((EmptyCell)c);
			}
		}
		return close;
	}
	
	protected int countSameNeighbors(Collection<Cell>neighbors){
		int sameCount = 0;
		for(Cell c: neighbors){
			if(c.getMyState()!=null && c.getMyState().equals(this.getMyState())){
				sameCount++;
			}
		}
		return sameCount;
	}
	
	protected int countDiffNeighbors(Collection<Cell>neighbors){
		int diffCount = 0;
		for(Cell c: neighbors){
			if(!(c.getClass() == this.getClass())){
				diffCount++;
			}
		}
		return diffCount;
	}
	
	public void copyLocation(Cell copyFrom){
		this.setMyCol(copyFrom.getMyCol());
		this.setMyRow(copyFrom.getMyRow());
	}
	
	
	
	
	/**
	 * Adjacency in common sense
	 * @param c target cell
	 * @return true if adjacent, false otherwise
	 */
	public boolean isAdjacent(Cell c){
		return isAdjacent(c.getMyLocation());
	}
	
	
	protected boolean isAdjacent(Location l){
		return isCardinalAdjacent(l)||(oneAwayVertical(l) && oneAwayHorizontal(l));
	}
	
	/**
	 * Adjacency, but only in cardinal directions
	 */
	public boolean isCardinalAdjacent(Cell c){
		return isCardinalAdjacent(c.getMyLocation());
	}
	
	public boolean isCardinalAdjacent(Location l){
		return (sameColumn(l) && oneAwayVertical(l))||(sameRow(l) && oneAwayHorizontal(l));
	}
	
	
	/**
	 * Adjacency EVEN ACROSS BOARD (wrapped sides)
	 * @param c target cell
	 * @param size size of the board
	 * @return true if wrapped adjacent, false otherwise
	 */
	protected boolean isWrappedAdjacent(Cell c, int size){
		return isWrappedAdjacent(c.getMyLocation(), size);
	}
	
	protected boolean isWrappedAdjacent(Location l, int size){
		return (sameColumn(l) && inRowAcrossBoard(l,size)) || 
				(sameRow(l) && inColAcrossBoard(l,size));
	}
	
	/**
	 * Includes both common adjacency and wrapped adjacency
	 * @param l target cell
	 * @param size size of the board
	 * @return true if any type of adjacent, false otherwise
	 */
	protected boolean isAnyAdjacent(Location l, int size){
		return isWrappedAdjacent(l,size)|| isAdjacent(l);
	}
	
	public boolean isAnyAdjacent(Cell c, int size){
		return isWrappedAdjacent(c.getMyLocation(),size)|| isAdjacent(c.getMyLocation());
	}
	
	
	/**
	 * Check for same column/row ACROSS the board
	 */
	private boolean inColAcrossBoard(Location l, int size){
		return (l.getMyCol()==0 && getMyCol() == size-1) ||
			   (getMyCol()==0 && l.getMyCol() == size-1);
	}
	
	private boolean inRowAcrossBoard(Location l, int size){
		return (l.getMyRow()==0 && getMyRow() == size-1) ||
				   (getMyRow()==0 && l.getMyRow() == size-1);
	}
	
	
	/**
	 * Check for same column/row
	 * 
	 */
	
	private boolean sameColumn(Location l){
		return l.getMyCol() == getMyCol();
	}
	private boolean sameRow(Location l){
		return l.getMyRow() == getMyRow();
	}
	
	
	/**
	 * Check distance in directions
	 */
	private boolean oneAwayVertical(Location l){
		return oneAwayDown(l) || oneAwayUp(l);
	}
	
	private boolean oneAwayHorizontal(Location l){
		return oneAwayRight(l) || oneAwayLeft(l);
	}
	
	/**
	 * Check distance in cardinal directions
	 */
	private boolean oneAwayUp(Location l){
		return ((this.getMyRow()-1) == l.getMyRow());
	}
	
	private boolean oneAwayDown(Location l){
		return ((this.getMyRow()+1) == l.getMyRow());
	}
	
	private boolean oneAwayRight(Location l){
		return ((this.getMyCol()+1) == l.getMyCol());
	}
	
	private boolean oneAwayLeft(Location l){
		return ((this.getMyCol()-1) == l.getMyCol());
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
