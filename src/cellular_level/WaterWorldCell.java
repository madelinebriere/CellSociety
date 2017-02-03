package cellular_level;

import java.util.ArrayList;
import java.util.Collection;
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
	
	protected EmptyCell getBreedSpot(Collection<Cell> nearbyCells, Collection<EmptyCell> available, int size){
		Collection<Cell> neighbors = neighbors(nearbyCells, size);
		Collection<EmptyCell> possibleBreedSpots = getOpenCells(neighbors, available);
		if(possibleBreedSpots!=null && possibleBreedSpots.size()!=0){
		int index = randy.nextInt(possibleBreedSpots.size());
		EmptyCell breedSpot = (new ArrayList<EmptyCell>(possibleBreedSpots)).get(index);
		return breedSpot;
		}
		else
			return null;
	}
	
	
	protected void move(Collection<Cell> neighbors, Collection<EmptyCell> available, int size){
		Collection<EmptyCell> openSpots = getOpenWrappedNeighbors(neighbors, available, size);
		if(openSpots.size()!=0){
			int index = randy.nextInt(openSpots.size());
			this.copyLocation(new ArrayList<Cell>(openSpots).get(index));
		}
	}
	
}
