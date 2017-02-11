package neighbors;

import java.util.ArrayList;
import java.util.List;
import util.Tuple;

import borders.Border;
import cellular_level.Cell;
import data_structures.BorderType;
import data_structures.Dimensions;
import patch_level.Patch;

public class SquareNeighbors extends Neighbors {


	public SquareNeighbors(Patch[][] cells, BorderType b) {
		super(cells, b);
	}

	@Override
	public List<Patch> getAllNeighbors(Patch c) {
		List<Patch> neighbors = new ArrayList<Patch>();
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
		System.out.println(neighbors.size());
		return neighbors;
	}

	@Override
	public List<Patch> getCardinalNeighbors(Patch c) {
		
		List<Patch> neighbors = new ArrayList<Patch>();
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
		System.out.println(neighbors.size());
		return neighbors;
	}


}
