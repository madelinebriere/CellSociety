
package societal_level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import GUI.SocietyMaker;

import java.util.HashMap;

import cellular_level.*;
import file_handling.*;
import data_structures.*;
import javafx.scene.paint.Color;
import util.CellGenerator;
import util.Location;
import util.NeighborsChooser;
import util.PatchGenerator;
import util.Tuple;
import neighbors.*;
import patch_level.*;

/**
 * This class represents a SOCIETY of Cells, and keeps track of the current
 * cells, the size (width and height) of the society, and the color of empty
 * cells in this society. It also keeps track of the Patches in the society (the
 * background tiles) which can have functionality, color, etc.
 * 
 * This abstract class has many subclasses, one for each simulation type --
 * these subclasses set simulation specific variables (like satisfied threshold
 * in population society).
 * 
 * @author maddiebriere
 */

public abstract class CellSociety {

	private SimulationName name;
	private CellShape myShape;
	private Dimensions mySize;
	private TreeMap<CellName, List<Cell>> currentCells;
	private Patch[][] patches;
	private BorderType border;
	private SimulationData mySimulationData;
	private boolean getAllNeighbors;

	/**
	 * Default set-up is a Wa-Tor world simulation
	 */
	public CellSociety() {
		this(generateDefaultData());
	}

	/**
	 * Take in SimulationData (user related input) and set current cells
	 * according to given ratios
	 * 
	 * @param sim
	 *            SimulationData from front-end
	 */
	public CellSociety(SimulationData sim) {
		this.mySimulationData = sim;
		setBoardData(sim.getData());
		setCurrentCells(makeCells(sim));
		setPatches(setPatches());
	}

	/**
	 * Take in SimulationType (file related input) and set current cells
	 * according to given locations
	 * 
	 * @param sim
	 *            SimulationType from file-reader
	 */
	public CellSociety(SimulationType sim) {
		setBoardData(sim.getBoardData());
		setCurrentCells(sim.getCells());
		setPatches(setPatches());
	}

	/**
	 * Sets data-related common between both the SimulationType AND the
	 * SimulationData (SimulationName, general variables, Cell shape,
	 * dimensions, border type)
	 * 
	 * @param data
	 *            BoardData that holds current settings
	 */
	public void setBoardData(BoardData data) {
		parseRules(data.getRaw());
		setName(data.getName());
		setMyShape(data.getShape());
		setSize(data.getDimensions());
		setBorder(data.getBorder());
		setGetAllNeighbors(data.isGetAllNeighbors());
	}

	/**
	 * Step function (update) -- Applies the current rules of the simulation To
	 * each of the current Cells. Sorts the current cells by Cell-defined
	 * preference (decided with the compareTo method in each Cell), putting
	 * certain types of Cells first on the list the update. This is used in
	 * WaterWorld to have the Sharks update before the fish so that the eaten
	 * fish can be removed before the fish update. After the sorting, it calls
	 * guidedStep to shuffle the cells and update each, PROVIDING the Cells any
	 * available positions to move into, breed into, etc. (passing null)
	 * 
	 * @return 2D Array of current Cell colors
	 */
	public Tuple<Color[][], Dimensions> step() {
		applySettings();
		stepAllCells(getAllEmptyCells());
		updatePatches();
		return new Tuple<Color[][], Dimensions>(getCurrentColors(), this.getSimulationData().getDimensions());
	}


	/**
	 * This method should be implemented by every CellSociety in order to parse
	 * RawData passed to the society. If it does not define anything, the cells
	 * will just use their own default variables.
	 *
	 * The information in the RawData object should be passed in the correct
	 * order, as defined in each parseRules() implementation (this order also
	 * matches the initialization of the variables at the top of the Society
	 * class)
	 */
	public abstract void parseRules(RawData data);

	/**
	 * @return The color set for the patches (empty cells) in this particular
	 *         simulation
	 */
	public abstract Color getEmptyColor();

	/**
	 * @return the desired Patch Type for the specific society
	 */
	public abstract PatchName getPatchType();

	/**
	 * Resets the patches (background) with the current step by updating (taking
	 * Patch-specific action) and then reseting the Cell held by the patch to
	 * the new Cell layout
	 */
	public void updatePatches() {
		for (int i = 0; i < getPatches().length; i++) {
			for (int j = 0; j < getPatches()[0].length; j++) {
				getPatches()[i][j].update();
				getPatches()[i][j].setMyCell(null); // reset cells
			}
		}
		resetPatchCells();

	}

	/**
	 * Reset patches according to current cells
	 */
	public void resetPatchCells() {
		for (Cell c : getCellsAsList()) {
			getPatches()[c.getMyCol()][c.getMyRow()].setMyCell(c);
		}
	}

	/**
	 * Set cells and patches with current settings (for instance, in PopSociety,
	 * this with apply the satisfied threshold to every current cell) so that
	 * updates will be based upon the correct settings and not default values
	 */
	protected abstract void applySettings();

