package file_handling;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import cellular_level.Cell;

public abstract class SimulationType {

	private static final List<String> UNIVERSAL_DATA_TYPES = Arrays.asList(new String[] {
			"title",
	        "artist",
	        "dimension",
	        "cells"
	    });
	
	
	private List<String> cellData = new ArrayList<String>();																
	private List<String> dataTypes = combineDataTypes();
	private Map<String, String> myDataValues;

	
	public SimulationType(Map<String, String> values, ArrayList<String> cells){
		myDataValues = values;
		cellData = cells;
	}
	
	public String getTitle(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(0));
	}
	
	public String getArtist(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(1));
	}
	
	public int getDimension(){
		return Integer.parseInt(myDataValues.get(UNIVERSAL_DATA_TYPES.get(2)));
	}
	
	
	/**
	 * @return List of data types that all SimulationTypes share
	 */
	protected List<String> getUniversalTypes(){
		return UNIVERSAL_DATA_TYPES;
	}
	
	/**
	 * @return List of data types of SimulationType and the subclass instantiated
	 */
	public List<String> getDataTypes(){
		return dataTypes;
	}
	
	/**
	 * @return List of raw Cell data from file
	 */
	public List<String> getCellData(){
		return cellData;
	}
	
	/**
	 * @return Map of SimulationType's data values
	 */
	public Map<String, String> getDataValues(){
		return myDataValues;
	}
	
	
	/**
	 * Combines List of simulation-specific setting attributes with attributes that all files must contain
	 * 
	 * @return List of all attributes that an XMLParser will look for
	 */
	public abstract List<String> combineDataTypes(); 
	
	/**
	 * Uses List of cell data in String format to create an ArrayList of Cells
	 * 
	 * @return
	 */
	protected abstract ArrayList<Cell> getCells();	
	
}
