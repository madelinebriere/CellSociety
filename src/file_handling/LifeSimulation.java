/**
 * Holds, interprets, and returns data passed 
 * in from a file specific to the Game of Life Simulation.
 * 
 * @author Stone Mathers
 */
package file_handling;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LifeSimulation extends SimulationType {
	
	private static final List<String> DEFAULT_CELLS = Arrays.asList(new String[] {
			".3 live",
	        ".7 dead"
	   });
	
	public LifeSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.defaultCellData = DEFAULT_CELLS;
		myDataValues = createDataMap(values);
		cellData = createCellList(cells);
	}
	@Override
	protected List<String> combineDataTypes() {
		return this.getUniversalTypes();
	}
}