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

import cellular_level.LiveCell;
import cellular_level.DeadCell;
import data_structures.SimulationName;

public class LifeSimulation extends SimulationType {

	private static final List<String> SETTING_TYPES = Arrays
			.asList(new String[] { "underpopulated", "overpopulated", "numberForLive" });
	private static final List<String> DEFAULT_SETTINGS = Arrays
			.asList(new String[] { ((Integer) (LiveCell.UNDER_POP)).toString(),
					((Integer) (LiveCell.OVER_POP)).toString(), ((Integer) (DeadCell.NUM_FOR_LIVE)).toString(), });
	private static final List<String> DEFAULT_CELLS = Arrays.asList(new String[] { ".3 live", ".7 dead" });

	public LifeSimulation(Map<String, String> values, List<String> cells) {
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

	public Integer getUnderpopulated() {
		try {
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(0)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(0)));

		}
	}

	public Integer getOverpopulated() {
		try {
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
		} catch (Exception e) {
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(1)));
		}
	}

	public Integer getNumberForLive() {
		try {
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(2)));

		} catch (Exception e) {
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(2)));
		}
	}

	@Override
	protected List<String> combineDataTypes() {
		return this.getUniversalTypes();
	}
	@Override
	protected List<Integer> getIntegerData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(getUnderpopulated());
		data.add(getOverpopulated());
		data.add(getNumberForLive());
		return data;
	}

	@Override
	protected List<Double> getDoubleData() {
		ArrayList<Double> data = new ArrayList<Double>();
		return data;
	}

	@Override
	protected SimulationName getSimulationName() {
		return SimulationName.GAME_OF_LIFE;
	}
}