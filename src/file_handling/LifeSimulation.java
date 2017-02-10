/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Game of Life Simulation.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.List;
import java.util.Map;


public class LifeSimulation extends SimulationType {

	public LifeSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
	}

	@Override
	protected List<String> combineDataTypes() {
		return this.getUniversalTypes();
	}



}
