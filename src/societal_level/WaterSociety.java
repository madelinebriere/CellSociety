package societal_level;

import java.util.List;
import java.util.TreeMap;

import data_structures.BoardData;
import data_structures.PatchName;
import data_structures.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;
import patch_level.Patch;

public class WaterSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.BLUE;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;
	
	private int fishBreed;
	private int sharkBreed;
	private int sharkDie;
	
	public WaterSociety(SimulationData sim){
		super(sim);
	}
	
	public WaterSociety(SimulationType sim){
		super(sim);
	}
	
	@Override
	public void parseRules(RawData data) {
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

	@Override
	protected void applySettings() {
		// TODO Auto-generated method stub
		
	}

}
