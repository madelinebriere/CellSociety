package societal_level;

import java.util.List;

import cellular_level.RawData;
import data_structures.BoardData;
import data_structures.*;
import file_handling.SimulationType;
import javafx.scene.paint.Color;
import patch_level.Patch;

public class FireSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.YELLOW;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;
	private static final double DEFAULT_THRESH = .5;
	
	private double thresh;

	public FireSociety(SimulationType sim){
		super(sim);
	}
	
	public FireSociety(SimulationData data){
		super(data);
	}
	
	@Override
	public void parseRules(RawData data) {
		//TODO
	}

	@Override
	public void setVariablesToDefault() {
		//TODO
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
