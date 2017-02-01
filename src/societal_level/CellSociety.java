/**
 * Class made abstract solely to keep
 * users from creating instances of the
 * superclass
 */
package societal_level;
import java.util.ArrayList;
import java.util.HashSet;

import cellular_level.*;
import javafx.scene.paint.Color;

public abstract class CellSociety {
	private ArrayList<Cell> currentCells;
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
	
	public void printCurrentColors(){
		Color[][] curr = getCurrentColors();
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				System.out.print(curr[i][j].toString()+ "  ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Basic step, can override in subclass
	 * @return
	 */
	public Color[][] step() {
		updateAllCells();
		return getCurrentColors();
	}
	
	public void updateAllCells(){
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			nextGen.addAll(updateCell(c));
			System.out.println("HERE " + c.getMyState());
		}
		setCurrentCells(nextGen);
	}
	
	/**
	 * Basic update: Can override in subclass
	 * @param c
	 * @return
	 */
	private ArrayList<Cell> updateCell(Cell c){
		ArrayList<Cell> neighbors = getFirstNeighbors(c);
		ArrayList<EmptyCell> emptyCells = getEmptyCells();
		int size = getSize();
		ArrayList<Cell> newCells =  c.update(neighbors, emptyCells, size);
		return newCells;
	}
	
	
	public ArrayList<EmptyCell> getEmptyCells(){
		ArrayList <EmptyCell>toRet = new ArrayList<EmptyCell>();
		for(Cell c: currentCells){
			if(c instanceof EmptyCell){
				toRet.add((EmptyCell)c);
			}
			
		}
		return toRet;
	}
	
	public ArrayList<Cell> getFirstNeighbors(Cell c){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(Cell possible: currentCells){
			if(possible.isAdjacent(c)){
				neighbors.add(possible);
			}
		}
		return neighbors;
	}
	
	public ArrayList<Cell> getWrappedFirstNeighbors(Cell c){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(Cell possible: currentCells){
			if(possible.isAnyAdjacent(c, size)){
				neighbors.add(possible);
			}
			
		}
		return neighbors;
	}


	public ArrayList<Cell>getSecondNeighbors(Cell c){
		HashSet <Cell> neighborhood = new HashSet<Cell>();
		ArrayList<Cell> firstOrderNeighbors = getFirstNeighbors(c);
		neighborhood.addAll(firstOrderNeighbors);
		for(Cell n: firstOrderNeighbors){
			ArrayList<Cell> secondOrderNeighbors = getFirstNeighbors(n);
			neighborhood.addAll(secondOrderNeighbors);
		}
		ArrayList<Cell> toRet = new ArrayList<Cell>(neighborhood);
		return toRet;
	}
	
	public ArrayList<Cell>getSecondWrappedNeighbors(Cell c){
		HashSet <Cell> neighborhood = new HashSet<Cell>();
		ArrayList<Cell> firstOrderNeighbors = getWrappedFirstNeighbors(c);
		neighborhood.addAll(firstOrderNeighbors);
		for(Cell n: firstOrderNeighbors){
			ArrayList<Cell> secondOrderNeighbors = getWrappedFirstNeighbors(n);
			neighborhood.addAll(secondOrderNeighbors);
		}
		ArrayList<Cell> toRet = new ArrayList<Cell>(neighborhood);
		return toRet;
	}
	
	public ArrayList<Cell> getCurrentCells(){
		return currentCells;
	}
	
	public void setCurrentCells(ArrayList<Cell> current){
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
