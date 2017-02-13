package societal_level;

import data_structures.PatchName;
import data_structures.RawData;
import javafx.scene.paint.Color;

public class SugarSociety extends CellSociety{

	@Override
	public void parseRules(RawData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getEmptyColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatchName getPatchType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void applySettings() {
		if(getCurrentCells().size()==0){return;}
		
	}

}
