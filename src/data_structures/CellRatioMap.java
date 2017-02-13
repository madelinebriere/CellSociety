//author Talha Koc

//Author Talha Koc
package data_structures;

import java.util.Map;
import java.util.Map.Entry;

public class CellRatioMap {
	private Map<CellName, CellRatio> ratios;

	public CellRatioMap(Map<CellName, CellRatio> ratios) {
		// TODO error checking
		// check if ratios add up to 1.0
		double sum = 0;
		for (Entry<CellName, CellRatio> entry : ratios.entrySet()) {
			sum += entry.getValue().getRatio();
		}
		if (sum < 0.999999 || sum > 1.000001) {// margin of error for double
												// addition
			// TODO throw error, invalid ratios
		}
		// TODO: check if cells are compatible
		this.ratios = ratios;
	}

	public Map<CellName, CellRatio> getMapOfCellsRatios() {
		return ratios;
	}
}
