package societal_level;

import java.util.ArrayList;
import java.util.List;

import cellular_level.Cell;
import cellular_level.HouseCell;
import data_structures.CellName;
import data_structures.PatchName;
import data_structures.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;
import patch_level.Patch;

public class PopSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.ALICEBLUE;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;
	
	private double satisfiedThresh;
	
	public PopSociety(SimulationType data){
		super(data);
	}
	
	public PopSociety(SimulationData data){
		super(data);
	}

	@Override
	public void parseRules(RawData data) {
		satisfiedThresh = data.getDoubleVariables().get(0);
	}
	
	public double getSatisfiedThresh() {
		return satisfiedThresh;
	}

	public void setSatisfiedThresh(double satisfiedThresh) {
		this.satisfiedThresh = satisfiedThresh;
	}

	@Override
	public Color getEmptyColor() {
		return EMPTY_COLOR;
	}

	@Override
	public PatchName getPatchType() {
		return PATCH_TYPE;
	}

	@Override
	protected void applySettings() {
		for(Cell c: getCurrentCells().get(CellName.HOUSE_CELL_1)){
			if(satisfiedThresh>0 && satisfiedThresh<=1)
			((HouseCell)c).setSatisfiedThreshold(satisfiedThresh);
		}
		for(Cell c: getCurrentCells().get(CellName.HOUSE_CELL_2)){
			if(satisfiedThresh>0 && satisfiedThresh<=1)
			((HouseCell)c).setSatisfiedThreshold(satisfiedThresh);
		}
		for(Cell c: getCurrentCells().get(CellName.HOUSE_CELL_3)){
			if(satisfiedThresh>0 && satisfiedThresh<=1)
			((HouseCell)c).setSatisfiedThreshold(satisfiedThresh);
		}
		
	}
}
