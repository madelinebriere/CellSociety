package data_structures;



public class SimulationData {
	private Dimensions dimensions;
	private SimulationName name;
	private CellRatio ratio;
	private CellShape shape;
	
	public SimulationData(Dimensions d, SimulationName id, CellRatio r, CellShape s){
		dimensions = d;
		name = id;
		ratio = r;
		shape = s;
	}
	public CellShape getShape(){
		return shape;
	}
	public Dimensions getDimensions(){
		return dimensions;
	}
	public CellRatio getRatio(){
		return ratio;
	}
	public SimulationName getSimulationName(){
		return name;
	}
}

