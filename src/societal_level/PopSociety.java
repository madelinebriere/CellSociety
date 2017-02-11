package societal_level;

import java.util.ArrayList;
import java.util.List;

import cellular_level.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import patch_level.Patch;

public class PopSociety extends CellSociety {
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

	@Override
	public void setPatches() {
		
	}

	@Override
	public List<Patch> getShiftedPatches() {
		// TODO Auto-generated method stub
		return null;
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
}
