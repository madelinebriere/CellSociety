package data_structures;

import borders.Border;
import borders.*;
/**
 * Information held in SimulationData about board set-up, including dimensions, type of border
 * and shape of tiles
 * 
 * @author maddiebriere
 *
 */

public class BoardData {
	private static int DEFAULT_SIZE = 10;
	
	private Dimensions dimensions;
	private Border border;
	private CellShape shape;
	
	public BoardData(){
		dimensions = new Dimensions(DEFAULT_SIZE,DEFAULT_SIZE);
		border = new FiniteBorder();
		shape = CellShape.SQUARE;
	}


	public BoardData(Dimensions dimensions, Border border, CellShape shape) {
		super();
		this.dimensions = dimensions;
		this.border = border;
		this.shape = shape;
	}



	public Dimensions getDimensions() {
		return dimensions;
	}
	
	public Border getBorder() {
		return border;
	}
	
	public CellShape getShape(){
		return shape;
	}
	
}
