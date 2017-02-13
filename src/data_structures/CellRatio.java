//author Talha Koc
//Just an object that holds an integer with value greater than zero and less than or equal to 1

package data_structures;

/**
 * intended use: define ratio of cells to other cells in a grid 
 * 
 * Simply holds a double value that is between 0 and 1
 * @author talha koc
 *
 */
public class CellRatio {
	private double r;

	public CellRatio(double r) {
		if (r > 0 && r <= 1) {
			this.r = r;
		} else {
			// TODO: error handling,
		}
	}

	public double getRatio() {
		return r;
	}
}
