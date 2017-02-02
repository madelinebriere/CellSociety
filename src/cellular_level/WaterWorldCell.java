package cellular_level;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * Add wrapped,
 * delete duplicate code
 * 
 * 
 * @author maddiebriere
 *
 */
public abstract class WaterWorldCell extends Cell {
	private Random randy = new Random();
	
	public WaterWorldCell(){
		super();
	}
	
	public WaterWorldCell(int row, int col, Color color){
		super(row, col, color);
	}
	
	protected ArrayList<Cell> getFirstOrderNeighbors(ArrayList<Cell> nearbyCells, int size){
		ArrayList<Cell> close = new ArrayList<Cell>();
		for(Cell c: nearbyCells){
			if(isAnyAdjacent(c, size)){
				close.add(c);
			}
		}
		return close;
	}
	
	protected ArrayList<EmptyCell> getFirstOrderOpenNeighbors(ArrayList<Cell>nearbyCells, int size){
		ArrayList<EmptyCell> close = new ArrayList<EmptyCell>();
		for(Cell c: nearbyCells){
			if(isAnyAdjacent(c, size) && c instanceof EmptyCell){
				close.add((EmptyCell)c);
			}
		}
		return close;
	}
	
	protected ArrayList<Cell> getOpenCells(ArrayList<Cell> neighbors){
		ArrayList<Cell> toRet = new ArrayList<Cell>();
		for(Cell c: neighbors){
			if(c instanceof EmptyCell){
				toRet.add(c);
			}
		}
		return toRet;
	}
	
	
	protected EmptyCell getBreedSpot(ArrayList<Cell> nearbyCells, int size){
		ArrayList<EmptyCell> possibleBreedSpots = getFirstOrderOpenNeighbors(nearbyCells,size);
		if(possibleBreedSpots!=null && possibleBreedSpots.size()!=0){
		int index = randy.nextInt(possibleBreedSpots.size());
		EmptyCell breedSpot = possibleBreedSpots.get(index);
		return breedSpot;
		}
		else
			return null;
	}
	
	
	protected void move(ArrayList<Cell> neighbors){
		ArrayList<Cell> openSpots = getOpenCells(neighbors);
		if(openSpots.size()!=0){
			int index = randy.nextInt(openSpots.size());
			this.copyLocation(openSpots.get(index));
		}
	}
	
}
