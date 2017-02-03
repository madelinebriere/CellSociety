/**
 * Class made abstract solely to keep
 * users from creating instances of the
 * superclass
 */
package societal_level;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import cellular_level.*;
import javafx.scene.paint.Color;
import util.CellData;
import util.Location;

public abstract class CellSociety {
	private static final String CELL_PACKAGE = "cellular_level.";
	
	private Collection<Cell> currentCells;
	private int size;
	private Color emptyColor;
	

	public Color[][] getCurrentColors(){
		//Catch to avoid null pointer 
		if(getEmptyColor()==null){
			setEmptyColor(Color.WHITE);
		}
		Color [][] toRet = new Color[size][size];
		for(Cell c: currentCells){
			if(!(c instanceof EmptyCell)){
				toRet[c.getMyRow()][c.getMyCol()]=c.getMyState();
			}
			else{
				toRet[c.getMyRow()][c.getMyCol()]=getEmptyColor();
			}
		}
		return toRet;
	}
	
	/**
	 * Basic step, can override in subclass
	 * @return
	 */
	public abstract Color[][] step();
	
	
	public Color [][] totalStep(){
		updateAllCells(null);
		return getCurrentColors();
	}
	
	public Color[][] guidedStep(){
		updateAllCells(new ArrayList<EmptyCell> (getAllEmptyCells()));
		return getCurrentColors();
	}
	
	public Color [][] orderedStep(Collection<String>types){
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		ArrayList<EmptyCell> emptyCells = new ArrayList<EmptyCell>(getAllEmptyCells());
		for(String s: types){
			nextGen.addAll(updateSomeCells(getCategoryCells(getCurrentCells(), s), emptyCells));
		}
		fillEmptySpots(nextGen);
		setCurrentCells(nextGen);
		return getCurrentColors();
	}
	
	protected Collection<EmptyCell> getAllEmptyCells(){
		return getEmptyCells(getCurrentCells());
	}
	
