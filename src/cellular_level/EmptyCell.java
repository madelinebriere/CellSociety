package cellular_level;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import util.CellData;

public class EmptyCell extends Cell{
	private static final Color EMPTY_COLOR = Color.WHITE;

	public EmptyCell(){
		this(0,0);
	}
	
	public EmptyCell(int row, int col){
		this(row, col, EMPTY_COLOR);
	}
	
	public EmptyCell(int row, int col, Color color){
		super(row, col, color);
	}
	
	
	public EmptyCell(Cell c){
		super(c.getMyRow(), c.getMyCol(), EMPTY_COLOR);
	}
	
	@Override
	public Cell createCopy(){
		EmptyCell copy = new EmptyCell();
		copy.basicCopy(this);
		return copy;
	}
	
	@Override
	public List<Cell> update(CellData data) {
		ArrayList<Cell> empty = new ArrayList<Cell>();
		return empty;
	}

}
