/**
 * Defines the variables and methods that are needed to store, interpret, and 
 * return the data behind any Simulation. The abstract class holds the title,
 * artist, dimension, and ArrayList of initial cells. The subclasses are in charge
 * of defining which initial settings need to be stored and how to interpret the
 * Strings of initial cell data. This is a Read-Only data structure.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import cellular_level.Cell;
import javafx.scene.paint.Color;
import societal_level.CellSociety;

public abstract class SimulationType {

	private static final List<String> UNIVERSAL_DATA_TYPES = Arrays.asList(new String[] {
			"title",
	        "author",
	        "dimension",
	        "cells"
	    });
	
	private List<String> cellData;																
	private List<String> dataTypes = combineDataTypes();
	private Map<String, String> myDataValues;
	private Color emptyColor;
	private List<Class> cellTypes;
	private List<Class> defaultCellTypes;

	
	public SimulationType(Map<String, String> values, List<String> cells){
		myDataValues = values;
		cellData = cells;
	}
	
	/**
	 *	Called by CellSociety when it wants to initialize its own variables according 
	 *	to simulation type
	 */
	public void initializeSociety(CellSociety c){
		initializeSocietyVariables();
		initializeCellTypes();
		initializeDefaultCells();
		c.setCurrentCells(getCells());
		c.setSize(getDimension());
		c.setEmptyColor(getEmptyColor());
		c.setCellTypes(cellTypes);
		c.setDefaultCellTypes(defaultCellTypes);
	}
	
	public abstract void initializeSocietyVariables();
	public abstract void initializeCellTypes();
	public abstract void initializeDefaultCells();
	
	public String getTitle(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(0));
	}
	
	public String getAuthor(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(1));
	}
	
	public int getDimension(){
		return Integer.parseInt(myDataValues.get(UNIVERSAL_DATA_TYPES.get(2)));
	}
	
	/**
	 * Uses List of cell data in String format to create an ArrayList of Cells.
	 * 
	 * @return
	 */
	public abstract List<Cell> getCells();	
	
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
	protected abstract List<String> combineDataTypes();

	public Color getEmptyColor() {
		return emptyColor;
	}

	public void setEmptyColor(Color emptyColor) {
		this.emptyColor = emptyColor;
	}

	public List<Class> getCellTypes() {
		return cellTypes;
	}

	public void setCellTypes(List<Class> cellTypes) {
		this.cellTypes = cellTypes;
	}

	public List<Class> getDefaultCellTypes() {
		return defaultCellTypes;
	}

	public void setDefaultCellTypes(List<Class> defaultCellTypes) {
		this.defaultCellTypes = defaultCellTypes;
	} 
	
	
	
	
	
	
}
