/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Fire Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import cellular_level.BurnCell;
import cellular_level.TreeCell;
import data_structures.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FireSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"probability",
	        "steps"
	   });
	private static final List<String> DEFAULT_SETTINGS = Arrays.asList(new String[] {
			((Double)(TreeCell.PROB_CATCH)).toString(),
	        ((Integer)(BurnCell.STEPS_TO_BURN)).toString()
	   });
	private static final List<String> DEFAULT_CELLS = Arrays.asList(new String[] {
			".95 tree",
	        ".05 burn"
	   });

	public FireSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES;  //This must be called before this.dataTypes is initialized by combineDataTypes()
		this.settingDefaults = DEFAULT_SETTINGS; //This must be called before this.dataDefaults is initialized by combineDefaultData()
		this.dataTypes = combineDataTypes();
		this.dataDefaults = combineDefaultData();
		this.defaultCellData = DEFAULT_CELLS;
		myDataValues = createDataMap(values);
		cellData = createCellList(cells);
	}
	
	public Double getProbability(){
		try{
			return Double.parseDouble(getDataValues().get(SETTING_TYPES.get(0)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(0)));
		}
	}
	
	public Integer getSteps(){
		try{
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(1)));
		}
	}

//	@Override
//	protected BoardData createBoardData() {
//		SimulationName name = SimulationName.FIRE_SOCIETY;
//		Dimensions dim = new Dimensions(this.getDimension(), this.getDimension());
//		//BorderType border = new
//		
//		return null;
//	}
}

