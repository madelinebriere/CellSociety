 package neighbors;

import java.util.List;

import borders.Border;
import cellular_level.Cell;
import data_structures.BorderType;
import data_structures.Dimensions;
import patch_level.Patch;
import util.Tuple;

/**
 * Class to define neighbors/ boundaries between neighbors, adjacency, etc.
 * @author maddiebriere
 *
 */

public abstract class Neighbors {
	protected Patch[][] myGrid;
	protected BorderType myBorderType;
	
	public Neighbors(Patch[][] patchGrid, BorderType b){
		myGrid = patchGrid;
		myBorderType= b;
	}
	
	public abstract List<Patch> getAllNeighbors(Patch c);
	public abstract List<Patch> getCardinalNeighbors(Patch c);
	

	protected boolean isValidCoordinate(int x, int y){
		return (x>=0 && x<myGrid.length) && (y>=0 && y<myGrid.length);
	}
	protected Tuple<Integer, Integer> getCoordinateWithWrapCheck(int x, int y){
		if (x < 0) x = myGrid.length;
		else if (x >= myGrid.length) x = 0;	
		if (y < 0) y = myGrid.length;
		else if (y >= myGrid.length) y = 0;
		return new Tuple<Integer, Integer>(x,y);
	}	
}