	/**
	 * Use to set the type of patches (empty spots) in the simulation
	 */
	public Patch[][] setPatches() {
		Patch[][] patches = new Patch[getX()][getY()];
		if (getCurrentCells().size() != 0) {
			for (Cell c : getCellsAsList()) {
				if (validSpot(c.getMyLocation())) {
					patches[c.getMyCol()][c.getMyRow()] = createNewPatch(c, c.getMyLocation());
				}
			}
		}
		fillPatchList(patches);
		return patches;
	}

	public void fillPatchList(Patch[][] patchyPatches) {
		ArrayList<Location> locs = getValidLocations(getSize());
		for (Location l : locs) {
			if (patchyPatches[l.getMyCol()][l.getMyRow()] == null) {
				patchyPatches[l.getMyCol()][l.getMyRow()] = createNewPatch(null, l);
			}
		}
	}

	/**
	 * Create a new patch according to the current simulation's settings
	 * 
	 * @param patchCell
	 *            The Cell held by this new patch
	 * @param loc
	 *            The location of this new patch
	 * @return The new patch
	 */
	private Patch createNewPatch(Cell patchCell, Location loc) {
		Patch newPatch = PatchGenerator.newPatch(getPatchType());
		newPatch.setMyPatchType(getPatchType());
		newPatch.setMyColor(getEmptyColor());
		newPatch.setMyLocation(loc);
		newPatch.setMyCell(patchCell);
		return newPatch;
	}

	public boolean validSpot(Location loc) {
		return (loc.getMyRow() < getY() && loc.getMyCol() < getX());
	}

	/**
	 * Main method for interaction between front and back end
	 * 
	 * @return A 2D array of colors, with location by index, to be used to
	 *         create the visualization of the current simulation status
	 */
	public Color[][] getCurrentColors() {
		Color[][] toRet = new Color[getX()][getY()]; // rows along y axis, cols
														// along x axis
		for (int i = 0; i < getX(); i++) {
			for (int j = 0; j < getY(); j++) {
				toRet[i][j] = patches[i][j].getShadedColor(); // Start will all
																// cells color
																// by patch
			}
		}
		for (Cell c : getCellsAsList()) {
			if (validSpot(c.getMyLocation()))
				toRet[c.getMyCol()][c.getMyRow()] = c.getMyState();
		}
		return toRet;
	}

	/**
	 * Assumptions made here: -> Correct cell types have been given ->
	 * Dimensions are even (no incomplete rows)
	 * 
	 * @param sim
	 *            SimulationData object with set CellRatioMap, from which cells
	 *            must be generated. For instance, if 100 cells are expected and
	 *            the ratios given are .6 and .4, then 60 cells of the first
	 *            type and 40 cells of the second type will be created and
	 *            returned in a map
	 * 
	 * @return Map of new cells, organized by CellName
	 */
	public Map<CellName, List<Cell>> makeCells(SimulationData sim) {

		ArrayList<Location> validLocations = getValidLocations(sim.getDimensions());
		if (validLocations.size() == 0) {
			return null;
		}
		Map<CellName, Integer> cellNums = getCellDistribution(sim, validLocations);
		return distributeCells(cellNums, validLocations);
	}

	private Map<CellName, Integer> getCellDistribution(SimulationData sim, ArrayList<Location> validLocations) {
		Map<CellName, CellRatio> ratios = sim.getRatios().getMapOfCellsRatios();
		Map<CellName, Integer> cellNums = new HashMap<CellName, Integer>();
		int total = validLocations.size();
		for (CellName name : ratios.keySet()) {
			int numPlace = (int) (total * ratios.get(name).getRatio());
			cellNums.put(name, numPlace);
		}
		return cellNums;
	}

	private TreeMap<CellName, List<Cell>> distributeCells(Map<CellName, Integer> cellNums,
			ArrayList<Location> validLocations) {
		TreeMap<CellName, List<Cell>> toRet = new TreeMap<CellName, List<Cell>>();
		Random randomizer = new Random();
		for (CellName name : cellNums.keySet()) {
			ArrayList<Cell> singleType = new ArrayList<Cell>();
			for (int i = 0; i < cellNums.get(name); i++) {
				int index = randomizer.nextInt(validLocations.size());
				Location newLoc = validLocations.remove(index);
				Cell newCell = CellGenerator.newCell(name);
				newCell.setMyLocation(newLoc);
				singleType.add(newCell);
			}
			toRet.put(name, singleType);
		}
		return toRet;
	}

	private ArrayList<Location> getValidLocations(Dimensions valid) {
		ArrayList<Location> locs = new ArrayList<Location>();
		for (int i = 0; i < getY(); i++) {
			for (int j = 0; j < getX(); j++) {
				locs.add(new Location(i, j));
			}
		}
		return locs;
	}

	/**
	 * Locations getter
	 * 
	 * @return List of all Locations in the currentCells
	 */
	protected List<Location> getAllEmptyCells() {
		return getEmptyCells(getValidLocations(getSize()));
	}

