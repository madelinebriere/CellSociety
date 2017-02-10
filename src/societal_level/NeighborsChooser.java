package societal_level;

import borders.Border;
import data_structures.CellShape;
import data_structures.SimulationData;
import neighbors.*;

public class NeighborsChooser {
	public static Neighbors chooseNeighbors(Border b, CellShape s){
		Neighbors neighbors = new SquareNeighbors(b); //Default
		switch (s){
			case TRIANGLE:
				neighbors = new TriangleNeighbors(b);
				break;
			case HEXAGON:
				neighbors = new HexagonNeighbors(b);
				break;
		}
		return neighbors;	
	}
	
}
