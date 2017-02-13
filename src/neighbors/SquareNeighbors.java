package neighbors;

import java.util.ArrayList;
import java.util.List;
import util.Tuple;

import data_structures.BorderType;
import patch_level.Patch;

public class SquareNeighbors extends Neighbors {

	public SquareNeighbors(Patch[][] cells, BorderType b) {
		super(cells, b);
	}

	@Override
	public List<Patch> getAllNeighbors(Patch c) {
		List<Patch> neighbors = new ArrayList<Patch>();
		int x = c.getMyLocation().getMyCol();
		int y = c.getMyLocation().getMyRow();
		neighbors.addAll(getCardinalNeighbors(c));
		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x - 1, y - 1)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x - 1, y - 1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x - 1, y + 1)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x - 1, y + 1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x + 1, y - 1)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x + 1, y - 1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x + 1, y + 1)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x + 1, y + 1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		return neighbors;
	}

	@Override
	public List<Patch> getCardinalNeighbors(Patch c) {

		List<Patch> neighbors = new ArrayList<Patch>();
		int x = c.getMyLocation().getMyCol();
		int y = c.getMyLocation().getMyRow();

		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x - 1, y)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x - 1, y);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x + 1, y)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x + 1, y);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x, y - 1)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x, y - 1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if (myBorderType == BorderType.TOROIDAL || isValidCoordinate(x, y + 1)) {
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x, y + 1);
			neighbors.add(myGrid[t.x][t.y]);
		}
		return neighbors;
	}

}
