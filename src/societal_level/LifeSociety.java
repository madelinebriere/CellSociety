package societal_level;

import java.util.List;
import java.util.TreeMap;

import cellular_level.RawData;
import data_structures.BoardData;
import data_structures.PatchName;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;
import patch_level.Patch;

public class LifeSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.WHITE;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;
	
	public LifeSociety(SimulationType sim){
		super(sim);
	}
	
	public LifeSociety(SimulationData data){
		super(data);
	}
	
	@Override
	public void parseRules(RawData raw) {
		// TODO Auto-generated method stub
		
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
