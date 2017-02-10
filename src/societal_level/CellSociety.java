package societal_level;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import cellular_level.*;
import file_handling.*;
import data_structures.*;
import javafx.scene.paint.Color;
import util.CellData;
import util.Location;

/**
 * 1) Figure out empty color situation
 * 2) SimulationType incorporation/ constructor?
 * 3) How do I know the size of the cell list I'm creating?
 * 4) Implement neighbors by shape (move all neighbors stuff into that)
 * 		Implement interface by edges? 
 * 5) Check all default variables
 * 6) Double check that step function works
 * 7) General makeCells function
 * 8) Make a class that converts strings -> a default instance of a cell
 */


/**
 * This class represents a SOCIETY of Cells, and keeps track of the current
 * cells, the size (width and height) of the society, and the color of empty
 * cells in this society.
 * 
 * @author maddiebriere
 *
 *         Class made abstract solely to keep users from creating instances of
 *         the superclass
 */

public class CellSociety {
	private static final Color DEFAULT_COLOR = Color.WHITE;
	
	private CellShape myShape;
	private Dimensions mySize;
	private Collection<Cell> currentCells;
	private Color emptyColor;
	
	/**
	 * Default 
	 */
	public CellSociety(){
		this(generateDefaultData());
	}
	
	private static SimulationData generateDefaultData(){
		HashMap<CellName, CellRatio> m = new HashMap<CellName, CellRatio>();
		m.put(CellName.FISH_CELL, new CellRatio(0.5));
		m.put(CellName.SHARK_CELL, new CellRatio(0.2));
		m.put(CellName.EMPTY_CELL, new CellRatio(0.3));
		CellRatioMap r = new CellRatioMap(m);
		SimulationData d = new SimulationData(
				new Dimensions(10,10), SimulationName.WATER_SOCIETY, r, CellShape.SQUARE, true, Color.WHITE 
				);
		return d;
	}
	
	
	public CellSociety(SimulationData sim) {
		myShape = sim.getShape();
		mySize = sim.getDimensions();
		CellRatioMap myRatio = sim.getRatio();
		makeCells(myRatio);
	}
	
	public CellSociety(SimulationType sim) {
		setCurrentCells(sim.getCells());
		setSize(new Dimensions(sim.getDimension(), sim.getDimension()));
	}
	

	/**
	 * Main method for interaction between front and back end
	 * 
	 * @return A 2D array of colors, with location by index, to be used to
	 *         create the visualization of the current simulation status
	 */
	public Color[][] getCurrentColors() {
		Color[][] toRet = new Color[getX()][getY()];
		Color emptyColor = getEmptyColor();
		for (int i = 0; i < getX(); i++) {
			for (int j = 0; j < getY(); j++) {
				toRet[i][j] = emptyColor;
			}
		}
		System.out.println(currentCells.size());
		for (Cell c : currentCells) {
			if (!(c instanceof EmptyCell)) {
				toRet[c.getMyRow()][c.getMyCol()] = c.getMyState();
			} else {
				Color setColor;
				if (getEmptyColor() != null) {
					setColor = getEmptyColor();
				} else {
					setColor = DEFAULT_COLOR;
				}
				toRet[c.getMyRow()][c.getMyCol()] = setColor;

			}
		}
		return toRet;
	}

	

	/**
	 *	Step function (update) -- Sorts the current cells by Cell-defined preference
	 * (decided with the compareTo method in each Cell), putting certain types
	 * of Cells first on the list the update. This is used in WaterWorld to have
	 * the Sharks update before the fish so that the eaten fish can be removed
	 * before the fish update. After the sorting, it calls guidedStep to shuffle
	 * the cells and update each, PROVIDING the Cells any available positions to
	 * move into, breed into, etc. (passing null)
	 * 
	 * @return 2D Array of current Cell colors
	 */
	public Color[][] step() {
		sortByPriorityCurrentCells();
		shuffleCurrentCells();
		stepAllCells(new ArrayList<EmptyCell>(getAllEmptyCells()));
		return getCurrentColors();
	}

	
	public Collection<Cell> makeCells(CellRatioMap r){
		ArrayList<Cell> newCells = new ArrayList<Cell>();
		for(CellName n: r.getMapOfCellsRatios().keySet()){
			Cell newCell = CellGenerator.newCell(n);
		}
		
		return newCells;
	}

