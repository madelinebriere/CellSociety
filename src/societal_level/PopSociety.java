package societal_level;

import cellular_level.Cell;
import cellular_level.HouseCell;
import data_structures.CellName;
import data_structures.PatchName;
import data_structures.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety specific to Segregation simulation,
 * with multiple populations relocating according to certain
 * thresholds of satisfaction.
 * 
 * HUGE assumption: 
 * Only three types of populations are allowed. Our design
 * could facilitate as many populations as there are cells, but
 * the only difference between populations is color and this did not
 * warrant a set of different classes. Because different cells are distinguished
 * by class name, HouseCells of different colors must be 
 * manually accounted for (and assigned colors).
 * 
 * @author maddiebriere
 *
 */

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
		if(data.getDoubleVariables().size()==0){return;}
		satisfiedThresh = data.getDoubleVariables().get(0);
	}
	
	public double getSatisfiedThresh() {
		return satisfiedThresh;
	}

	public void setSatisfiedThresh(double satisfiedThresh) {
		this.satisfiedThresh = satisfiedThresh;
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
		if(getCurrentCells().size()==0){return;}
		activateHouseCell(CellName.HOUSE_CELL_1);
		activateHouseCell(CellName.HOUSE_CELL_2);
		activateHouseCell(CellName.HOUSE_CELL_3);
	}
	
	private void activateHouseCell(CellName name){
		if(!getCurrentCells().containsKey(name)){
			return;
		}
		for(Cell c: getCurrentCells().get(name)){
			if(satisfiedThresh>0 && satisfiedThresh<=1)
			((HouseCell)c).setSatisfiedThreshold(satisfiedThresh);
		}
	}
}
