package data_structures;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import cellular_level.Cell;
import patch_level.Patch;
import societal_level.CellSociety;
import util.Location;
/**
 * This class is used to limit the amount of control given to a single Cell
 * 
 * A CopySociety is passed to a cell to give it the information it needs about
 * the entire board, but limit access to ONLY NEIGHBORING CELLS.
 * 
 * This class does most of the processing for the cell so that it doesn't have
 * to iterate through the Cells and Patches to make decisions
 * 
 * @author maddiebriere
 *
 */
public class CellData {
	private CellSociety mySociety;
	private List<Location> available;
	public CellData(CellSociety s) {
		mySociety = s;
		available = null;
	}
	public CellData(CellSociety s, List<Location> validSpots) {
		mySociety = s;
		available = validSpots;
	}
	public int getX() {
		return mySociety.getX();
	}
	public int getY() {
		return mySociety.getY();
	}
	public int getNumberNeighbors(Cell c) {
		return mySociety.neighbors(c).size();
	}
	/**
	 * Call will be specific to CellSociety --> correct type of neighbors
	 */
	public List<Patch> getNeighbors(Cell c) {
		return mySociety.neighbors(c);
	}
	public List<Location> getNeighborsLocations(Cell c) {
		return getPatchLocations(getNeighbors(c));
	}
	public Map<CellName, List<Cell>> getCurrentCellsCopy() {
		return copy(mySociety.getCurrentCells());
	}
	public Patch getCurrentPatch(Cell c) {
		if (mySociety.validSpot(c.getMyLocation())) {
			return mySociety.getPatches()[c.getMyCol()][c.getMyRow()];
		}
		return null;
	}
	public Patch[][] getCurrentPatches() {
		return mySociety.getPatches();
	}
	public Patch[][] getCurrentPatchesCopy() {
		return copy(mySociety.getPatches());
	}
	public ArrayList<Patch> getRadialPatches(Cell origin, int radius) {
		ArrayList<Patch> radial = new ArrayList<Patch>();
		for (int i = 0; i < mySociety.getPatches().length; i++) {
			for (int j = 0; j < mySociety.getPatches()[0].length; j++) {
				Location loc1 = origin.getMyLocation();
				Location loc2 = mySociety.getPatches()[i][j].getMyLocation();
				if (loc1.distance(loc2) <= radius) {
					radial.add(mySociety.getPatches()[i][j]);
				}
			}
		}
		return radial;
	}
	public int countSameNeighbors(Cell center) {
		int sameCount = 0;
		for (Patch p : mySociety.neighbors(center)) {
			if (p.getMyCell() != null && p.getMyCell().getMyState() != null
					&& p.getMyCell().getMyState().equals(center.getMyState())) {
				sameCount++;
			}
		}
		return sameCount;
	}
	public int countDiffNeighbors(Cell center) {
		return getNumberNeighbors(center) - countSameNeighbors(center);
	}
	public int countNonEmptyNeighbors(Cell center) {
		List<Patch> neighbors = mySociety.neighbors(center);
		List<Location> emptyNeighbors = mySociety.getEmptyCells(getPatchLocations(neighbors));
		if (neighbors.size() == 0) {
			return 0;
		}
		if (emptyNeighbors.size() == 0) {
			return neighbors.size();
		}
		return neighbors.size() - emptyNeighbors.size();
	}
	private List<Location> getPatchLocations(List<Patch> patches) {
		ArrayList<Location> locs = new ArrayList<Location>();
		for (Patch p : patches) {
			locs.add(p.getMyLocation());
		}
		return locs;
	}
	public Location getAvailableSpot() {
		if (available.size() == 0) {
			return null;
		}
		Random randy = new Random();
		int emptyIndex = randy.nextInt(available.size());
		ArrayList<Location> openCells = new ArrayList<Location>(available);
		return openCells.get(emptyIndex);
	}
	public Location getAvailableNeighbor(Cell c) {
		if (available.size() == 0) {
			return null;
		}
		Random randy = new Random();
		ArrayList<Location> availableNeighbors = new ArrayList<Location>(getAvailableNeighbors(c));
		if (availableNeighbors.size() == 0) {
			return null;
		}
		int emptyIndex = randy.nextInt(availableNeighbors.size());
		return availableNeighbors.get(emptyIndex);
	}
	public List<Location> getAvailableNeighbors(Cell c) {
		ArrayList<Location> availableNeighbors = new ArrayList<Location>(available);
		availableNeighbors.retainAll(getPatchLocations(mySociety.neighbors(c)));
		return availableNeighbors;
	}
	/**
	 * Basic List copy function to limit actual access to items
	 * 
	 * @param list
	 *            List to be copied
	 * @return Copy of List filled with COPIES OF EACH ITEM
	 */
	private <T extends Cell> List<Cell> copy(List<T> list) {
		ArrayList<Cell> copy = new ArrayList<Cell>();
		for (Cell c : list) {
			Cell newCell = c.createCopy();
			copy.add(newCell);
		}
		return copy;
	}
	public Location spotNearTarget(Location target, Cell c) {
		ArrayList<Location> neighbors = new ArrayList<Location>(getAvailableNeighbors(c));
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
	
	public Location getTargetLocation(ArrayList<Patch> patches, Cell c, int thresh) {
		Location newSpot = null;
		int maxChem = thresh;
		double minDist = 1000; // random large number
		for (Patch spot : patches) {
			if (greaterConcentration(maxChem, spot) || equalButCloser(maxChem, minDist, spot, c)) {
				newSpot = spot.getMyLocation();
				maxChem = spot.getConcentration();
				minDist = c.getDistance(spot);
			}
		}
		return newSpot;
	}
	private boolean greaterConcentration(int maxChem, Patch spot) {
		return spot.getConcentration() > maxChem;
	}
	private boolean equalConcentration(int maxChem, Patch spot) {
		return spot.getConcentration() == maxChem;
	}
	private boolean equalButCloser(int maxChem, double minDistance, Patch spot, Cell c) {
		return equalConcentration(maxChem, spot) && c.getDistance(spot) < minDistance;
	}
	
	/**
	 * Basic List copy function to limit actual access to items
	 * 
	 * @param toCopy
	 *            List to be copied
	 * @return Copy of List filled with COPIES OF EACH ITEM
	 */
	private Map<CellName, List<Cell>> copy(Map<CellName, List<Cell>> toCopy) {
		TreeMap<CellName, List<Cell>> ret = new TreeMap<CellName, List<Cell>>();
		for (CellName name : toCopy.keySet()) {
			ret.put(name, copy(toCopy.get(name)));
		}
		return ret;
	}
	private Patch[][] copy(Patch[][] toCopy) {
		Patch[][] copy = new Patch[toCopy.length][toCopy[0].length];
		for (int i = 0; i < toCopy.length; i++) {
			for (int j = 0; j < toCopy[0].length; j++) {
				copy[i][j] = toCopy[i][j].createCopy();
			}
		}
		return copy;
	}
}