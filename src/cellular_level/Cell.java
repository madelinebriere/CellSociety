// This entire file is part of my masterpiece.
// Madeline Briere

package cellular_level;

import util.Location;

import java.util.List;

import data_structures.CellData;
import javafx.scene.paint.Color;
import patch_level.Patch;

/**
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
 * This is an example of good design because it allows for the creation of a
 * a logical hierarchical structure in our code. It clearly defines
 * what any new Cells must implement to be used, and also provides basic
 * functions common across all Cells to avoid boiler-plate code. 
 * 
 * It also has small and well-named methods, with clear and targeted purpose
 * in this class. 
 * 
 * @author maddiebriere
 */

public abstract class Cell implements Comparable<Object> {
	public static final Color DEFAULT_COLOR = Color.WHITE;
	
	/** Cell location on grid **/
	private Location myLocation;
	
	/** Cell color, representative of state**/
	private Color myState;

	public Cell() {
		this(0, 0, DEFAULT_COLOR);
	}

	public Cell(Color c) {
		this(0, 0, c);
	}
	
	public Cell(int row, int col, Color state) {
		myLocation = new Location(row, col);
		myState = state;
	}

	/**
	 * This is the most important method for a Cell to implement --
	 * it allows for a Cell to update according to its type.
	 * The CellSociety will iterate through all of the Cells it has knowledge
	 * of and call update() and each one, but polymorphism will
	 * redirect each call to the appropriate version.
	 * 
	 * @param data A CellData object with all of the information and methods
	 * for access the Cell is allowed to use.  Allows the Cell to get 
	 * a copy of the current cells, count the number of neighbors, etc.
	 * without writing out the function in several sub-classes.
	 * 
	 * @return A List of new and/or updated Cells to add to the 
	 * next generation of Cells
	 */
	public abstract List<Cell> update(CellData data);


	/**
	 * All Cells must be able to create a copy of
	 * themselves so that when a copy of the 
	 * current Cell Map must be passed to a Cell for update,
	 * the Cells in the Map each have unique copy functions.
	 * All implementations will likely call basicCopy (which
	 * copies state and location). They will also likely add
	 * in other variables that are relevant to the
	 * specific Cell type (e.g., breed time, probability
	 * of catching fire)
	 * 
	 * @return Cell that is an exact copy of 'this'
	 */
	public abstract Cell createCopy();
	
	public void basicCopy(Cell copyFrom) {
		this.setMyState(copyFrom.getMyState());
		this.copyLocation(copyFrom);
	}
	
	/**
	 * Checks the Cell's location against an object
	 * @param o The object to compare to
	 * @return True if the location is the same, false otherwise
	 */
	public boolean positionEquals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Cell)) {
			return false;
		}
		return compareLocation((Cell)o);
	}

	/**
	 * Checks if Cells have the same location
	 * @param compare The Cell to compare to
	 * @return True if the location is the same, false otherwise
	 */
	public boolean compareLocation(Cell compare){
		Location l1 = this.getMyLocation();
		Location l2 = compare.getMyLocation();
		return l1.equals(l2);
	}
	
	/**
	 * compareTo method, implemented via Comparable
	 * and use to sort Cells
	 */
	@Override
	public int compareTo(Object o) {
		if (o == null) {
			return -1;
		}
		if (this == o) {
			return 0;
		}
		return compareTo((Cell)o);

	}
	
	/**
	 * Version of compareTo specific to Cell parameter
	 * @param c Cell to compare to
	 * @return Negative number if this is "less than" c, 0 if
	 * they are the same and a positive number if this is 
	 * "greater than" c
	 */
	public int compareTo(Cell c) {
		String name1 = this.getClass().getName();
		String name2 = c.getClass().getName();
		return name1.compareTo(name2);
	}

	/**
	 * Checks if this Cell's location is contained in
	 * the given list of Cells
	 * @param cells List of Cells in which to search
	 * @return true if the List does contain the location, false otherwise
	 */
	public <T extends Cell> boolean locationIn(List<T> cells) {
		for (Cell c : cells) {
			if (positionEquals(c)) {
				return true;
			}
		}
		return false;
	}
	
	public void copyLocation(Cell copyFrom) {
		this.copyLocation(copyFrom.getMyLocation());
	}

	public void copyLocation(Location copyFrom) {
		this.setMyCol(copyFrom.getMyCol());
		this.setMyRow(copyFrom.getMyRow());
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
		this.myLocation.setMyRow(row);
	}

	public int getMyCol() {
		return myLocation.getMyCol();
	}

	public void setMyCol(int col) {
		this.myLocation.setMyCol(col);
	}

	public Color getMyState() {
		return myState;
	}

	public void setMyState(Color myState) {
		this.myState = myState;
	}

}
