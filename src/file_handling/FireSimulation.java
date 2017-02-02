package file_handling;

import cellular_level.TreeCell;
import cellular_level.BurnCell;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import cellular_level.Cell;

public class FireSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"probability",
	        "steps"
	    });

	public FireSimulation(Map<String, String> values, ArrayList<String> cells) {
		super(values, cells);
	}

	@Override
	public List<String> combineDataTypes() {
		List<String> data = new ArrayList<String>();
		data.addAll(getUniversalTypes());
		data.addAll(SETTING_TYPES);
		return data;
	}

	@Override
	protected ArrayList<Cell> getCells() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(String data: this.getCellData()){
			String[] vars = data.split(" ");
			if(vars[2].toUpperCase().equals("TREE")){
				cells.add(new TreeCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
			else{
				cells.add(new BurnCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
		}
		return cells;
	}
	
	public String getProbability(){
		return getDataValues().get(SETTING_TYPES.get(0));
	}
	
	public String getSteps(){
		return getDataValues().get(SETTING_TYPES.get(1));
	}


}
