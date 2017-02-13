//author Talha Koc
package neighbors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data_structures.BorderType;
import patch_level.Patch;

/**
 * Intended use: provide cells with a list of neighbors
 * 
 * Customized for hexagonal relationship between cells
 * 
 * @author talha koc
 *
 */
public class HexagonNeighbors extends Neighbors{

	public HexagonNeighbors(Patch[][] cells, BorderType b) {
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
		neighbors.add(getPatchAtXY(x, y-2));
		neighbors.add(getPatchAtXY(x, y+2));
		neighbors.add(getPatchAtXY(x, y-1));
		neighbors.add(getPatchAtXY(x, y+1));
		if(y%2==0){ // even rowed hexagons have different relations than odd numbered ones
			neighbors.add(getPatchAtXY(x-1, y+1));
			neighbors.add(getPatchAtXY(x-1, y-1));
		}else{
			neighbors.add(getPatchAtXY(x+1, y+1));
			neighbors.add(getPatchAtXY(x+1, y-1));
		}
		neighbors.removeAll(Collections.singleton(null));
		return neighbors;
	}

}