package societal_level;
import cellular_level.Cell;
import cellular_level.SugarCell;
import data_structures.CellName;
import data_structures.PatchName;
import data_structures.RawData;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.paint.Color;
import patch_level.Patch;
import patch_level.SugarPatch;
/**
 * Extension of CellSociety specific to the SugarScape simulation. More
 * complicated in that the initial patches and concentrations must be accounted
 * for as well as initial cells. Allows SugarCells.
 * 
 * @author maddiebriere
 *
 */
public class SugarSociety extends CellSociety {
	public static final Color SUGAR_COLOR = Color.ORANGE;
	public static final PatchName PATCH_TYPE = PatchName.SUGAR_PATCH;
	
	private int sugarMetabolism;
	private int vision;
	private int sugarGrowBack;
	
	public SugarSociety(SimulationData data){
		super(data);
	}
	
	public SugarSociety(SimulationType data){
		super(data);
	}
	
	
	@Override
	public void parseRules(RawData data) {
		if (data.getIntegerVariables().size() < 2) {
			return;
		}
		sugarMetabolism = data.getIntegerVariables().get(0);
		vision = data.getIntegerVariables().get(1);
		sugarGrowBack = data.getIntegerVariables().get(2);
	}
	@Override
	public Color getEmptyColor() {
		return SUGAR_COLOR;
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
		activateSugarCells();
		activateSugarPatches();
	}
	
	private void activateSugarCells() {
		if (!getCurrentCells().containsKey(CellName.SUGAR_CELL)) {
			return;
		}
		for (Cell c : getCurrentCells().get(CellName.SUGAR_CELL)) {
			if (sugarMetabolism > 0) {
				((SugarCell) c).setSugarMetabolism(sugarMetabolism);
			}
			if(vision>0){
				((SugarCell)c).setVision(vision);
			}
		}
	}
	private void activateSugarPatches() {
		if (getPatches().length == 0) {
			return;
		}
		for (Patch patch : getPatchesAsList()) {
			if (patch.getMyPatchType() == PatchName.SUGAR_PATCH && sugarGrowBack>0) {
				((SugarPatch) patch).setSugarGrowBackRate(sugarGrowBack);
			}
		}
	}
	
	public int getSugarMetabolism() {
		return sugarMetabolism;
	}
	public void setSugarMetabolism(int sugarMetabolism) {
		this.sugarMetabolism = sugarMetabolism;
	}
	public int getVision() {
		return vision;
	}
	public void setVision(int vision) {
		this.vision = vision;
	}
	
	
}