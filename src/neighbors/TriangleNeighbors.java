//Author Talha Koc
package neighbors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import data_structures.BorderType;
import patch_level.Patch;

public class TriangleNeighbors extends Neighbors {

	public TriangleNeighbors(Patch[][] cells, BorderType b) {
		super(cells, b);
	}


	@Override
	public List<Patch> getAllNeighbors(Patch c) {
		return getCardinalNeighbors(c);
	}


	@Override
	public List<Patch> getCardinalNeighbors(Patch c) {
		List<Patch> neighbors = new ArrayList<Patch>();
		int x = c.getMyLocation().getMyCol();
		int y = c.getMyLocation().getMyRow();
		neighbors.add(getPatchAtXY(x-1, y));
		neighbors.add(getPatchAtXY(x+1, y));
		if (x%2 == 0){
			neighbors.add(getPatchAtXY(x+1, y-1));
		}else{
			neighbors.add(getPatchAtXY(x-1, y+1));
		}
		neighbors.removeAll(Collections.singleton(null));
		return neighbors;
	}

}
