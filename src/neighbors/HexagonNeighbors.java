//author Talha Koc
package neighbors;
import java.util.ArrayList;
import java.util.List;

import borders.Border;
import cellular_level.Cell;
import data_structures.BorderType;
import data_structures.Dimensions;
import patch_level.Patch;
import util.Tuple;

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
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x,y-2)){//TOP hexagon 
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x, y-2);
			neighbors.add(myGrid[t.x][t.y]);
		}
		if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x, y+2)){ //bottom hexagon
			Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x, y+2);
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
		if(y%2==0){ // even rowed hexagons have different relations than odd numbered ones
			if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x-1, y+1)){
				Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x-1, y+1);
				neighbors.add(myGrid[t.x][t.y]);
			}
			if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x-1, y-1)){
				Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x-1, y-1);
				neighbors.add(myGrid[t.x][t.y]);
			}
		}else{
			if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x+1, y+1)){
				Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x+1, y+1);
				neighbors.add(myGrid[t.x][t.y]);
			}
			if(myBorderType == BorderType.TOROIDAL || isValidCoordinate(x+1, y-1)){
				Tuple<Integer, Integer> t = getCoordinateWithWrapCheck(x+1, y-1);
				neighbors.add(myGrid[t.x][t.y]);
			}
		}
		
		return neighbors;
	}

}
