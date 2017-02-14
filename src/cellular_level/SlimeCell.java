package cellular_level;
import java.util.ArrayList;
import java.util.List;
import data_structures.CellData;
import data_structures.PatchName;
import javafx.scene.paint.Color;
import patch_level.Patch;
import patch_level.SlimePatch;
import util.Location;
/**
 * Extension of Cell class specific to the Slime simulation.
 * 
 * The SlimeCell leaves most actions to the SlimePatch. It does, however, look
 * within a certain range of vision for a patch of high concentration and
 * gravitate towards that patch if the concentration is over its set "sniff
 * threshold"
 * 
 * @author maddiebriere
 *
 */
public class SlimeCell extends Cell {
	public static final Color SLIME_COLOR = Color.GREENYELLOW;
	public static final int VISION = 5;
	public static final int SNIFF_THRESH = 8;
	public static final int DEPOSIT = 2; // Amount deposited when cell is in
	// patch
	private int sniffThresh; // concentration at which Cell no longer moves
	private int vision; // Radial distance
	private int depositInc; // Units of chemical released
	public SlimeCell() {
		this(0, 0);
	}
	public SlimeCell(int row, int col) {
		this(0, 0, SLIME_COLOR);
	}
	public SlimeCell(int row, int col, Color c) {
		this(row, col, c, SNIFF_THRESH, VISION, DEPOSIT);
	}
	public SlimeCell(int row, int col, Color c, int thresh, int vis, int dep) {
		super(row, col, c);
		sniffThresh = thresh;
		vision = vis;
		depositInc = dep;
	}
	@Override
	public Cell createCopy() {
		SlimeCell copy = new SlimeCell();
		copy.basicCopy(this);
		copy.setSniffThresh(this.getSniffThresh());
		copy.setVision(this.getVision());
		copy.setDeposit(this.getDeposit());
		return copy;
	}
	@Override
	public List<Cell> update(CellData data) {
		List<Cell> toRet = new ArrayList<Cell>();
		moveTowardsSlime(data);
		layCampTrail(data);
		toRet.add(this);
		return toRet;
	}
	private void layCampTrail(CellData data){
		Patch current = data.getCurrentPatch(this);
		current.incrementConcentration(depositInc);
	}
	
	private ArrayList<Patch> getSlimePatches(CellData data) {
		ArrayList<Patch> patches = data.getRadialPatches(this, VISION);
		ArrayList<Patch> slime = new ArrayList<Patch>();
		for (Patch patch : patches) {
			if (patch.getMyPatchType() == PatchName.SLIME_PATCH) {
				slime.add(patch);
			}
		}
		return slime;
	}
	// Takes in an ArrayList of SlimePatches on board
	private void moveTowardsSlime(CellData data) {
		ArrayList<Patch> slime = getSlimePatches(data);
		if (slime.size() == 0) {
			return;
		}
		Location target = data.getTargetLocation(slime, this, sniffThresh);
		toWalkOrNot(target, data);
		Location newSpot = data.spotNearTarget(target, this);
		if (newSpot != null) {
			this.setMyLocation(newSpot);
		}
	}
	
	private void toWalkOrNot(Location target, CellData data){
		if (target == null) {
			randomWalk(data);
			return;
		}
	}
	private void randomWalk(CellData data) {
		Location loc = data.getAvailableNeighbor(this);
		if (loc != null)
			this.setMyLocation(loc);
	}
	public int getSniffThresh() {
		return sniffThresh;
	}
	public void setSniffThresh(int sniffThresh) {
		this.sniffThresh = sniffThresh;
	}
	public int getVision() {
		return vision;
	}
	public void setVision(int vision) {
		this.vision = vision;
	}
	
	public int getDeposit(){
		return depositInc;
	}
	
	public void setDeposit(int dep){
		this.depositInc = dep;
	}
}