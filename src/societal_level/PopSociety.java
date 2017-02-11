package societal_level;

import java.util.ArrayList;
import java.util.List;

import cellular_level.Cell;
import cellular_level.RawData;
import data_structures.PatchName;
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
		
	}
	
	public double getSatisfiedThresh() {
		return satisfiedThresh;
	}

	public void setSatisfiedThresh(double satisfiedThresh) {
		this.satisfiedThresh = satisfiedThresh;
	}

	@Override
	public void setVariablesToDefault() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getEmptyColor() {
		return EMPTY_COLOR;
	}

	@Override
	public PatchName getPatchType() {
		return PATCH_TYPE;
	}
}
