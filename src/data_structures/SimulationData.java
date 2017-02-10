//author Talha Koc

package data_structures;

import javafx.scene.paint.Color;

public class SimulationData {
	private Dimensions dimensions;
	private SimulationName name;
	private CellRatioMap ratios;
	private CellShape shape;
	private boolean getAllNeighbors;
	private Color emptyCellColor;
	
	public SimulationData(Dimensions d, SimulationName id, CellRatioMap r, CellShape s, boolean b, Color emptyColor){
		dimensions = d;
		name = id;
		ratios = r;
		shape = s;
		getAllNeighbors = b;
		emptyCellColor = emptyColor;
	}
	public CellShape getShape(){
		return shape;
	}
	public Dimensions getDimensions(){
		return dimensions;
	}
	public CellRatioMap getRatio(){
		return ratios;
	}
	public SimulationName getSimulationName(){
		return name;
	}
}

