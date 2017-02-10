package societal_level;

import data_structures.SimulationData;
import neighbors.*;

public class NeighborsChooser {
	public static Neighbors chooseNeighbors(SimulationData sim){
		Neighbors neighbors = new SquareNeighbors(); //Default
		switch (sim.getShape()){
			case TRIANGLE:
				neighbors = new TriangleNeighbors();
				break;
			case HEXAGON:
				neighbors = new HexagonNeighbors();
				break;
		}
		return neighbors;	
	}
	
}
