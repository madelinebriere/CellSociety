package borders;

import cellular_level.*;
import data_structures.Dimensions;
/**
 * Class to define what happens at the edge of the board
 * @author maddiebriere
 *
 */

public abstract class Border {
	private Dimensions myDimensions;
	
	public Border(Dimensions d){
		myDimensions=d;
	}
	
	public abstract boolean isAdjacent(Cell c1, Cell c2);

	public Dimensions getMyDimensions() {
		return myDimensions;
	}

	public void setMyDimensions(Dimensions myDimensions) {
		this.myDimensions = myDimensions;
	}
	
	
}
