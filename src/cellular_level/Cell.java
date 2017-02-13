
package cellular_level;

import util.Location;

import java.util.List;

import data_structures.CellData;
import javafx.scene.paint.Color;
import patch_level.Patch;

/**
 * 
 * This abstract class defines the framework for all of the Cells used in the
 * simulations. Every cell has a location and a state defined by color.
 * 
 * This class provides general functions like copying and basic updates.
 * 
 * Note that the Cell class also extends Comparable because the Cells in
 * CellSociety are held in a sorted order (in case certain types of Cells need
 * to be updated before others, like Sharks before Fish). Comparable allows for
 * unique definition of this sorting.
 * 
 * @author maddiebriere
 */

public abstract class Cell implements Comparable {
	private Location myLocation;
	private Color myState;

	public Cell() {
		this(0, 0, Color.WHITE);
	}

	public Cell(Color c) {
		this(0, 0, c);
	}

	public Cell(int row, int col, Color state) {
		myLocation = new Location(row, col);
		myState = state;
	}

	public boolean positionEquals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Cell)) {
			return false;
		}
		return getMyLocation().equals(((Cell) o).getMyLocation());
	}

	/**
	 * This compares by Class name, so sorts alphabetically. This could
	 * potentially be modified if the need were to arise
	 */
	@Override
	public int compareTo(Object o) {
		if (o == null) {
			return -1;
		}
		if (this == o) {
			return 0;
		}
		return this.getClass().getName().compareTo(o.getClass().getName());

	}

	/**
	 * Checks if a list of Cells contains the location of this Cell
	 * 
	 * @return boolean true if the Cell's location is in the List
	 */
	public <T extends Cell> boolean locationIn(List<T> cells) {
		for (Cell c : cells) {
			if (positionEquals(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method must be defined by every cell so that Cells can be copied in
	 * the CellData class. This allows for an entire structure of Cells to be
	 * copied without fear of passing a changeable Cell (which could potentially
	 * wreak havoc on a simulation)
	 * 
	 * @return
	 */
	public abstract Cell createCopy();

	/**
	 * This is the main method of Cell -- this is the method that updates the
	 * state of the cell according to its own variables, prior state and
	 * surroundings. It is abstract to allow subclasses to fill in the
	 * implementation.
	 * 
	 * @param data
	 *            CellData object holding information relevant to the Cell as it
	 *            is updating
	 * 
	 * @return a List of Cells that have been updated -- these Cells will be
	 *         added to an entirely new list of current cells
	 */
	public abstract List<Cell> update(CellData data);

	public void copyLocation(Cell copyFrom) {
		this.setMyCol(copyFrom.getMyCol());
		this.setMyRow(copyFrom.getMyRow());
	}

	public void copyLocation(Location copyFrom) {
		this.setMyCol(copyFrom.getMyCol());
		this.setMyRow(copyFrom.getMyRow());
	}

	/**
	 * This is called by almost all Cells in the createCopy function -- it
	 * copies the basic elements of a Cell
	 * 
	 * @param copyFrom
	 *            The cell to copy information from
	 */
	public void basicCopy(Cell copyFrom) {
		this.setMyState(copyFrom.getMyState());
		this.copyLocation(copyFrom);
	}

	public double getDistance(Patch patch) {
		return patch.getMyLocation().distance(this.getMyLocation());
	}

	public Location getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	public int getMyRow() {
		return myLocation.getMyRow();
	}

	public void setMyRow(int row) {
		myLocation.setMyRow(row);
	}

	public int getMyCol() {
		return myLocation.getMyCol();
	}

	public void setMyCol(int col) {
		myLocation.setMyCol(col);
	}

	public Color getMyState() {
		return myState;
	}

	public void setMyState(Color myState) {
		this.myState = myState;
	}

}
