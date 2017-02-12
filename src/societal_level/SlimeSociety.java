package societal_level;

import cellular_level.Cell;
import data_structures.CellName;
import data_structures.PatchName;
import data_structures.RawData;
import javafx.scene.paint.Color;

public class SlimeSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.BLACK;
	private static final PatchName PATCH_TYPE = PatchName.SLIME_PATCH;
	
	private int evaporate;
	private int depositRate;
	
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
		for(Cell c: getCurrentCells().get(CellName.SLIME_CELL)){
			if(evaporate>0){
			}
		}
		
	}

	@Override
	public void parseRules(RawData data) {
		if(data.getIntegerVariables().size()==0){return;}
		evaporate = data.getIntegerVariables().get(0);
		depositRate = data.getIntegerVariables().get(1);
	}
	
	

}
