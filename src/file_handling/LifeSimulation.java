/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Game of Life Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import cellular_level.LiveCell;
import javafx.scene.paint.Color;
import cellular_level.DeadCell;
import cellular_level.EmptyCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import cellular_level.Cell;

public class LifeSimulation extends SimulationType {
	

	public LifeSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
	}

	@Override
	protected List<String> combineDataTypes() {
		return this.getUniversalTypes();
	}

	@Override
	public List<Cell> getCells() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(String data: this.getCellData()){
			String[] vars = data.split(" ");
			if(vars[NAME_INDEX].toUpperCase().equals("LIVE")){
				cells.add(new LiveCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
			else if(vars[NAME_INDEX].toUpperCase().equals("DEAD")){
				cells.add(new DeadCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
		}
		return cells;
	}

	@Override
	public void initializeSocietyVariables() {
		setEmptyColor(Color.WHITE);
	}
	
	@Override
	public void initializeCellTypes(){
		ArrayList<Class<? extends Cell>>cellTypes = new ArrayList<Class<? extends Cell>>();
		cellTypes.add(LiveCell.class);
		cellTypes.add(DeadCell.class);
		setCellTypes(cellTypes);
	}
	
	@Override
	public void initializeDefaultCells(){
		ArrayList<Class<? extends Cell>>cellTypes = new ArrayList<Class<? extends Cell>>();
		cellTypes.add(DeadCell.class);
		setDefaultCellTypes(cellTypes);
	}
	

}
