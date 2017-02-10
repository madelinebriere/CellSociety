/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Fire Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FireSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"probability",
	        "steps"
	    });

	public FireSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES;
		this.dataTypes = combineDataTypes();
	}
	
	public Double getProbability(){
		return Double.parseDouble(getDataValues().get(SETTING_TYPES.get(0)));
	}
	
	public Integer getSteps(){
		return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
	}


}