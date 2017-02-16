// This entire file is part of my masterpiece.
// Madeline Briere

package cellular_level;

import java.util.ArrayList;
import java.util.List;

import data_structures.CellData;

/**
 * This class was added to avoid passing null
 * objects to represent empty Cells/ non-existent Cells.
 * 
 * In the current design, many of the methods will return null if 
 * nothing is created/ found in the method. This could cause problems for 
 * someone else just starting to work with the project.
 * 
 * Hence, this new class could be used to convey the same
 * message without creating fatal errors within the program.
 * 
 * This follows the design pattern of the Null Object Pattern, which
 * creates an object used solely to do nothing (surprisingly useful)
 * 
 * @author maddiebriere
 */
public class NullCell extends Cell{

	@Override
	public Cell createCopy() {
		NullCell copy = new NullCell();
		copy.basicCopy(this);
		return copy;
	}

	@Override
	public List<Cell> update(CellData data) {
		ArrayList<Cell> newCells = new ArrayList<Cell>();
		return newCells;
	}
	
}
