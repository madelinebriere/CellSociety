/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Predator-Prey Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import cellular_level.FishCell;
import cellular_level.SharkCell;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import cellular_level.Cell;

public class WaterSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"fish breed",
	        "shark breed",
	        "shark starve"
	    });

	public WaterSimulation(Map<String, String> values, ArrayList<String> cells) {
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
	public ArrayList<Cell> getCells() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(String data: this.getCellData()){
			String[] vars = data.split(" ");
			if(vars[2].toUpperCase().equals("FISH")){
				cells.add(new FishCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
			else if(vars[2].toUpperCase().equals("SHARK")){
				cells.add(new SharkCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
		}
		return cells;
	}
	
	public String getFishBreed(){
		return getDataValues().get(SETTING_TYPES.get(0));
	}
	
	public String getSharkBreed(){
		return getDataValues().get(SETTING_TYPES.get(1));
	}
	
	public String getSharkStarve(){
		return getDataValues().get(SETTING_TYPES.get(2));
	}
}
