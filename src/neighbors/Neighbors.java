 package neighbors;

import java.util.List;


import data_structures.BorderType;
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
	
	
	protected Patch getPatchAtXY(int x, int y){
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x,y)){//TOP hexagon 
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x, y);
			return (myGrid[t.x][t.y]);
		}
		return null;
	}
	protected boolean isValidCoordinate(int x, int y){
		return (x>=0 && x<myGrid.length) && (y>=0 && y<myGrid[0].length);
	}
	protected Tuple<Integer, Integer> getCoordinateWithWrapCheck(int x, int y){
		if (x < 0) x = myGrid.length - 1;
		else if (x >= myGrid.length) x = 0;	
		if (y < 0) y = myGrid[0].length - 1;
		else if (y >= myGrid[0].length) y = 0;
		return new Tuple<Integer, Integer>(x,y);
	}	
}
