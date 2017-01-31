package societal_level;

import java.util.ArrayList;
import java.util.Random;

import cellular_level.*;
import javafx.scene.paint.Color;

public class LifeSociety extends CellSociety {

	/**
	 * Need to instantiate
	 */
	public LifeSociety(){
		setSize(10);
		Random rnd = new Random();
		ArrayList<Cell>makeCells = new ArrayList<Cell>();
		for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
					makeCells.add( rnd.nextBoolean()? new LiveCell(i,j) : new DeadCell(i,j));
				
			}
		}
		setCurrentCells(makeCells);
	}
	
	@Override
	public Color[][] step() {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			nextGen.addAll(updateCell(c));
		}
		setCurrentCells(nextGen);
		return getCurrentColors();
	}
	
	private ArrayList<Cell> updateCell(Cell c){
		ArrayList<Cell> neighbors = getFirstNeighbors(c);
		ArrayList<EmptyCell> emptyCells = getEmptyCells();
		int size = getSize();
		ArrayList<Cell> newCells =  c.update(neighbors, emptyCells, size);
		return newCells;
	}

}
