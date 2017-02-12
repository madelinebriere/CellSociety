package societal_level;

import data_structures.*;
import file_handling.SimulationType;
import javafx.scene.paint.Color;

public class FireSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.YELLOW;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;
	
	private double probCatch;
	private int stepsToBurn;

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
