package neighbors;

import java.util.List;

import borders.Border;
import cellular_level.Cell;
import data_structures.BorderType;
import data_structures.Dimensions;
import patch_level.Patch;

public class TriangleNeighbors extends Neighbors {

	public TriangleNeighbors(Patch[][] cells, BorderType b) {
		super(cells, b);
	}


	@Override
	public List<Patch> getAllNeighbors(Patch c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Patch> getCardinalNeighbors(Patch c) {
		// TODO Auto-generated method stub
		return null;
	}

}
