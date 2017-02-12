package societal_level;

import cellular_level.BurnCell;
import cellular_level.Cell;
import cellular_level.TreeCell;
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
		probCatch = data.getDoubleVariables().get(0);
		stepsToBurn = data.getIntegerVariables().get(0);
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
		for(Cell c: getCurrentCells().get(CellName.TREE_CELL)){
			if (probCatch>0 && probCatch<=1){
				((TreeCell)c).setProbCatch(probCatch);
			}
		}
		for(Cell c: getCurrentCells().get(CellName.BURN_CELL)){
			if(stepsToBurn>0){
				((BurnCell)c).setStepsToBurn(stepsToBurn);
			}
		}
		
	}
	
}
