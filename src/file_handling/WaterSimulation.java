/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Predator-Prey Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import cellular_level.FishCell;
import cellular_level.LiveCell;
import cellular_level.SharkCell;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import cellular_level.Cell;
import cellular_level.DeadCell;
import cellular_level.EmptyCell;

public class WaterSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"fishBreed",
	        "sharkBreed",
	        "sharkStarve"
	    });

	public WaterSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
	}

	@Override
	protected List<String> combineDataTypes() {
		List<String> data = new ArrayList<String>();
		data.addAll(getUniversalTypes());
		data.addAll(SETTING_TYPES);
		return data;
	}

	@Override
	public List<Cell> getCells() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(String data: this.getCellData()){
			String[] vars = data.split(" ");
			if(vars[2].toUpperCase().equals("FISH")){
				cells.add(new FishCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
			else if(vars[2].toUpperCase().equals("SHARK")){
				cells.add(new SharkCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
			else if(vars[2].toUpperCase().equals("EMPTY")){
				cells.add(new EmptyCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
		}
		return cells;
	}
	
	public int getFishBreed(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(0)));
	}
	
	public int getSharkBreed(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
	}
	
	public int getSharkStarve(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(2)));
	}

	@Override
	public void initializeSocietyVariables() {
		FishCell.setStepsToBreed(getFishBreed());
		SharkCell.setStepsToBreed(getSharkBreed());
		SharkCell.setStepsToStarve(getSharkStarve());
		setEmptyColor(Color.LIGHTBLUE);
	}
	
	@Override
	public void initializeCellTypes(){
		ArrayList<Class<? extends Cell>>cellTypes = new ArrayList<Class<? extends Cell>>();
		cellTypes.add(FishCell.class);
		cellTypes.add(SharkCell.class);
		cellTypes.add(EmptyCell.class);
		setCellTypes(cellTypes);
	}
	
	@Override
	public void initializeDefaultCells(){
		ArrayList<Class<? extends Cell>>cellTypes = new ArrayList<Class<? extends Cell>>();
		cellTypes.add(EmptyCell.class);
		setDefaultCellTypes(cellTypes);
	}
}