	/**
	 * TARGETED Locations getter
	 * 
	 * @return List of all Locations in the given List
	 */
	public List<Location> getEmptyCells(List<Location> possibleOptions) {
		ArrayList<Location> loc = new ArrayList<Location>();
		for (int i = 0; i < getPatches().length; i++) {
			for (int j = 0; j < getPatches()[0].length; j++) {
				Patch currPatch = getPatches()[i][j];
				if (currPatch.getMyCell() == null && possibleOptions.contains(currPatch.getMyLocation())) {
					loc.add(getPatches()[i][j].getMyLocation());
				}
			}
		}
		return loc;
	}

	/**
	 * Updates all cells given a list of available spots (for moving, breeding,
	 * etc.) Fill spots left empty after update Reset current cells to the
	 * updated cells
	 * 
	 * @param available
	 *            Available spots for movement, breeding, etc.
	 */
	private void stepAllCells(List<Location> available) {
		TreeMap<CellName, List<Cell>> nextGen = (TreeMap<CellName, List<Cell>>) updateAllCells(available);
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
	private Map<CellName, List<Cell>> updateAllCells(List<Location> available) {
		TreeMap<CellName, List<Cell>> newMap = new TreeMap<CellName, List<Cell>>();
		for (CellName name : getCurrentCells().keySet()) {
			for (Cell cell : getCurrentCells().get(name)) {
				ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(cell, available));
				removeUsedSpots(available, cells);
				for (Cell updated : cells) {
					CellName n = CellGenerator.getCellName(updated);
					if (newMap.containsKey(n)) {
						(newMap.get(n)).add(updated);
					} else {
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
	private List<Cell> updateCell(Cell c, List<Location> available) {
		return c.update(new CellData(this, available));
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
	private void removeUsedSpots(List<Location> available, ArrayList<Cell> newCells) {
		if (available == null || newCells == null || available.size() == 0 || newCells.size() == 0) {
			return;
		}

		for (int i = 0; i < available.size(); i++) {
			for (int j = 0; j < newCells.size(); j++) {
				if (available.get(i).equals(newCells.get(j).getMyLocation())) {
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
	 * @return neighbors of Cell c, either all neighbors or cardinal neighbors
	 */
	public List<Patch> neighbors(Cell c) {
		Neighbors neighbors = NeighborsChooser.chooseNeighbors(border, getMyShape(), getPatches());
		Patch patch = patches[c.getMyCol()][c.getMyRow()];
		if (getAllNeighbors)
			return neighbors.getAllNeighbors(patch);
		else
			return neighbors.getCardinalNeighbors(patch);
	}

	public static SimulationData generateDefaultData() {
		CellRatioMap r = SocietyMaker.getDefaultCellRatioValues(SimulationName.WATER_SOCIETY);
		BoardData data = new BoardData(SimulationName.WATER_SOCIETY);
		data.setShape(CellShape.SQUARE);
		return new SimulationData(data, r);
	}

	public List<Cell> getCellsAsList() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (CellName c : currentCells.keySet()) {
			cells.addAll(currentCells.get(c));
		}
		return cells;
	}

	public List<Patch> getPatchesAsList() {
		ArrayList<Patch> patches = new ArrayList<Patch>();
		for (int i = 0; i < getPatches().length; i++) {
			for (int j = 0; j < getPatches()[0].length; j++) {
				patches.add(getPatches()[i][j]);
			}
		}
		return patches;
	}

	public Dimensions getMySize() {
		return mySize;
	}

	public void setMySize(Dimensions mySize) {
		this.mySize = mySize;
	}

	public Patch[][] getPatches() {
		return patches;
	}

	public void setPatches(Patch[][] patches) {
		this.patches = patches;
	}

	public SimulationData getSimulationData() {
		return this.mySimulationData;
	}

	public TreeMap<CellName, List<Cell>> getCurrentCells() {
		return currentCells;
	}

	public void setCurrentCells(Map<CellName, List<Cell>> map) {
		currentCells = (TreeMap<CellName, List<Cell>>) map;
	}

	public Dimensions getSize() {
		return mySize;
	}

	public void setSize(Dimensions size) {
		this.mySize = size;
	}

	public int getX() {
		return mySize.getX();
	}

	public int getY() {
		return mySize.getY();
	}

	public CellShape getMyShape() {
		return myShape;
	}

	public void setMyShape(CellShape myShape) {
		this.myShape = myShape;
	}

	public BorderType getBorder() {
		return border;
	}

	public void setBorder(BorderType border) {
		this.border = border;
	}

	public SimulationName getName() {
		return name;
	}

	public void setName(SimulationName name) {
		this.name = name;
	}

	public boolean isGetAllNeighbors() {
		return getAllNeighbors;
	}

	public void setGetAllNeighbors(boolean getAllNeighbors) {
		this.getAllNeighbors = getAllNeighbors;
	}

}