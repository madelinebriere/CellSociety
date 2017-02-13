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
import java.util.HashMap;

import cellular_level.Cell;
import data_structures.BoardData;
import data_structures.BorderType;
import data_structures.CellName;
import data_structures.CellShape;
import data_structures.Dimensions;
import data_structures.RawData;
import data_structures.SimulationName;
import util.CellGenerator;
import util.Location;

public abstract class SimulationType {
	private static final List<String> UNIVERSAL_DATA_TYPES = Arrays.asList(new String[] {
			"title",
	        "author",
	        "dimension",
	        "border",
	        "cellShape",
	        "cells"
	    });
	private static final List<String> DEFAULT_UNIVERSAL_DATA = Arrays.asList(new String[] {
			"Simulation",
	        "Default Generator",
	        "10",
	        "FINITE",
	        "SQUARE"
	    });
	protected static final int NAME_INDEX = 2;
	protected static final String CELLS = "cells";
	protected static final String DEFAULT_CELL_TYPE = "percentage";
	private static final String ERROR_BUNDLE = "resources/Errors";
	
	protected BoardData boardData;
	protected List<String> cellData;
	protected List<String> defaultCellData;
	protected List<String> dataTypes;
	protected List<String> settingTypes = Arrays.asList(new String[] {""});
	protected Map<String, String> dataDefaults;
	protected List<String> settingDefaults = Arrays.asList(new String[] {""});
	protected Map<String, String> myDataValues;
	public ResourceBundle myResources = ResourceBundle.getBundle(ERROR_BUNDLE);
	
	
	public SimulationType(Map<String, String> values, List<String> cells){
		myDataValues = values;
		cellData = cells;
	}
	
	public String getTitle(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(0));
	}
	
	public String getAuthor(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(1));
	}
	
	public Integer getDimension(){
		try{
			return Integer.parseInt(myDataValues.get(UNIVERSAL_DATA_TYPES.get(2)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), UNIVERSAL_DATA_TYPES.get(2)));
		}
	}
	
	public String getBorder(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(3));
	}
	
	public String getCellShape(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(4));
	}
	
	/**
	 * Uses List of cell data in String format to create an ArrayList of Cells.
	 * There must be a valid tag at the beginning of the list for the correct
	 * filling method to be chosen.
	 * 
	 * @return
	 */
	public Map<CellName,List<Cell>> getCells(){
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
				System.out.println(name);
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
			e.printStackTrace(); //TODO
			throw new XMLException(e, myResources.getString("InvalidCellData"));
		}
		return cells;
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
	 * @return List of defaults that all SimulationTypes share.
	 */
	protected List<String> getUniversalDefaults(){
		return DEFAULT_UNIVERSAL_DATA;
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
	
	/**
	 * Combines List of simulation-specific setting defaults with defaults that all files must contain.
	 * 
	 * @return List of all defaults for a specific simulation.
	 */
	protected Map<String, String> combineDefaultData(){
		Map<String, String> data = new HashMap<String, String>();

		mapLists(data, UNIVERSAL_DATA_TYPES, DEFAULT_UNIVERSAL_DATA);
		mapLists(data, settingTypes, settingDefaults);

		return data;
	}
	
	/**
	 * Takes two Lists, one of keys and one of values, and a Map. The given Map
	 * is filled with mappings from the keys to the values.
	 * 
	 * @param map to be filled
	 * @param keys
	 * @param values
	 */
	private void mapLists(Map<String, String> map, List<String> keys, List<String> values){
		int element = 0;  //Used to account for "cells" being skipped, as well as having to access a List
		for(String tag: keys){
			if(!tag.equals(CELLS)){
				map.put(tag, values.get(element));
				element++;
			}
		}
	}
	
	public BoardData getBoardData() {
		return boardData;
	}
	
	/**
	 * Uses the information held by the SimulationType to create a BoardData object.
	 * 
	 * @return BoardData object containing information passed in by user.
	 */
	protected BoardData createBoardData(){
		SimulationName name = getSimulationName();
		Dimensions dim = new Dimensions(this.getDimension(), this.getDimension());
		BorderType borderType = BorderType.valueOf(getBorder().toUpperCase());
		CellShape shape = CellShape.valueOf(getCellShape().toUpperCase());
		List<Integer> intData = getIntegerData();
		List<Double> dblData = getDoubleData();
		RawData data = new RawData(intData, dblData);
		
		return new BoardData(name, dim, borderType, shape, data);
	}
	
	/**
	 * Puts all Integer-valued initial settings into a List and returns it.
	 * 
	 * @return List of Integers
	 */
	protected abstract List<Integer> getIntegerData();
	
	/**
	 * Puts all Double-valued initial settings into a List and returns it.
	 * 
	 * @return List of Doubles
	 */
	protected abstract List<Double> getDoubleData();
	
	protected abstract SimulationName getSimulationName();
	
	/**
	 * Creates a map for the Simulation's data, using default values when parameters aren't passed in.
	 * 
	 * @return Map containing the Simulation's data.
	 */
	protected Map<String, String> createDataMap(Map<String, String> values){
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(String tag: this.dataTypes){
			if(!tag.equals(CELLS)){ //Cells must be handled separately
				if(values.containsKey(tag)){
					map.put(tag, values.get(tag));
				}else{
					map.put(tag, this.dataDefaults.get(tag));
				}
			}
		}
		return map;
	}
	
	/**
	 * Takes the raw cell data from the file and uses a CellDataGenerator
	 * and CellDataDecoder to translate it to location-based data that
	 * SimulationTypes can use.
	 * 
	 * @param data - Raw cell data from file
	 * @return List of cell data strings formatted as an x-coordinate, y-coordinate, and cell type
	 */
	protected List<String> createCellList(List<String> data){
		if(getCellData().size() > 0){  //If given cell data is invalid, then the default cell data will be used
			return data;
		}else{
			CellDataGenerator gen = new CellDataGenerator(defaultCellData, this.getDimension());
			CellDataDecoder dec = new CellDataDecoder(gen);
			return dec.decodeData(DEFAULT_CELL_TYPE);
		}		
	}
}
