//author Talha Koc

package data_structures;

import java.util.Map;
import java.util.Map.Entry;

public class CellRatioMap {
	private Map<CellName, CellRatio> ratios;
	public CellRatioMap(Map<CellName, CellRatio> ratios){
		//TODO error checking
		//check if ratios add up to one
		double sum = 0;
		for(Entry<CellName, CellRatio> entry: ratios.entrySet()){
			sum += entry.getValue().getRatio();
		}
		if(sum < 0.99999 || sum > 1.00001){//
			//TODO throw error, invalid ratios
		}
		this.ratios = ratios;
	}
	public Map<CellName, CellRatio> getMapOfCellsRatios(){
		return ratios;
	}
}
