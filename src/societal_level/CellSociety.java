package societal_level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import cellular_level.*;
import file_handling.*;
import data_structures.*;
import javafx.scene.paint.Color;
import util.BorderChooser;
import util.CellGenerator;
import util.NeighborsChooser;
import neighbors.*;
import borders.*;


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
	
	private SimulationName name;
	private CellShape myShape;
	private Dimensions mySize;
	private TreeMap<CellName, List<Cell>> currentCells;
	private Color emptyColor;
	private Neighbors neighbors;
	private Border border;
	private CellName defaultCell;
	
	/**
	 * Default 
	 */

	public CellSociety(){
		this(generateDefaultData());
	}
	public CellSociety(SimulationData sim) {
		setMyShape(sim.getShape());
		setSize(sim.getDimensions());
		setBorder(BorderChooser.chooseBorder(sim));
		setNeighbors(NeighborsChooser.chooseNeighbors(border, sim.getShape()));
		makeCells(sim);
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
		/**Color emptyColor = getEmptyColor();
		for (int i = 0; i < getX(); i++) {
			for (int j = 0; j < getY(); j++) {
				toRet[i][j] = emptyColor;
			}
		}
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
		}**/
		return toRet;
		//TODO: Fix this to account for non-positive indices
	}

	

	/**
	 *	Step function (update) -- Applies the current rules of the simulation
	 * To each of the current Cells. Sorts the current cells by Cell-defined preference
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
		shuffleCurrentCells();
		stepAllCells(getAllEmptyCells());
		return getCurrentColors();
	}

	
	/**
	 * Assumptions made here:
	 * 	-> Correct cell types have been given 
	 * 	-> Dimensions are even (no incomplete rows)
	 * 
	 * @param sim
	 * @return
	 */
	public List<Cell> makeCells(SimulationData sim);
	

	/**
	 * EmptyCells getter
	 * 
	 * @return List of all emptyCells in the currentCells
	 */
	protected List<EmptyCell> getAllEmptyCells() {
		try{
			ArrayList<EmptyCell> toRet = new ArrayList<EmptyCell>();
			for(Cell c: currentCells.get(CellName.EMPTY_CELL)){
				toRet.add((EmptyCell)c);
			}
			return toRet;
		}
		catch(Exception e){
			return new ArrayList<EmptyCell>();
		}
	}

	/**
	 * TARGETED EmptyCells getter
	 * 
	 * @return List of all emptyCells in the given List
	 */
	public List<EmptyCell> getEmptyCells(List<Cell> possibleOptions) {
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
	private void stepAllCells(List<EmptyCell> available) {
		TreeMap<CellName, List<Cell>> nextGen = updateAllCells(available);
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
	private TreeMap<CellName, List<Cell>> updateAllCells(List<EmptyCell> available) {
		TreeMap<CellName, List<Cell>> newMap = new TreeMap<CellName, List<Cell>>();
		for (CellName name : getCurrentCells().keySet()) {
			for(Cell cell: getCurrentCells().get(name)){
				ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(cell, available));
				removeUsedSpots(available, cells);
				for(Cell updated: cells){
					CellName n = CellGenerator.getCellName(updated);
					if(newMap.containsKey(n)){
						newMap.get(n).add(updated);
					}
					else{
						ArrayList<Cell> newList = new ArrayList<>();
						newList.add(updated);
						newMap.put(n, newList);
					}
				}
			}
		}
		return newMap;
	}

	/**
	 * ABSTRACT BECAUSE EACH SUBCLASS MUST DEFINE THE 
	 * 
	 * Updates the cell by passing it a new CellData object with defined
	 * available spots and knowledge of this specific Cell Society
	 * 
	 * @param c
	 *            Cell for update
	 * @param available
	 *            List of available cells
	 * @return All updated cells from cell updates (new baby cells, moved cells,
	 *         etc.)
	 */
	private List<Cell> updateCell(Cell c, List<EmptyCell> available){
		return c.update(new CellData(this, available));
	}
	/**
	 * Iterate through given List and fill any cell-less locations in a
	 * size x size grid with a new EmptyCell
	 * 
	 * @param nextGen
	 *            The List of cells to be padded
	 */
	public void fillEmptySpots(TreeMap <CellName, List<Cell>> cells) {
		ArrayList<Cell>allCells = new ArrayList<Cell>();
		for(CellName n: cells.keySet()){
			allCells.addAll(cells.get(n));
		}
		int[][] filled = new int[getX()][getY()];
		for (Cell c : allCells) {
			filled[c.getMyRow()][c.getMyCol()] += 1;
		}
		ArrayList<Cell> newEmpty = new ArrayList<Cell>();
		for (int i = 0; i < getX(); i++) {
			for (int j = 0; j < getY(); j++) {
				if (filled[i][j] == 0)
					newEmpty.add(new EmptyCell(i, j));
			}
		}
		ArrayList<Cell>oldEmpty = cells.get(CellName.EMPTY_CELL);
		for(Cell c: newEmpty){
			
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
	private void removeUsedSpots(List<EmptyCell> available, ArrayList<Cell> newCells) {
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
	public List<Cell> neighbors(Cell c){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		//return getNeighbors(c);
		return neighbors;
	}
	
	private static SimulationData generateDefaultData(){
		HashMap<CellName, CellRatio> m = new HashMap<CellName, CellRatio>();
		m.put(CellName.FISH_CELL, new CellRatio(0.5));
		m.put(CellName.SHARK_CELL, new CellRatio(0.2));
		m.put(CellName.EMPTY_CELL, new CellRatio(0.3));
		CellRatioMap r = new CellRatioMap(m);
		SocietyData s = new SocietyData(false, Color.WHITE, r, CellName.EMPTY_CELL);
		BoardData b = new BoardData ();
		SimulationData d = new SimulationData(SimulationName.WATER_SOCIETY, b, s);
		return d;
	}

	/**
	 * Shuffle current cells to avoid certain cells consistently updating before
	 * others
	 */
	private void shuffleCurrentCells() {
		for(CellName name: getCurrentCells().keySet()){
			ArrayList<Cell> shuffle = new ArrayList<Cell>(getCurrentCells().get(name));
			Collections.shuffle(shuffle);
			getCurrentCells().put(name, shuffle);
		}
	}



	public TreeMap<CellName,List<Cell>> getCurrentCells() {
		return currentCells;
	}

	public void setCurrentCells(TreeMap<CellName, List<Cell>> current) {
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

	public Neighbors getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Neighbors neighbors) {
		this.neighbors = neighbors;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public CellName getDefaultCell() {
		return defaultCell;
	}

	public void setDefaultCell(CellName defaultCell) {
		this.defaultCell = defaultCell;
	}

	public SimulationName getName() {
		return name;
	}
	public void setName(SimulationName name) {
		this.name = name;
	}
	
	
	
	
	
	
	
	
}