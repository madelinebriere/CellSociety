package societal_level;
import cellular_level.Cell;
import cellular_level.SlimeCell;
import data_structures.CellName;
import data_structures.PatchName;
import data_structures.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;
import patch_level.Patch;
import patch_level.SlimePatch;
/**
 * Extension of CellSociety specific to Slime Simulation
 * 
 * Models mold cells that gravitate towards each other when they reach a certain
 * threshold of cAMP (a chemical). Allows SlimeCells.
 * 
 * @author maddiebriere
 *
 */
public class SlimeSociety extends CellSociety {
	private static final Color EMPTY_COLOR = Color.GREEN;
	private static final PatchName PATCH_TYPE = PatchName.SLIME_PATCH;
	private int evaporate;
	private int sniffThresh;
	private int depositRate;
	public SlimeSociety(SimulationData data) {
		super(data);
	}
	public SlimeSociety(SimulationType data) {
		super(data);
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
		activateSlimeCells();
		activateSlimePatches();
	}
	@Override
	public void parseRules(RawData data) {
		if (data.getIntegerVariables().size() < 2) {
			return;
		}
		sniffThresh = data.getIntegerVariables().get(0);
		evaporate = data.getIntegerVariables().get(1);
		depositRate = data.getIntegerVariables().get(2);
	}
	private void activateSlimeCells() {
		if (!getCurrentCells().containsKey(CellName.SLIME_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.SLIME_CELL)) {
			if (sniffThresh > 0) {
				((SlimeCell) c).setSniffThresh(sniffThresh);
			}
			if(depositRate>0){
				((SlimeCell)c).setDeposit(depositRate);
			}
		}
	}
	private void activateSlimePatches() {
		if (getPatches().length == 0) {
			return;
		}
		for (Patch patch : getPatchesAsList()) {
			if (patch.getMyPatchType() == PatchName.SLIME_PATCH) {
				setEvaporate((SlimePatch) patch);
			}
		}
	}
	private void setEvaporate(SlimePatch patch) {
		if (evaporate > 0) {
			patch.setEvaporate(evaporate);
		}
	}
}