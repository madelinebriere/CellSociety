package borders;

import cellular_level.Cell;
import data_structures.Dimensions;

public class InfiniteBorder extends Border{
	
	
	public InfiniteBorder(Dimensions d) {
		super(d);
	}

	@Override
	public boolean isAdjacent(Cell c1, Cell c2) {
		// TODO Auto-generated method stub
		return false;
	}

}
