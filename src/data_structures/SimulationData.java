package data_structures;

/**
 * Data structure holding information coming from the front end
 * 
 * @author maddiebriere
 * @author tnk3
 */

public class SimulationData {
	private BoardData data;
	private CellRatioMap ratios;

	public SimulationData(BoardData data, CellRatioMap c) {
		this.data = data;
		ratios = c;
	}

	public BoardData getData() {
		return data;
	}

	public void setData(BoardData data) {
		this.data = data;
	}

	public CellRatioMap getRatios() {
		return ratios;
	}

	public void setRatios(CellRatioMap ratios) {
		this.ratios = ratios;
	}

	public boolean isGetAllNeighbors() {
		return data.isGetAllNeighbors();
	}

	public SimulationName getSimulationName() {
		return data.getName();
	}

	public Dimensions getDimensions() {
		return data.getDimensions();
	}

	public BorderType getBorder() {
		return data.getBorder();
	}

	public CellShape getShape() {
		return data.getShape();
	}

	public RawData getRawData() {
		return data.getRaw();
	}

}
