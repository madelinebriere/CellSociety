package societal_level;

import data_structures.PatchName;
import data_structures.RawData;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety specific to 
 * the SugarScape simulation. More complicated in that
 * the initial patches and concentrations must be accounted for
 * as well as initial cells. Allows SugarCells.
 * 
 * @author maddiebriere
 *
 */

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
