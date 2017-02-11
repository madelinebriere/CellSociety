package data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import cellular_level.Cell;
import cellular_level.EmptyCell;
import societal_level.CellSociety;
import util.Location;

/**
 * This class is used to limit the amount of control given to a single Cell
 * 
 * A CopySociety is passed to a cell to give it the information it needs about
 * the entire board, but limit access to ONLY NEIGHBORING CELLS
 * 
 * @author maddiebriere
 *
 */

public class CellData {
	private CellSociety mySociety;
	private List<EmptyCell> available;
	
	public CellData(CellSociety s){
		mySociety=s;
		available=null;
	}

	public CellData(CellSociety s, List<EmptyCell> validSpots) {
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
	public List<Cell> getNeighbors(Cell c) {
		return mySociety.neighbors(c);
	}

	public Map <CellName, List<Cell>> getCurrentCellsCopy() {
		return copy(mySociety.getCurrentCells());
	}

	public List<Cell> getAvailableCellsCopy() {
		return copy(available);
	}

	public int countSameNeighbors(Cell center) {
		int sameCount = 0;
		for (Cell c : mySociety.neighbors(center)) {
			if (c.getMyState() != null && c.getMyState().equals(center.getMyState())) {
				sameCount++;
			}
		}
		return sameCount;
	}

	public int countDiffNeighbors(Cell center) {
		return getNumberNeighbors(center) - countSameNeighbors(center);
	}

	public int countNonEmptyNeighbors(Cell center) {
		List<Cell> neighbors = mySociety.neighbors(center);
		List<EmptyCell> emptyNeighbors = mySociety.getEmptyCells(neighbors);
		if (neighbors.size() == 0) {
			return 0;
		}
		if (emptyNeighbors.size() == 0) {
			return neighbors.size();
		}
		return neighbors.size() - emptyNeighbors.size();
	}

	public Cell getCopyAvailableCell() {
		if (available.size() == 0) {
			return null;
		}
		Random randy = new Random();
		int emptyIndex = randy.nextInt(available.size());
		ArrayList<Cell> openCells = new ArrayList<Cell>(available);
		return openCells.get(emptyIndex).createCopy();
	}

	public Location getCopyAvailableLocation() {
		Cell c = getCopyAvailableCell();
		if (c == null)
			return null;
		return c.getMyLocation();

	}

	public Cell getCopyAvailableNeighbor(Cell c) {
		if (getAvailableNeighbor(c) != null)
			return getAvailableNeighbor(c).createCopy();
		else
			return null;
	}

	public Cell getAvailableNeighbor(Cell c) {
		if (available.size() == 0) {
			return null;
		}
		Random randy = new Random();
		ArrayList<EmptyCell> availableNeighbors = new ArrayList<EmptyCell>(available);
		availableNeighbors.retainAll(mySociety.neighbors(c));
		if (availableNeighbors.size() == 0) {
			return null;
		}
		int emptyIndex = randy.nextInt(availableNeighbors.size());
		return availableNeighbors.get(emptyIndex);
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
	
	/**
	 * Basic List copy function to limit actual access to items
	 * 
	 * @param toCopy
	 *            List to be copied
	 * @return Copy of List filled with COPIES OF EACH ITEM
	 */
	private Map<CellName,List<Cell>> copy(Map<CellName, List<Cell>> toCopy) {
		TreeMap <CellName,List<Cell>> ret = new TreeMap<CellName, List<Cell>>();
		for(CellName name: toCopy.keySet()){
			ret.put(name, copy(toCopy.get(name)));
		}
		return ret;

	}

}