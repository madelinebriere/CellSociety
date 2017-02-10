/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Predator-Prey Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WaterSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"fishBreed",
	        "sharkBreed",
	        "sharkStarve"
	    });

	public WaterSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES;
		this.dataTypes = combineDataTypes();
	}


	public Integer getFishBreed(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(0)));
	}
	
	public Integer getSharkBreed(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
	}
	
	public Integer getSharkStarve(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(2)));
	}

}