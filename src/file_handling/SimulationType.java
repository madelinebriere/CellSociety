/**
 * Defines the variables and methods that are needed to store, interpret, and 
 * return the data behind any Simulation. The abstract class holds the title,
 * artist, dimension, and ArrayList of initial cells. The subclasses are in charge
 * of defining which initial settings need to be stored and how to interpret the
 * Strings of initial cell data.
 * 
 * @author Stone Mathers
 */
package file_handling;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Arrays;
import cellular_level.Cell;
import data_structures.CellName;
import data_structures.PatchName;
import javafx.scene.paint.Color;
import patch_level.Patch;
import util.CellGenerator;
import util.Location;
import util.PatchGenerator;

public abstract class SimulationType {
	private static final List<String> UNIVERSAL_DATA_TYPES = Arrays.asList(new String[] {
			"title",
	        "author",
	        "dimension",
	        "cells"
	    });
	protected static final int NAME_INDEX = 2;
	private static final String ERROR_BUNDLE = "resources/Errors";
	
	private List<String> cellData;																
	protected List<String> dataTypes;
	protected List<String> settingTypes = Arrays.asList(new String[] {""});
	private Map<String, String> myDataValues;
	public ResourceBundle myResources = ResourceBundle.getBundle(ERROR_BUNDLE);
	
	public SimulationType(Map<String, String> values, List<String> cells){
		myDataValues = values;
		cellData = cells;
		dataTypes = combineDataTypes();
	}
	
	public String getTitle(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(0));
	}
	
	public String getAuthor(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(1));
	}
	
	public int getDimension(){
		try{
			return Integer.parseInt(myDataValues.get(UNIVERSAL_DATA_TYPES.get(2)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), UNIVERSAL_DATA_TYPES.get(2)));
		}
	}
	
	/**
	 * Uses List of cell data in String format to create an ArrayList of Cells.
	 * There must be a valid tag at the beginning of the list for the correct
	 * filling method to be chosen.
	 * 
	 * @return
	 */
	private TreeMap<CellName,List<Cell>> getShiftedCells(){
		TreeMap<CellName, List<Cell>> cells = new TreeMap<CellName, List<Cell>>();
		try{
			for(String data: this.getCellData()){
				String[] vars = data.split(" ");
				int row = Integer.parseInt(vars[0]);
				int col = Integer.parseInt(vars[1]);
			
				if(row >= this.getDimension() || col >= this.getDimension()){
					throw new XMLException(myResources.getString("InvalidCellLocation"));
				}
			
				Location loc = new Location(row,col);
				CellName name = CellGenerator.getCellName(vars[NAME_INDEX].toUpperCase());
				Cell newCell = CellGenerator.newCell(name);
				newCell.setMyLocation(loc);
				if(cells.containsKey(name)){
					cells.get(name).add(newCell);
				}
				else{
					ArrayList<Cell> list = new ArrayList<>();
					list.add(newCell);
					cells.put(name, list);
				}
			}
		}catch(Exception e){
			throw new XMLException(e, myResources.getString("InvalidCellData"));
		}
		return cells;
	}
	
	public abstract TreeMap<PatchName, List<Patch>> getShiftedPatches();
	
	
	public TreeMap<PatchName, List<Patch>> getShiftedPatches(PatchName patchType, Color color){
		TreeMap<PatchName, List<Patch>> patches = new TreeMap<PatchName, List<Patch>>();
		TreeMap<CellName, List<Cell>> cells = getShiftedCells();
		ArrayList<Patch>fill = new ArrayList<Patch>();
		for(CellName name: cells.keySet()){
			for(Cell cell: cells.get(name)){
				Patch newPatch = PatchGenerator.newPatch(patchType);
				newPatch.setMyCell(cell);
				newPatch.setMyColor(color);
				newPatch.setMyLocation(cell.getMyLocation());
				fill.add(newPatch);
			}
		}
		patches.put(patchType, fill);
		return patches;
	}
	
	/**
	 * @return List of raw Cell data from file.
	 */
	public List<String> getCellData(){
		List<String> copy = new ArrayList<String>();
		copy.addAll(cellData);
		return copy;
	}
	
	/**
	 * @return List of data types that all SimulationTypes share.
	 */
	protected List<String> getUniversalTypes(){
		return UNIVERSAL_DATA_TYPES;
	}
	
	/**
	 * @return List of data types of SimulationType and the subclass instantiated.
	 */
	public List<String> getDataTypes(){
		List<String> copy = new ArrayList<String>();
		copy.addAll(dataTypes);
		return copy;
	}
	
	protected Map<String, String> getDataValues(){
		return myDataValues;
	}
	
	
	/**
	 * Combines List of simulation-specific setting attributes with attributes that all files must contain.
	 * 
	 * @return List of all attributes that an XMLParser will look for.
	 */
	protected List<String> combineDataTypes(){
		List<String> data = new ArrayList<String>();
		data.addAll(getUniversalTypes());
		data.addAll(settingTypes);
		return data;
	}
}