package util;

import borders.Border;
import cellular_level.Cell;
import data_structures.BorderType;
import data_structures.CellShape;
import data_structures.SimulationData;
import neighbors.*;

public class NeighborsChooser {
	public static Neighbors chooseNeighbors(BorderType b, CellShape s, Cell[][] grid){
		Neighbors neighbors;
		switch (s){
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
