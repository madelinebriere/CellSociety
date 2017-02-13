package util;

import data_structures.BorderType;
import data_structures.CellShape;
import neighbors.*;
import patch_level.Patch;

/**
 * Chooses the correct neighbors object based on the CellShape and the border
 * type
 * 
 * @author maddiebriere
 *
 */

public class NeighborsChooser {
	public static Neighbors chooseNeighbors(BorderType b, CellShape s, Patch[][] grid) {
		Neighbors neighbors;
		switch (s) {
		case TRIANGLE:
			neighbors = new TriangleNeighbors(grid, b);
			break;
		case HEXAGON:
			neighbors = new HexagonNeighbors(grid, b);
			break;
		default:
			neighbors = new SquareNeighbors(grid, b);
			break;
		}
		return neighbors;
	}

}
