/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Segregation Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import cellular_level.HouseCell;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import cellular_level.Cell;

public class PopSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"threshold"
	    });

	public PopSimulation(Map<String, String> values, List<String> cells) {
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
			if(vars[2].toUpperCase().equals("BLUE")){
				cells.add(new HouseCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1]), Color.BLUE));
			}
			else if(vars[2].toUpperCase().equals("RED")){
				cells.add(new HouseCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1]), Color.RED));
			}
		}
		return cells;
	}
	
	public Double getThreshold(){
		return Double.parseDouble(getDataValues().get(SETTING_TYPES.get(0)));
	}
}
