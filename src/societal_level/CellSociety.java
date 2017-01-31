package societal_level;
import cellular_level.*;

public abstract class CellSociety {
	private Cell [][] currentCells;
	
	public abstract Cell[][] step();
	
	public Cell[][] getCurrentCells(){
		return currentCells;
	}
	
	public void setCurrentCells(Cell[][] current){
		currentCells=current;
	}
}
