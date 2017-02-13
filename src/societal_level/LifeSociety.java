package societal_level;

import cellular_level.Cell;
import cellular_level.DeadCell;
import cellular_level.LiveCell;
import data_structures.CellName;
import data_structures.PatchName;
import data_structures.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety specific to Game of Life simulation. Allows Dead and
 * Live Cells.
 * 
 * @author maddiebriere
 *
 */

public class LifeSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.WHITE;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;

	private int underpop;
	private int overpop;
	private int numforlive;

	public LifeSociety(SimulationType sim) {
		super(sim);
	}

	public LifeSociety(SimulationData data) {
		super(data);
	}

	@Override
	public void parseRules(RawData raw) {
		if (raw.getIntegerVariables().size() < 2) {
			return;
		}
		underpop = raw.getIntegerVariables().get(0);
		overpop = raw.getIntegerVariables().get(1);
		numforlive = raw.getIntegerVariables().get(2);
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
		activateLiveCells();
		activateDeadCells();
	}

	private void activateLiveCells() {
		if (!getCurrentCells().containsKey(CellName.LIVE_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.LIVE_CELL)) {
			setOverPop((LiveCell) c);
			setUnderPop((LiveCell) c);
		}
	}

	private void activateDeadCells() {
		if (!getCurrentCells().containsKey(CellName.DEAD_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.DEAD_CELL)) {
			if (numforlive > 0) {
				((DeadCell) c).setNumForLive(numforlive);
			}
		}
	}

	private void setOverPop(LiveCell c) {
		if (underpop > 0 && underpop < overpop) {
			c.setUnderpopulation(underpop);
		}
	}

	private void setUnderPop(LiveCell c) {
		if (overpop > underpop) {
			c.setOverpopulation(overpop);
		}
	}
}
