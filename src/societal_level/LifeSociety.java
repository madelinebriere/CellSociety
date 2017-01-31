package societal_level;

import java.util.ArrayList;

import cellular_level.*;

public class LifeSociety extends CellSociety {

	/**
	 * Need to instantiate
	 */
	public LifeSociety(){
		setSize(10);
		ArrayList<Cell>makeCells = new ArrayList<Cell>();
		for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				makeCells.add(new LiveCell(i,j));
			}
		}
		setCurrentCells(makeCells);
	}
	
	@Override
	public ArrayList<Cell> step() {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			nextGen.addAll(updateCell(c));
		}
		return nextGen;
	}
	
	private ArrayList<Cell> updateCell(Cell c){
		ArrayList<Cell> neighbors = getFirstNeighbors(c);
		ArrayList<EmptyCell> emptyCells = getEmptyCells();
		int size = getSize();
		ArrayList<Cell> newCells =  c.update(neighbors, emptyCells, size);
		return newCells;
	}

}
