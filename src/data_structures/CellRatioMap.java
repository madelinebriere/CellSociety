//author Talha Koc

//Author Talha Koc
package data_structures;

import java.util.Map;
import java.util.Map.Entry;
/**
 * Intended use: use with CellSociety to initialize cells with a given ratio
 * 
 * Meant to hold a map of cell names to cell ratios
 * 
 * @author talha koc
 *
 */
public class CellRatioMap {
	private Map<CellName, CellRatio> ratios;
	public CellRatioMap(Map<CellName, CellRatio> ratios){
		//check if ratios add up to 1.0
		double sum = 0;
		for(Entry<CellName, CellRatio> entry: ratios.entrySet()){
			sum += entry.getValue().getRatio();
		}
		if(sum > 1.){
			throw new RuntimeException("Developer Error: sum of ratios exceed the value of 1");
		}
		this.ratios = ratios;
	}
	public Map<CellName, CellRatio> getMapOfCellsRatios(){
		return ratios;
	}
}
