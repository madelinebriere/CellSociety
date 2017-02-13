/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Slime Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import cellular_level.SlimeCell;
import patch_level.SlimePatch;
import data_structures.SimulationName;

public class SlimeSimulation extends SimulationType {

	private static final List<String> SETTING_TYPES = Arrays
			.asList(new String[] { "threshold", "evaporationRate", "depositRate" });
	private static final List<String> DEFAULT_SETTINGS = Arrays
			.asList(new String[] { ((Integer) (SlimeCell.SNIFF_THRESH)).toString(),
					((Integer) (SlimePatch.EVAPO_RATE)).toString(), ((Integer) (SlimeCell.DEPOSIT)).toString() });
	private static final List<String> DEFAULT_CELLS = Arrays.asList(new String[] { ".5 slime" });

	public SlimeSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES; // This must be called before
											// this.dataTypes is initialized by
											// combineDataTypes()
		this.settingDefaults = DEFAULT_SETTINGS; // This must be called before
													// this.dataDefaults is
													// initialized by
													// combineDefaultData()
		this.defaultCellData = DEFAULT_CELLS;
		this.dataTypes = combineDataTypes();
		this.dataDefaults = combineDefaultData();
		myDataValues = createDataMap(values);
		cellData = createCellList(cells);
		boardData = createBoardData();
	}

	public Integer getThreshold() {
		try {
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(0)));
		} catch (Exception e) {
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(0)));
		}
	}

	public Integer getEvaporationRate() {
		try {
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
		} catch (Exception e) {
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(1)));
		}
	}

	public Integer getDepositRate() {
		try {
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(3)));
		} catch (Exception e) {
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(3)));
		}
	}

	@Override
	protected List<Integer> getIntegerData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(getThreshold());
		data.add(getEvaporationRate());
		data.add(getDepositRate());
		return data;
	}

	@Override
	protected List<Double> getDoubleData() {
		ArrayList<Double> data = new ArrayList<Double>();
		return data;
	}

	@Override
	protected SimulationName getSimulationName() {
		return SimulationName.SLIME_SOCIETY;
	}
}
