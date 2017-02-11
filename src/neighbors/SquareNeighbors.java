package neighbors;

import java.util.ArrayList;
import java.util.List;
import util.Tuple;

import borders.Border;
import cellular_level.Cell;
import data_structures.BorderType;
import data_structures.Dimensions;

public class SquareNeighbors extends Neighbors {


	public SquareNeighbors(Cell[][] cells, BorderType b) {
		super(cells, b);
	}

	@Override
	public List<Cell> getAllNeighbors(Cell c) {
		
		List<Cell> neighbors = new ArrayList<Cell>();
		int x = c.getMyLocation().getMyRow();
		int y = c.getMyLocation().getMyCol();
		neighbors.addAll(getCardinalNeighbors(c));
		
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x-1, y-1)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x-1, y-1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x-1, y+1)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x-1, y+1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x+1, y-1)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x+1, y-1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x+1, y+1)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x+1, y+1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		return neighbors;
	}

	@Override
	public List<Cell> getCardinalNeighbors(Cell c) {
		
		List<Cell> neighbors = new ArrayList<Cell>();
		int x = c.getMyLocation().getMyRow();
		int y = c.getMyLocation().getMyCol();
		
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x-1, y)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x-1, y);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x+1, y)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x+1, y);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x, y-1)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x, y-1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x, y+1)){
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x, y+1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		return neighbors;
	}


}
