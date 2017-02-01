/**
 * 
 * Bug with current version:
 * Can't get people to stay
 * on screen
 * 
 * Some sort of overriding happening
 * 
 */


package societal_level;

import java.util.ArrayList;
import java.util.Random;

import cellular_level.*;
import javafx.scene.paint.Color;


public class PopSociety extends CellSociety {
	private Random rnd = new Random();
	
	public PopSociety(){
		setSize(10);
		setEmptyColor(Color.LIGHTBLUE);
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				if(rnd.nextBoolean()){
					makeCells.add(rnd.nextBoolean()? 
							new HouseCell(i,j, Color.BLUE): new HouseCell(i,j,Color.RED));
				}
				else
					makeCells.add(new EmptyCell(i,j));
			}
		}
		setCurrentCells(makeCells);
	}
	
	@Override
	public Color[][] step(){
		updateAllCells();
		//resolveConflicts();
		return getCurrentColors();
	}

	public void resolveConflicts(){
		ArrayList<Cell> oldCells = new ArrayList<Cell>(); //avoid concurrent modification
		for(Cell c1: getCurrentCells()){
			for(Cell c2: getCurrentCells()){
				if(c1.positionEquals(c2)){//same location
					handleConflict(c1, c2, oldCells);
				}
			}
		}
		for(Cell c: oldCells){
			getCurrentCells().remove(c);
		}
	}
	
	public void handleConflict(Cell c1, Cell c2, ArrayList<Cell> oldCells){
		if(((c1 instanceof EmptyCell) && (c2 instanceof HouseCell))){
			oldCells.add(c1);
			return;
		}
		if(((c2 instanceof EmptyCell) && (c1 instanceof HouseCell))){
			oldCells.add(c2);
			return;
		}
		if(c1 instanceof HouseCell && c2 instanceof HouseCell){
			Cell toRemove = rnd.nextBoolean()? c1:c2;
			relocate((HouseCell)toRemove, oldCells);
		}
	}
	
	public void relocate(HouseCell cell, ArrayList<Cell>oldCells){
		ArrayList <EmptyCell> emptySpots = getEmptyCells();
		int index = rnd.nextInt(emptySpots.size());
		EmptyCell toReplace = emptySpots.get(index);
		oldCells.add(toReplace);
		cell.copyLocation(toReplace);
	}
}
