package cellular_level;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import data_structures.CellData;

/**
 * Superclass to the Live and Dead Cells, holds common
 * actions like copy creation and general update
 * 
 * @author Stone Mathers
 * @author maddiebriere
 *
 */

public abstract class LifeSimCell extends Cell {
	
	public LifeSimCell(){
		super();
	}
	
	public LifeSimCell(int row, int col, Color color){
		super(row, col, color);
	}
	
	@Override
	public Cell createCopy(){
		Cell copy = new DeadCell();  //Arbitrary instantiation
		try {
			copy = this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		copy.basicCopy(this);
		return copy;
	}
	
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
	
	protected abstract void changeState(CellData data, ArrayList<Cell> newGen);
}