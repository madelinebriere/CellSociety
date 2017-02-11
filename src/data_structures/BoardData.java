package data_structures;

/**
 * 
 * STONE USE THIS :)
 * 
 * Information held in SimulationData about board set-up, including dimensions, type of border
 * and shape of tiles
 * 
 * @author maddiebriere
 */

public class BoardData {
	private static final SimulationName DEFAULT_SIM = SimulationName.GAME_OF_LIFE;
	private static final CellShape DEFAULT_SHAPE = CellShape.SQUARE;
	private static final BorderType DEFAULT_BORDER = BorderType.FINITE;
	private static final Dimensions DEFAULT_SIZE = new Dimensions(10,10);
	
	private boolean getAllNeighbors;
	private SimulationName name;
	private Dimensions dimensions;
	private BorderType border;
	private CellShape shape;
	
	public BoardData(){
		this(DEFAULT_SIM);
	}
	
	public BoardData(SimulationName name){
		this(name, DEFAULT_SIZE, DEFAULT_BORDER, DEFAULT_SHAPE);
	}

	public BoardData(SimulationName name, Dimensions dimensions, BorderType border, CellShape shape) {
		this(false, name, dimensions, border, shape);
	}


	public BoardData(boolean getAllNeighbors, SimulationName name, Dimensions dimensions,
			BorderType border, CellShape shape) {
		super();
		this.getAllNeighbors = getAllNeighbors;
		this.name = name;
		this.dimensions = dimensions;
		this.border = border;
		this.shape = shape;
	}

	public boolean isGetAllNeighbors() {
		return getAllNeighbors;
	}

	public void setGetAllNeighbors(boolean getAllNeighbors) {
		this.getAllNeighbors = getAllNeighbors;
	}

	public void setName(SimulationName name) {
		this.name = name;
	}

	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	public void setBorder(BorderType border) {
		this.border = border;
	}

	public void setShape(CellShape shape) {
		this.shape = shape;
	}

	public SimulationName getName() {
		return name;
	}

	public Dimensions getDimensions() {
		return dimensions;
	}
	
	public BorderType getBorder() {
		return border;
	}
	
	public CellShape getShape(){
		return shape;
	}
	
}
