package societal_level;

import data_structures.PatchName;
import javafx.scene.paint.Color;

public class SlimeSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.BLACK;
	private static final PatchName PATCH_TYPE = PatchName.SLIME_PATCH;
	
	private int diffuseRate;
	private int evaporate;
	private int depositRate;
	private int maxSaturation;
	
	@Override
	public Color getEmptyColor() {
		return EMPTY_COLOR;
	}

	@Override
	public PatchName getPatchType() {
		return PATCH_TYPE;
	}

	@Override
	public void setVariablesToDefault() {
		// TODO Auto-generated method stub
		
	}

}
