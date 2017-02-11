package borders;

import cellular_level.Cell;
import data_structures.Dimensions;

public class FiniteBorder extends Border {

	public FiniteBorder(Dimensions d) {
		super(d);
	}

	@Override
	public boolean isAdjacent(Cell c1, Cell c2) {
		// TODO Auto-generated method stub
		return false;
	}

}