	public Collection<EmptyCell> getEmptyCells(Collection <Cell> possibleOptions){
		ArrayList <EmptyCell>toRet = new ArrayList<EmptyCell>();
		for(Cell c: possibleOptions){
			if(c instanceof EmptyCell){
				toRet.add((EmptyCell)c);
			}
		}
		return toRet;
	}
	
	
	private void updateAllCells(ArrayList<EmptyCell> available){
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(c, available));
			removeUsedSpots(available, cells);
			nextGen.addAll(cells);
		}
		fillEmptySpots(nextGen);
		setCurrentCells(nextGen);
	}
	
	private Collection<Cell> updateSomeCells(ArrayList<Cell> targetCells, ArrayList<EmptyCell>available){
		if(targetCells.size()==0){return new ArrayList<Cell>();}
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		for(Cell c: targetCells){
			ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(c, available));
			removeUsedSpots(available, cells);
			nextGen.addAll(cells);
		}
		return nextGen;
	}
	
	public void fillEmptySpots(Collection<Cell> nextGen){
		int [][] filled = new int[size][size];
		for(Cell c: nextGen){
			filled[c.getMyRow()][c.getMyCol()]+=1;
		}
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(filled[i][j]==0)
					nextGen.add(new EmptyCell(i,j));
			}
		}
	}
	
	
	private void removeUsedSpots(ArrayList<EmptyCell> available, ArrayList<Cell> newCells){
		if(available ==null || newCells ==null || available.size()==0 
				|| newCells.size()==0){return;}
		
		for(int i=0; i<available.size(); i++){
			for(int j=0; j<newCells.size(); j++){
				if(available.get(i).getMyLocation().
						equals(newCells.get(j).
						getMyLocation())){
					available.remove(i);
					if(i>0)
						i--;
					else
						break;
				}
			}
		}
	}
	
	/**
	 * NOTE: DEFAULT AVAILABLE IS NULL UNLESS YOU SPECIFY OTHERWISE
	 * 
	 * Basic update: Can override in subclass
	 * @param c
	 * @return
	 */
	private Collection<Cell> updateCell(Cell c, Collection<EmptyCell> available){
		Collection<Cell> newCells =  c.update(new CellData(this, available));
		return newCells;
	}
	
	public ArrayList getCategoryCells(Collection <Cell> currentCells, String className){
		ArrayList<Cell> toRet = new ArrayList<Cell>();
		for(Cell c: currentCells){
			if(c.getClass().getName().equals(CELL_PACKAGE + className)){
				toRet.add(c);
			}
		}
		return toRet;
	}
	
	public abstract Collection<Cell> neighbors(Cell c);
	
	/**
	 * Each CellSociety defines a certain way to identify neighbors, depending
	 * on how that society works. The generalized options are:
	 * 1) Normal neighbors (8 nearby cells, no board crossing)
	 * 2) Cardinal neighbors (N S W E cells)
	 * 3) Wrapped neighbors (8 nearby cells, board crossing)
	 * Descendant classes can combine these when they define neighbors or come
	 * up with a new way to choose neighbors
	 * 
	 * @param c
	 * @return
	 */
	protected Collection<Cell> getNeighbors(Cell c){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(Cell possible: currentCells){
			if(isAdjacent(c, possible)){
				neighbors.add(possible);
			}
		}
		return neighbors;
	}
	
	protected Collection<Cell> getWrappedNeighbors(Cell c){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(Cell possible: currentCells){
			if(isAnyAdjacent(c, possible)){
				neighbors.add(possible);
			}
		}
		return neighbors;
	}
	
	protected Collection<Cell> getCardinalNeighbors(Cell c){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(Cell possible: currentCells){
			if(isCardinalAdjacent(c, possible)){
				neighbors.add(possible);
			}
		}
		return neighbors;
	}
	
	
	/**
	 * Adjacency in common sense
	 * @param c target cell
	 * @return true if adjacent, false otherwise
	 */
	public boolean isAdjacent(Cell c1, Cell c2){
		return isAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}
	
	
	protected boolean isAdjacent(Location l1, Location l2){
		return isCardinalAdjacent(l1, l2)||
				(oneAwayVertical(l1, l2) && oneAwayHorizontal(l1, l2));
	}
	
	/**
	 * Adjacency, but only in cardinal directions
	 */
	public boolean isCardinalAdjacent(Cell c1, Cell c2){
		return isCardinalAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}
	
	public boolean isCardinalAdjacent(Location l1, Location l2){
		return (sameColumn(l1, l2) && oneAwayVertical(l1, l2))||(sameRow(l1, l2) && oneAwayHorizontal(l1, l2));
	}
	
	
	/**
	 * Adjacency EVEN ACROSS BOARD (wrapped sides)
	 * @param c target cell
	 * @param size size of the board
	 * @return true if wrapped adjacent, false otherwise
	 */
	protected boolean isWrappedAdjacent(Cell c1, Cell c2){
		return isWrappedAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}
	
	protected boolean isWrappedAdjacent(Location l1, Location l2){
		return (sameColumn(l1, l2) && inRowAcrossBoard(l1, l2)) || 
				(sameRow(l1, l2) && inColAcrossBoard(l1, l2));
	}
	
	/**
	 * Includes both common adjacency and wrapped adjacency
	 * @param l target cell
	 * @param size size of the board
	 * @return true if any type of adjacent, false otherwise
	 */
	protected boolean isAnyAdjacent(Location l1, Location l2){
		return isWrappedAdjacent(l1, l2)|| isAdjacent(l1, l2);
	}
	
	public boolean isAnyAdjacent(Cell c1, Cell c2){
		return isAnyAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}
	
	
	/**
	 * Check for same column/row ACROSS the board
	 */
	private boolean inColAcrossBoard(Location l1, Location l2){
		return (l1.getMyCol()==0 && l2.getMyCol() == size-1) ||
			   (l1.getMyCol()==0 && l2.getMyCol() == size-1);
	}
	
	private boolean inRowAcrossBoard(Location l1, Location l2){
		return (l1.getMyRow()==0 && l2.getMyRow() == size-1) ||
				   (l1.getMyRow()==0 && l2.getMyRow() == size-1);
	}
	
	
	/**
	 * Check for same column/row
	 * 
	 */
	
	private boolean sameColumn(Location l1, Location l2){
		return l2.getMyCol() == l1.getMyCol();
	}
	private boolean sameRow(Location l1, Location l2){
		return l2.getMyRow() == l1.getMyRow();
	}
	
	
	/**
	 * Check distance in directions
	 */
	private boolean oneAwayVertical(Location l1, Location l2){
		return oneAwayDown(l1, l2) || oneAwayUp(l1, l2);
	}
	
	private boolean oneAwayHorizontal(Location l1, Location l2){
		return oneAwayRight(l1, l2) || oneAwayLeft(l1, l2);
	}
	
	/**
	 * Check distance in cardinal directions
	 */
	private boolean oneAwayUp(Location l1, Location l2){
		return ((l1.getMyRow()-1) == l2.getMyRow());
	}
	
	private boolean oneAwayDown(Location l1, Location l2){
		return ((l1.getMyRow()+1) == l2.getMyRow());
	}
	
	private boolean oneAwayRight(Location l1, Location l2){
		return ((l1.getMyCol()+1) == l2.getMyCol());
	}
	
	private boolean oneAwayLeft(Location l1, Location l2){
		return ((l1.getMyCol()-1) == l2.getMyCol());
	}
	
	
	public Collection<Cell> getCurrentCells(){
		return currentCells;
	}
	
	public void setCurrentCells(Collection<Cell> current){
		currentCells=current;
	}
	
	public Color getEmptyColor() {
		return emptyColor;
	}

	public void setEmptyColor(Color emptyColor) {
		this.emptyColor = emptyColor;
	}
	
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
