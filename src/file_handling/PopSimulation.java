/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Segregation Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PopSimulation extends SimulationType {
	
	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"threshold"
	    });

	public PopSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES;
		this.dataTypes = combineDataTypes();
	}
	
	public Double getThreshold(){
		try{
			return Double.parseDouble(getDataValues().get(SETTING_TYPES.get(0)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(0)));
		}
	}
}