	/**
	 * EmptyCells getter
	 * 
	 * @return Collection of all emptyCells in the currentCells
	 */
	protected Collection<EmptyCell> getAllEmptyCells() {
		return getEmptyCells(getCurrentCells());
	}

	/**
	 * TARGETED EmptyCells getter
	 * 
	 * @return Collection of all emptyCells in the given Collection
	 */
	public Collection<EmptyCell> getEmptyCells(Collection<Cell> possibleOptions) {
		ArrayList<EmptyCell> toRet = new ArrayList<EmptyCell>();
		for (Cell c : possibleOptions) {
			if (c instanceof EmptyCell) {
				toRet.add((EmptyCell) c);
			}
		}
		return toRet;
	}

	/**
	 * Updates all cells given a list of available spots (for moving, breeding,
	 * etc.) Fill spots left empty after update Reset current cells to the
	 * updated cells
	 * 
	 * @param available
	 *            Available spots for movement, breeding, etc.
	 */
	private void stepAllCells(ArrayList<EmptyCell> available) {
		ArrayList<Cell> nextGen = updateAllCells(available);
		fillEmptySpots(nextGen);
		setCurrentCells(nextGen);
	}

	/**
	 * Cycle through each cell and add the returned updated cells to an
	 * ArrayList for return
	 * 
	 * @param available
	 *            Available spots
	 * @return ArrayList of updated cells
	 */
	private ArrayList<Cell> updateAllCells(ArrayList<EmptyCell> available) {
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		for (Cell c : getCurrentCells()) {
			ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(c, available));
			removeUsedSpots(available, cells);
			nextGen.addAll(cells);
		}
		return nextGen;
	}

	/**
	 * Updates the cell by passing it a new CellData object with defined
	 * available spots and knowledge of this specific Cell Society
	 * 
	 * @param c
	 *            Cell for update
	 * @param available
	 *            Collection of available cells
	 * @return All updated cells from cell updates (new baby cells, moved cells,
	 *         etc.)
	 */
	private Collection<Cell> updateCell(Cell c, Collection<EmptyCell> available) {
		return c.update(new CellData(this, available));
	}

	/**
	 * Iterate through given Collection and fill any cell-less locations in a
	 * size x size grid with a new EmptyCell
	 * 
	 * @param nextGen
	 *            The Collection of cells to be padded
	 */
	public void fillEmptySpots(Collection<Cell> nextGen) {
		int[][] filled = new int[getX()][getY()];
		for (Cell c : nextGen) {
			filled[c.getMyRow()][c.getMyCol()] += 1;
		}
		for (int i = 0; i < getX(); i++) {
			for (int j = 0; j < getY(); j++) {
				if (filled[i][j] == 0)
					nextGen.add(new EmptyCell(i, j));
			}
		}
	}

	/**
	 * Iterate through both the available spots and the newly updated cells and
	 * remove any repeats from available, so that future cells will not update
	 * thinking that these spots are available (causing conflict)
	 * 
	 * @param available
	 *            Available spots in society
	 * @param newCells
	 *            Cells generated from update
	 */
	private void removeUsedSpots(ArrayList<EmptyCell> available, ArrayList<Cell> newCells) {
		if (available == null || newCells == null || available.size() == 0 || newCells.size() == 0) {
			return;
		}

		for (int i = 0; i < available.size(); i++) {
			for (int j = 0; j < newCells.size(); j++) {
				if (available.get(i).getMyLocation().equals(newCells.get(j).getMyLocation())) {
					available.remove(i);
					if (i > 0)
						i--;
					else
						break;
				}
			}
		}
	}

	/**
	 * Each CellSociety defines a certain way to identify neighbors, depending
	 * on how that society works. The generalized options are: 1) Normal
	 * neighbors (8 nearby cells, no board crossing) 2) Cardinal neighbors (N S
	 * W E cells) 3) Wrapped neighbors (8 nearby cells, board crossing)
	 * Descendant classes can combine these when they define neighbors or come
	 * up with a new way to choose neighbors
	 * 
	 * @param c
	 *            Cell whose neighbors are returned
	 * @return neighbors of Cell c
	 */
	public Collection<Cell> neighbors(Cell c){
		return getNeighbors(c);
	}

	/**
	 * Normal neighbors function, gets any adjacent cells
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Neighbors of Cell c
	 */
	protected Collection<Cell> getNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	/**
	 * Wrapped neighbors function, gets any adjacent cells, INCLUDING those
	 * cells that may be accessible across the board (moving from the far left
	 * to the far right, or the top to the bottom)
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Wrapped neighbors of Cell c
	 */
	protected Collection<Cell> getWrappedNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isAnyAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	/**
	 * Limited neighbors function, gets any adjacent cells IN THE CARDINAL
	 * DIRECTIONS (N, S, E, W)
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Cardinal neighbors of Cell c
	 */
	protected Collection<Cell> getCardinalNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isCardinalAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	/**
	 * Limited neighbors function, gets any adjacent cells IN THE CARDINAL
	 * DIRECTIONS (N, S, E, W), includes movement across the board
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Cardinal neighbors of Cell c
	 */
	protected Collection<Cell> getWrappedCardinalNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isWrappedCardinalAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	/**
	 * Adjacency in common sense
	 * 
	 * @param c1
	 *            target cell 1
	 * @param c2
	 *            target cell 2 (for comparison)
	 * @return true if adjacent, false otherwise
	 */
	public boolean isAdjacent(Cell c1, Cell c2) {
		return isAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isAdjacent(Location l1, Location l2) {
		return isCardinalAdjacent(l1, l2) || (oneAwayVertical(l1, l2) && oneAwayHorizontal(l1, l2));
	}

	/**
	 * Adjacency, but only in cardinal directions (N, S, E, W)
	 * 
	 * @param c1
	 *            target cell 1
	 * @param c2
	 *            target cell 2 (for comparison)
	 * @return true if cardinal adjacent, false otherwise
	 */
	public boolean isCardinalAdjacent(Cell c1, Cell c2) {
		return isCardinalAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	public boolean isCardinalAdjacent(Location l1, Location l2) {
		return (sameColumn(l1, l2) && oneAwayVertical(l1, l2)) || (sameRow(l1, l2) && oneAwayHorizontal(l1, l2));
	}

	/**
	 * Adjacency EVEN ACROSS BOARD (wrapped sides)
	 * 
	 * @param c1
	 *            target cell
	 * @param c2
	 *            target cell for comparison
	 * @return true if wrapped adjacent, false otherwise
	 */
	protected boolean isWrappedAdjacent(Cell c1, Cell c2) {
		return isWrappedAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isWrappedAdjacent(Location l1, Location l2) {
		return (sameColumn(l1, l2) && inRowAcrossBoard(l1, l2)) || (sameRow(l1, l2) && inColAcrossBoard(l1, l2));
	}

	/**
	 * Adjacency EVEN ACROSS BOARD (wrapped sides), includes cardinal cells
	 * 
	 * @param c1
	 *            target cell
	 * @param c2
	 *            target cell for comparison
	 * @return true if wrapped adjacent, false otherwise
	 */
	protected boolean isWrappedCardinalAdjacent(Cell c1, Cell c2) {
		return isWrappedCardinalAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isWrappedCardinalAdjacent(Location l1, Location l2) {
		return isWrappedAdjacent(l1, l2) || isCardinalAdjacent(l1, l2);
	}

	/**
	 * Includes both common adjacency and wrapped adjacency
	 * 
	 * @param c1
	 *            Target cell 1
	 * @param c2
	 *            Target cell 2
	 * @return true if any type of adjacent, false otherwise
	 */
	public boolean isAnyAdjacent(Cell c1, Cell c2) {
		return isAnyAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isAnyAdjacent(Location l1, Location l2) {
		return isWrappedAdjacent(l1, l2) || isAdjacent(l1, l2);
	}

	/**
	 * Check for adjacency by column ACROSS the board
	 * 
	 * @param l1
	 *            location 1
	 * @param l2
	 *            location 2 for comparison
	 * @return true if l1 and l2 are in adjacent columns, only considering
	 *         wrapped motion (e.g., if one cell is in the farthest left column
	 *         and the other is in the farthest right, this method will return
	 *         TRUE).
	 */
	private boolean inColAcrossBoard(Location l1, Location l2) {
		return (l1.getMyCol() == 0 && l2.getMyCol() == getX() - 1) || (l2.getMyCol() == 0 && l1.getMyCol() == getX() - 1);
	}

	/**
	 * Check for adjacency by row ACROSS the board
	 * 
	 * @param l1
	 *            location 1
	 * @param l2
	 *            location 2 for comparison
	 * @return true if l1 and l2 are in adjacent row, only considering wrapped
	 *         motion (e.g., if one cell is in the top row and the other is in
	 *         the lowest row, this method will return TRUE).
	 */
	private boolean inRowAcrossBoard(Location l1, Location l2) {
		return (l1.getMyRow() == 0 && l2.getMyRow() == getY() - 1) || (l2.getMyRow() == 0 && l1.getMyRow() == getY() - 1);
	}

	/**
	 * Check for same column/row
	 */

	private boolean sameColumn(Location l1, Location l2) {
		return l2.getMyCol() == l1.getMyCol();
	}

	private boolean sameRow(Location l1, Location l2) {
		return l2.getMyRow() == l1.getMyRow();
	}

	/**
	 * Check distance in directions
	 */

	private boolean oneAwayVertical(Location l1, Location l2) {
		return oneAwayDown(l1, l2) || oneAwayUp(l1, l2);
	}

	private boolean oneAwayHorizontal(Location l1, Location l2) {
		return oneAwayRight(l1, l2) || oneAwayLeft(l1, l2);
	}

	/**
	 * Check if l1 and l2 are one away in each direction (up, down, right,
	 * left), return true if this is the case, false otherwise
	 */

	private boolean oneAwayUp(Location l1, Location l2) {
		return ((l1.getMyRow() - 1) == l2.getMyRow());
	}

	private boolean oneAwayDown(Location l1, Location l2) {
		return ((l1.getMyRow() + 1) == l2.getMyRow());
	}

	private boolean oneAwayRight(Location l1, Location l2) {
		return ((l1.getMyCol() + 1) == l2.getMyCol());
	}

	private boolean oneAwayLeft(Location l1, Location l2) {
		return ((l1.getMyCol() - 1) == l2.getMyCol());
	}

	/**
	 * Shuffle current cells to avoid certain cells consistently updating before
	 * others
	 */
	private void shuffleCurrentCells() {
		ArrayList<Cell> shuffle = new ArrayList<Cell>(getCurrentCells());
		Collections.shuffle(shuffle);
		setCurrentCells(shuffle);
	}

	/**
	 * Sort current cells by Cell-defined preference (using compareTo method in
	 * the Cell class) for ordered updates
	 */
	private void sortByPriorityCurrentCells() {
		ArrayList<Cell> orderedCells = new ArrayList<Cell>(getCurrentCells());
		Collections.sort(orderedCells, Comparator.reverseOrder());
		setCurrentCells(orderedCells);
	}

	public Collection<Cell> getCurrentCells() {
		return currentCells;
	}

	public void setCurrentCells(Collection<Cell> current) {
		currentCells = current;
	}

	public Color getEmptyColor() {
		return emptyColor;
	}

	public void setEmptyColor(Color emptyColor) {
		this.emptyColor = emptyColor;
	}

	public Dimensions getSize() {
		return mySize;
	}

	public void setSize(Dimensions size) {
		this.mySize = size;
	}

	public int getX(){
		return mySize.getX();
	}
	
	public int getY(){
		return mySize.getY();
	}

	public CellShape getMyShape() {
		return myShape;
	}

	public void setMyShape(CellShape myShape) {
		this.myShape = myShape;
	}
	
	
}