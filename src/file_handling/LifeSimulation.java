/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Game of Life Simulation.
 * 
 * @author Stone Mathers
 */
package file_handling;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import data_structures.SimulationName;

public class LifeSimulation extends SimulationType {
	
	private static final List<String> DEFAULT_CELLS = Arrays.asList(new String[] {
			".3 live",
	        ".7 dead"
	   });
	
	public LifeSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.defaultCellData = DEFAULT_CELLS;
		this.dataTypes = combineDataTypes();
		this.dataDefaults = combineDefaultData();
		myDataValues = createDataMap(values);
		cellData = createCellList(cells);
		boardData = createBoardData();
	}
	@Override
	protected List<String> combineDataTypes() {
		return this.getUniversalTypes();
	}
	@Override
	protected List<Integer> getIntegerData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		return data;
	}
	@Override
	protected List<Double> getDoubleData() {
		ArrayList<Double> data = new ArrayList<Double>();
		return data;
	}
	@Override
	protected SimulationName getSimulationName() {
		// TODO Auto-generated method stub
		return null;
	}
}