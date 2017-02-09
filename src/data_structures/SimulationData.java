package data_structures;



public class SimulationData {
	private Dimensions dimensions;
	private SimulationName name;
	private CellRatioMap ratios;
	private CellShape shape;
	
	public SimulationData(Dimensions d, SimulationName id, CellRatioMap r, CellShape s){
		dimensions = d;
		name = id;
		ratios = r;
		shape = s;
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

