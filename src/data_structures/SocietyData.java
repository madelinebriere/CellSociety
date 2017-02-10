package data_structures;

import javafx.scene.paint.Color;


/**
 * Information about Society held by SimulationData
 *	including the types of neighbors included, the color of empty
 *	cells and the ratio of each cell type
 * 
 * @author maddiebriere
 *
 */
public class SocietyData {
	private boolean getAllNeighbors;
	private Color emptyCellColor;
	private CellRatioMap ratios;
	private CellName defaultCell;
	
	public SocietyData(boolean getAllNeighbors, Color emptyCellColor, CellRatioMap ratios, CellName cell) {
		super();
		this.getAllNeighbors = getAllNeighbors;
		this.emptyCellColor = emptyCellColor;
		this.ratios = ratios;
		this.defaultCell = cell;
	}

	public boolean isGetAllNeighbors() {
		return getAllNeighbors;
	}

	public Color getEmptyCellColor() {
		return emptyCellColor;
	}

	public CellRatioMap getRatios() {
		return ratios;
	}
	
	public CellName getDefaultCell(){
		return defaultCell;
	}

}
