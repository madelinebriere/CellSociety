package cellular_level;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import util.CellData;

public abstract class LifeSimulationCell extends Cell {
	
	public LifeSimulationCell(){
		super();
	}
	
	public LifeSimulationCell(Color c){
		super(c);
	}
	
	public LifeSimulationCell(int row, int col, Color color){
		super(row, col, color);
	}
	
	/**
	 * Defined by subclasses
	 */
	protected abstract void changeState(CellData data, ArrayList<Cell> newGen);
	
	/**
	 *@param data CellData object holding accessible information for Cell
	 * @return ArrayList of Cells for the next generation. Will contain either the current
	 * live cell or a new dead cell in the same location. Never empty.
	 */
	@Override
	public List<Cell> update(CellData data) {
		ArrayList<Cell> newGen = new ArrayList<Cell>();
		changeState(data, newGen);
		return newGen;
	}

}