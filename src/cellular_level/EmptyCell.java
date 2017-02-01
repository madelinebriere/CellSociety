package cellular_level;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import util.Location;

public class EmptyCell extends Cell{
	private static Color EMPTY_COLOR = null;
	
	public EmptyCell(){
		this(0,0);
	}
	
	public EmptyCell(int row, int col){
		super(row, col,  EMPTY_COLOR);
	}
	
	public EmptyCell(Cell c){
		super(c.getMyRow(), c.getMyCol(), EMPTY_COLOR);
	}
	
	@Override
	public ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<EmptyCell> nullCells, int size) {
		ArrayList<Cell> empty = new ArrayList<Cell>();
		empty.add(this);
		return empty;
	}

}
