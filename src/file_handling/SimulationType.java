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
	
	private ArrayList<String> cellData;																
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
	 * Uses List of cell data in String format to create an ArrayList of Cells
	 * 
	 * @return
	 */
	public abstract ArrayList<Cell> getCells();	
	
	/**
	 * @return List of raw Cell data from file
	 */
	public List<String> getCellData(){
		return cellData;
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
	protected abstract List<String> combineDataTypes(); 
	
}
