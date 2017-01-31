package cellular_level;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import util.Location;

public class EmptyCell extends Cell{
	private static Color EMPTY_COLOR = null;
	
	public EmptyCell(){
		this(0,0, EMPTY_COLOR);
	}
	
	public EmptyCell(int row, int col, Color emptyColor){
		super(row, col, emptyColor);
	}
	
	@Override
	public ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<EmptyCell> nullCells, int size) {
		return null;
	}

}
