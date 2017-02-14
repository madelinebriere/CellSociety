/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Segregation Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cellular_level.HouseCell;
import data_structures.SimulationName;

public class PopSimulation extends SimulationType {

	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] { "threshold" });
	private static final List<String> DEFAULT_SETTINGS = Arrays
			.asList(new String[] { ((Double) (HouseCell.SATISFIED_THRESH)).toString(), });
	private static final List<String> DEFAULT_CELLS = Arrays.asList(new String[] { ".4 red", ".2 green", ".1 blue" });

	public PopSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES; // This must be called before
											// this.dataTypes is initialized by
											// combineDataTypes()
		this.settingDefaults = DEFAULT_SETTINGS; // This must be called before
													// this.dataDefaults is
													// initialized by
													// combineDefaultData()
		this.dataTypes = combineDataTypes();
		this.dataDefaults = combineDefaultData();
		this.defaultCellData = DEFAULT_CELLS;
		myDataValues = createDataMap(values);
		cellData = createCellList(cells);
		boardData = createBoardData();
	}

	public Double getThreshold() {
		try {
			return Double.parseDouble(getDataValues().get(SETTING_TYPES.get(0)));
		} catch (Exception e) {
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(0)));
		}
	}

	@Override
	protected List<Integer> getIntegerData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		return data;
	}

	@Override
	protected List<Double> getDoubleData() {
		ArrayList<Double> data = new ArrayList<Double>();
		data.add(getThreshold());
		return data;
	}

	@Override
	protected SimulationName getSimulationName() {
		return SimulationName.POPULATION_SOCIETY;
	}

}
