//author Talha Koc

package data_structures;

import java.util.Map;
import java.util.Map.Entry;

import cellular_level.Cell;

public class CellRatioMap {
	private Map<Class<Cell>, CellRatio> ratios;
	public <T extends Cell> CellRatioMap(Map<Class<Cell>, CellRatio> ratios){
		//TODO error checking
		//check if ratios add up to 1.0
		double sum = 0;
		for(Entry<Class<Cell>, CellRatio> entry: ratios.entrySet()){
			sum += entry.getValue().getRatio();
		}
		if(sum < 0.999999 || sum > 1.000001){//margin of error for double addition
			//TODO throw error, invalid ratios
		}
		//TODO: check if cells are compatible
		this.ratios = ratios;
	}
	public Map<Class<Cell>, CellRatio> getMapOfCellsRatios(){
		return ratios;
	}
}
