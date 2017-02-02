package file_handling;

import cellular_level.LiveCell;
import cellular_level.DeadCell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import cellular_level.Cell;

public class LifeSimulation extends SimulationType {
	

	public LifeSimulation(Map<String, String> values, ArrayList<String> cells) {
		super(values, cells);
	}

	@Override
	public List<String> combineDataTypes() {
		return this.getUniversalTypes();
	}

	@Override
	protected ArrayList<Cell> getCells() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(String data: this.getCellData()){
			String[] vars = data.split(" ");
			if(vars[2].toUpperCase().equals("LIVE")){
				cells.add(new LiveCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
			else{
				cells.add(new DeadCell(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
			}
		}
		return cells;
	}


}
