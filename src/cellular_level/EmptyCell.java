package cellular_level;

import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.paint.Color;

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
	public Cell createCopy(){
		EmptyCell copy = new EmptyCell();
		copy.setMyLocation(this.getMyLocation());
		copy.setMyState(this.getMyState());
		return copy;
	}
	
	@Override
	public Collection<Cell> update(Collection<Cell> currentCells, Collection<EmptyCell> available, int size) {
		ArrayList<Cell> empty = new ArrayList<Cell>();
		return empty;
	}
	
	@Override
	public Collection<Cell> neighbors(Collection<Cell> currentCells, int size){
		return getNeighbors(currentCells);
	}

}
