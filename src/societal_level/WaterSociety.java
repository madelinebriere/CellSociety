package societal_level;

import cellular_level.Cell;
import cellular_level.FishCell;
import cellular_level.SharkCell;
import data_structures.CellName;
import data_structures.PatchName;
import data_structures.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety, specific to the Wa-Tor world simulation, allows
 * Fish and Shark Cells.
 * 
 * @author maddiebriere
 *
 */

public class WaterSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.BLUE;
	private static final PatchName PATCH_TYPE = PatchName.EMPTY_PATCH;

	private int fishBreed;
	private int sharkBreed;
	private int sharkDie;

	public WaterSociety(SimulationData sim) {
		super(sim);
	}

	public WaterSociety(SimulationType sim) {
		super(sim);
	}

	@Override
	public void parseRules(RawData data) {
		if (data.getIntegerVariables().size() == 0) {
			return;
		}
		fishBreed = data.getIntegerVariables().get(0);
		sharkBreed = data.getIntegerVariables().get(1);
		sharkDie = data.getIntegerVariables().get(2);
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
		activateFishCells();
		activateSharkCells();
	}

	private void activateFishCells() {
		if (!getCurrentCells().containsKey(CellName.FISH_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.FISH_CELL)) {
			setFishBreed((FishCell) c);
		}
	}

	private void activateSharkCells() {
		if (!getCurrentCells().containsKey(CellName.SHARK_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.SHARK_CELL)) {
			setSharkBreed((SharkCell) c);
			setSharkStarve((SharkCell) c);
		}
	}

	private void setSharkBreed(SharkCell c) {
		if (sharkBreed > 0) {
			c.setStepsToBreed(sharkBreed);
		}
	}

	private void setSharkStarve(SharkCell c) {
		if (sharkDie > 0) {
			c.setStepsToStarve(sharkDie);
		}
	}

	private void setFishBreed(FishCell c) {
		if (fishBreed > 0) {
			c.setStepsToBreed(fishBreed);
		}
	}

}
