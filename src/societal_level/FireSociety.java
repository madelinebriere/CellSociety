package societal_level;

import cellular_level.BurnCell;
import cellular_level.Cell;
import cellular_level.TreeCell;
import data_structures.*;
import file_handling.SimulationType;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety specific to the Forest Fire simulation, sets up the
 * probability for a tree to catch on fire, and the steps for a tree to burn out
 * into an empty patch. Allows Burn and Tree Cells (as well as empty spots where
 * trees have burnt out).
 * 
 * @author maddiebriere
 *
 */
public class FireSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.YELLOW;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;

	private double probCatch;
	private int stepsToBurn;

	public FireSociety(SimulationType sim) {
		super(sim);
	}

	public FireSociety(SimulationData data) {
		super(data);
	}

	@Override
	public void parseRules(RawData data) {
		if (data.getDoubleVariables().size() != 0)
			probCatch = data.getDoubleVariables().get(0);
		if (data.getIntegerVariables().size() != 0)
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
		if (getCurrentCells().size() == 0) {
			return;
		}
		activateTreeCells();
		activateBurnCells();
	}

	private void activateTreeCells() {
		if (!getCurrentCells().containsKey(CellName.TREE_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.TREE_CELL)) {
			if (probCatch > 0 && probCatch <= 1) {
				((TreeCell) c).setProbCatch(probCatch);
			}
		}
	}

	private void activateBurnCells() {
		if (!getCurrentCells().containsKey(CellName.BURN_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.BURN_CELL)) {
			if (stepsToBurn > 0) {
				((BurnCell) c).setStepsToBurn(stepsToBurn);
			}
		}
	}

}
