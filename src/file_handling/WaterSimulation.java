/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Predator-Prey Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cellular_level.FishCell;
import cellular_level.SharkCell;
import data_structures.SimulationName;

public class WaterSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"fishBreed",
	        "sharkStarve",
	        "sharkBreed"
	    });
	private static final List<String> DEFAULT_SETTINGS = Arrays.asList(new String[] {
			((Integer)(FishCell.STEPS_TO_BREED)).toString(),
	        ((Integer)(SharkCell.STEPS_TO_STARVE)).toString(),
	        ((Integer)(SharkCell.STEPS_TO_BREED)).toString()
	   });
	private static final List<String> DEFAULT_CELLS = Arrays.asList(new String[] {
			".5 fish",
	        ".2 shark"
	   });

	public WaterSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES;  //This must be called before this.dataTypes is initialized by combineDataTypes()
		this.settingDefaults = DEFAULT_SETTINGS; //This must be called before this.dataDefaults is initialized by combineDefaultData()
		this.dataTypes = combineDataTypes();
		this.dataDefaults = combineDefaultData();
		this.defaultCellData = DEFAULT_CELLS;
		myDataValues = createDataMap(values);
		cellData = createCellList(cells);
		boardData = createBoardData();
	}


	public Integer getFishBreed(){
		try{
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(0)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(0)));
		}
	}
	
	public Integer getSharkBreed(){
		try{
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(1)));
		}
	}
	
	public Integer getSharkStarve(){
		try{
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(2)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(2)));
		}
	}


	@Override
	protected List<Integer> getIntegerData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(getFishBreed());
		data.add(getSharkStarve());
		data.add(getSharkBreed());
		return data;
	}

	@Override
	protected List<Double> getDoubleData() {
		ArrayList<Double> data = new ArrayList<Double>();
		return data;
	}


	@Override
	protected SimulationName getSimulationName() {
		return SimulationName.WATER_SOCIETY;
	}

}