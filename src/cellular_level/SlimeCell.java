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
	
	private ArrayList<SlimePatch> getSlimePatches(CellData data) {
		ArrayList<Patch> patches = data.getRadialPatches(this, VISION);
		ArrayList<SlimePatch> slime = new ArrayList<SlimePatch>();
		for (Patch patch : patches) {
			if (patch.getMyPatchType() == PatchName.SLIME_PATCH) {
				slime.add((SlimePatch) patch);
			}
		}
		return slime;
	}

	// Takes in an ArrayList of SlimePatches on board
	private void moveTowardsSlime(CellData data) {
		ArrayList<SlimePatch> slime = getSlimePatches(data);
		if (slime.size() == 0) {
			return;
		}
		Location target = getTargetLocation(slime);
		toWalkOrNot(target, data);
		Location newSpot = spotNearTarget(target, data);
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

	private Location getTargetLocation(ArrayList<SlimePatch> slime) {
		Location newSpot = null;
		int maxChem = sniffThresh;
		double minDist = 1000; // random large number
		for (SlimePatch spot : slime) {
			if (greaterConcentration(maxChem, spot) || equalButCloser(maxChem, minDist, spot)) {
				newSpot = spot.getMyLocation();
				maxChem = spot.getConcentration();
				minDist = this.getDistance(spot);
			}
		}
		return newSpot;
	}

	private Location spotNearTarget(Location target, CellData data) {
		ArrayList<Location> neighbors = new ArrayList<Location>(data.getAvailableNeighbors(this));
		if (neighbors.size() == 0) {
			return null;
		}
		if(target == null){
			return null;
		}
		Location toRet = neighbors.get(0);
		for (Location loc : neighbors) {
			if (loc.distance(target) < toRet.distance(target)) {
				toRet = loc;
			}
		}
		return toRet;
	}

	private boolean greaterConcentration(int maxChem, SlimePatch spot) {
		return spot.getConcentration() > maxChem;
	}

	private boolean equalConcentration(int maxChem, SlimePatch spot) {
		return spot.getConcentration() == maxChem;
	}

	private boolean equalButCloser(int maxChem, double minDistance, SlimePatch spot) {
		return equalConcentration(maxChem, spot) && this.getDistance(spot) < minDistance;
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