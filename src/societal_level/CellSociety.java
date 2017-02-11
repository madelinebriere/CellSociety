package societal_level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Collections;
import java.util.HashMap;

import cellular_level.*;
import file_handling.*;
import data_structures.*;
import javafx.scene.paint.Color;
import util.BorderChooser;
import util.CellGenerator;
import util.Location;
import util.NeighborsChooser;
import util.PatchGenerator;
import neighbors.*;
import patch_level.*;
import borders.*;


/**
 * TODO:
 * 1) Stone: Add BoardData initialization
 * 2) Talha: Make GUI work/ decide how to initial using new design
 * 3) Implement new Color method with cells AND patches
 * 4) Implement subclasses of CellSociety
 * 5) Finish Slime Simulation
 * 6) Assess viability of parseRules() / RawData structure
 * 7) ... Get rid of errors
 * 
 * 
 * 
 * OLD:
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

public abstract class CellSociety {
	
	private SimulationName name;
	private CellShape myShape;
	private Dimensions mySize;
	private TreeMap<CellName, List<Cell>> currentCells;
	private Patch[][] patches;
	private Neighbors neighbors;
	private Border border;
	
	/**
	 * Default 
	 */

	public CellSociety(){
		this(generateDefaultData());
	}
	public CellSociety(SimulationData sim) {
		setBoardData(sim.getData());
		setCurrentCells(makeCells(sim));
		setPatches();
	}
	
	public CellSociety(SimulationType sim) {
		setBoardData(sim.getBoardData());
		setCellsFromFile(sim);
		setPatches();
	}
	
	public void setBoardData(BoardData data){
		parseRules(data.getRaw());
		setName(data.getName());
		setMyShape(data.getShape());
		setSize(data.getDimensions());
		setBorder(BorderChooser.chooseBorder(data));
		setNeighbors(NeighborsChooser.chooseNeighbors(border, data.getShape()));
	}
	
	/**
	 * This is only the DEFAULT version of this method, to ensure that no variables
	 * are left uninitialized -- the default is NO PARSING at all -- this must
	 * be implemented by subclasses
	 * 
	 *	This method should be implemented by every CellSociety in order to parse
	 *	RawData passed to the society. If it does not define anything 
	 *
	 */
	//NOTE: Is this annotation a good way to avoid use in superclass?
	@Deprecated
	public void parseRules(RawData data){
		setVariablesToDefault();
	}
	
	/**
	 * Returns the color set for the patches (empty cells)
	 */
	public abstract Color getEmptyColor();
	
	/**
	 * Return the desired Patch Type for the specific society
	 */
	public abstract PatchName getPatchType();
	
	/**
	 * Set Society specific variables to default (backup)
	 */
	public abstract void setVariablesToDefault();
	
	
	public void updatePatches(){
		for(int i=0; i<getPatches().length; i++){
			for(int j=0; j<getPatches()[0].length; j++){
				getPatches()[i][j].update();
				getPatches()[i][j].setMyCell(null); //reset cells
			}
		}
		resetPatchCells();
		
	}
	
	/**
	 * Reset patches according to current cells
	 */
	public void resetPatchCells(){
		for(Cell c: getCellsAsList()){
			getPatches()[c.getMyCol()][c.getMyRow()].setMyCell(c);
		}
	}

	/**
	 * Use to set the type of patches (empty spots) in the simulation
	 */
	public Patch[][] setPatches(){
		Patch[][] patches = new Patch[getY()][getX()];
		if(getCurrentCells().size()!=0){
			for(Cell c: getCellsAsList()){
				if(validSpot(c.getMyLocation())){
					Patch newPatch = PatchGenerator.newPatch(getPatchType());
					newPatch.setMyColor(getEmptyColor());
					newPatch.setMyLocation(c.getMyLocation());
					newPatch.setMyCell(c);
					patches[c.getMyCol()][c.getMyRow()]=newPatch;
				}
			}
		}
		fillPatchList(patches);
		return patches;
	}
	
	public void fillPatchList(Patch[][] patchyPatches){
		ArrayList<Location> locs = getValidLocations(getSize());
		for(Location l: locs){
			if(patchyPatches[l.getMyCol()][l.getMyRow()]==null){
				Patch newPatch = PatchGenerator.newPatch(getPatchType());
				newPatch.setMyColor(getEmptyColor());
				newPatch.setMyLocation(l);
				newPatch.setMyCell(null);
				patchyPatches[l.getMyCol()][l.getMyRow()] = newPatch;
			}
		}
	}
	
	private boolean validSpot(Location loc){
		return (loc.getMyRow()<=mySize.getPosYBound() && loc.getMyRow()>=mySize.getNegYBound()) &&
				(loc.getMyCol()<=mySize.getPosXBound() && loc.getMyCol()>=mySize.getNegXBound());
	}
	
	
	
	public void setCellsFromFile(SimulationType sim){
		setCurrentCells(centerCells(sim.getShiftedCells()));
	}
	
	/**
	 * Main method for interaction between front and back end
	 * 
	 * @return A 2D array of colors, with location by index, to be used to
	 *         create the visualization of the current simulation status
	 */
	public Color[][] getCurrentColors() {
		Color[][] toRet = new Color[getY()][getX()]; //rows along y axis, cols along x axis
		Color emptyColor = getEmptyColor();
		for (int i = 0; i < getY(); i++) {
			for (int j = 0; j < getX(); j++) {
				toRet[i][j] = emptyColor;	//Start will all cells color by patch
			}
		}
		for (Cell c : getCellsAsList()) {
			int newRow = c.getMyRow() - mySize.getNegYBound();
			int newCol = c.getMyCol() - mySize.getNegXBound();
			toRet[newRow][newCol] = c.getMyState();
		}
		return toRet;
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
		updatePatches();
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
	public TreeMap<CellName, List<Cell>> makeCells(SimulationData sim){
		Random randomizer = new Random();
		ArrayList<Location> validLocations = getValidLocations(sim.getDimensions());
		if(validLocations.size()==0){return null;}
		TreeMap<CellName, List<Cell>> toRet = new TreeMap<CellName, List<Cell>>();
		Map<CellName, CellRatio> ratios = sim.getRatios().getMapOfCellsRatios();
		Map<CellName, Integer> cellNums = new HashMap<CellName, Integer>();
		int expectedNum = validLocations.size();
		
		for(CellName name: ratios.keySet()){
			int numPlace = (int)(expectedNum*ratios.get(name).getRatio());
			expectedNum -= numPlace;
			cellNums.put(name, numPlace);
		}
		if(expectedNum!=0){
			for(CellName name: cellNums.keySet()){
				int prev = cellNums.get(name);
				prev+=expectedNum;
				cellNums.put(name, prev);
				break;
			}
		}
		
		for(CellName name: cellNums.keySet()){
			ArrayList<Cell> singleType = new ArrayList<Cell>();
			for(int i=0; i<cellNums.get(name); i++){
				int index = randomizer.nextInt(validLocations.size());
				Location newLoc = validLocations.get(index);
				Cell newCell = CellGenerator.newCell(name);
				newCell.setMyLocation(newLoc);
				singleType.add(newCell);
			}
			toRet.put(name, singleType);
		}
		return toRet;
	}
	
	private ArrayList<Location> getValidLocations(Dimensions valid){
		ArrayList<Location>locs = new ArrayList<Location>();
		for(int i = valid.getNegXBound(); i<=valid.getPosXBound(); i++){
			for(int j=valid.getNegYBound(); j<=valid.getPosYBound(); j++){
				locs.add(new Location(i,j));
			}
		}
		return locs;
	}
	
	
	private TreeMap<CellName,List<Cell>> centerCells(Map<CellName,List<Cell>> shifted){
		for(CellName c: shifted.keySet()){
			for(Cell cell: shifted.get(c)){
				cell.setMyCol(cell.getMyCol()+mySize.getNegXBound());
				cell.setMyRow(cell.getMyRow()+mySize.getNegYBound());
			}
		}
		return new TreeMap<CellName,List<Cell>>(shifted);
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
		for(int i=0; i<getPatches().length; i++){
			for(int j=0; j<getPatches()[0].length; j++){
				Patch currPatch = getPatches()[i][j];
				if(currPatch.getMyCell()==null && possibleOptions.contains(currPatch.getMyLocation())){
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
		TreeMap<CellName, List<Cell>> nextGen = updateAllCells(available);
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
	private TreeMap<CellName, List<Cell>> updateAllCells(List<Location> available) {
		TreeMap<CellName, List<Cell>> newMap = new TreeMap<CellName, List<Cell>>();
		for (CellName name : getCurrentCells().keySet()) {
			for(Cell cell: getCurrentCells().get(name)){
				ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(cell, available));
				removeUsedSpots(available, cells);
				for(Cell updated: cells){
					CellName n = CellGenerator.getCellName(updated);
					if(newMap.containsKey(n)){
						(newMap.get(n)).add(updated);
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
	private List<Cell> updateCell(Cell c, List<Location> available){
		return c.update(new CellData(this, available));
	}
	/**
	 * Iterate through given List and fill any cell-less locations in a
	 * size x size grid with a new Location
	 * 
	 * @param nextGen
	 *            The List of cells to be padded
	 */
	/*public void fillEmptySpots(TreeMap <CellName, List<Cell>> cells) {
		ArrayList<Cell>allCells = new ArrayList<Cell>();
		if(cells==null || cells.size()==0){return;}
		for(CellName n: cells.keySet()){
			allCells.addAll(cells.get(n));
		}
		int[][] filled = new int[getX()][getY()];
		for (Cell c : allCells) {
			int shiftedRow = c.getMyRow() - mySize.getNegYBound();
			int shiftedCol = c.getMyCol() - mySize.getNegXBound();
			filled[shiftedRow][shiftedCol] += 1;
		}
		ArrayList<Cell> newEmpty = new ArrayList<Cell>();
		for (int i = 0; i < getX(); i++) {
			for (int j = 0; j < getY(); j++) {
				if (filled[i][j] == 0){
					int centerRow = i+ mySize.getNegYBound();
					int centerCol = j+ mySize.getNegXBound();
					newEmpty.add(new Location(centerRow, centerCol));
				}
			}
		}
		ArrayList<Cell>oldEmpty  = new ArrayList<Cell>();
		if(cells.get(CellName.EMPTY_CELL) != null && cells.get(CellName.EMPTY_CELL).size()!=0){
			oldEmpty = new ArrayList<Cell>(cells.get(CellName.EMPTY_CELL));	
		}
		oldEmpty.addAll(newEmpty);
		cells.put(CellName.EMPTY_CELL, oldEmpty);
	}*/

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
	 * @return neighbors of Cell c
	 */
	public List<Cell> neighbors(Cell c){
		return getNeighbors(c);
	}
	
	private static SimulationData generateDefaultData(){
		HashMap<CellName, CellRatio> m = new HashMap<CellName, CellRatio>();
		m.put(CellName.FISH_CELL, new CellRatio(0.5));
		m.put(CellName.SHARK_CELL, new CellRatio(0.2));
		m.put(CellName.EMPTY_CELL, new CellRatio(0.3));
		CellRatioMap r = new CellRatioMap(m);
		BoardData data = new BoardData();
		return new SimulationData(data, r);
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
	
	public List<Cell> getCellsAsList(){
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(CellName c: currentCells.keySet()){
			cells.addAll(currentCells.get(c));
		}
		return cells;
	}
	
	
	
	/**
	 * Normal neighbors function, gets any adjacent cells
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Neighbors of Cell c
	 */
	protected List<Cell> getNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : getCellsAsList()) {
			if (isAdjacent(c, possible)) {
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
	
	

	public void setCurrentCells(TreeMap<CellName, List<Cell>> current) {
		currentCells = current;
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

	public SimulationName getName() {
		return name;
	}
	public void setName(SimulationName name) {
		this.name = name;
	}
	
	
}