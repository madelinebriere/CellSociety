/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Fire Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cellular_level.*;

public class FireSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"probability",
	        "steps"
	    });

	public FireSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES;
		this.dataTypes = combineDataTypes();
	}

	@Override
	public List<Cell> getCells() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(String data: this.getCellData()){
			String[] vars = data.split(" ");
			if(vars[NAME_INDEX].toUpperCase().equals("TREE")){
				cells.add(new TreeCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
			else if(vars[NAME_INDEX].toUpperCase().equals("BURN")){
				cells.add(new BurnCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
		}
		return cells;
	}
	
	public Double getProbability(){
		return Double.parseDouble(getDataValues().get(SETTING_TYPES.get(0)));
	}
	
	public int getSteps(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
	}


}
