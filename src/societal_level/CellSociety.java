package societal_level;
import java.util.ArrayList;
import java.util.HashSet;

import cellular_level.*;
import javafx.scene.paint.Color;

public abstract class CellSociety {
	private ArrayList<Cell> currentCells;
	private int size;
	
	public abstract Color[][] step();
	
	public Color[][] getCurrentColors(){
		Color [][] toRet = new Color[size][size];
		for(Cell c: currentCells){
			toRet[c.getMyRow()][c.getMyCol()]=c.getMyState();
		}
		return toRet;
	}
	
	public ArrayList<Cell> getCurrentCells(){
		return currentCells;
	}
	
	public void setCurrentCells(ArrayList<Cell> current){
		currentCells=current;
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
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
	
	
}
