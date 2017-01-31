package cellular_level;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import util.Location;

public class EmptyCell extends Cell{
	private static Color emptyColor = null;
	
	public EmptyCell(){
		this(0,0);
	}
	
	public EmptyCell(int row, int col){
		super(row, col, emptyColor);
	}
	
	@Override
	protected ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<EmptyCell> nullCells, int size) {
		return null;
	}

	public static Color getEmptyColor() {
		return emptyColor;
	}

	public static void setEmptyColor(Color emptyColor) {
		EmptyCell.emptyColor = emptyColor;
	}
	

}
