/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Game of Life Simulation.
 * 
 * @author Stone Mathers
 */
package file_handling;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import data_structures.PatchName;
import javafx.scene.paint.Color;
import patch_level.Patch;

public class LifeSimulation extends SimulationType {
	public LifeSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
	}
	@Override
	protected List<String> combineDataTypes() {
		return this.getUniversalTypes();
	}
	
	@Override
	public TreeMap<PatchName, List<Patch>> getShiftedPatches() {
		return getShiftedPatches(PatchName.EMPTY_PATCH, Color.LIGHTBLUE);
	}

}