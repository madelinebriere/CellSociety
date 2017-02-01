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
	
	
	//private ArrayList<String> cellData = new ArrayList<String>();  //Would be used to store each initial cell's string of data
																	//Instead of holding it as one long String in myDataValues
																	//Would have to remove "cells" from UNIVERSAL_DATA_TYPES
	private List<String> dataTypes = setDataTypes();
	private Map<String, String> myDataValues;
	
	public SimulationType(Map<String, String> values){
		myDataValues = values;
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
	
	public List<Cell> getCellPositions(){
		String cellData = myDataValues.get(UNIVERSAL_DATA_TYPES.get(3));
		List<Cell> cellPositions = new ArrayList<Cell>();
		
		//TODO: implement
		
		return cellPositions;
	}
	
	
	/**
	 * @return List of data types that all SimulationTypes share
	 */
	protected List<String> getUniversalTypes(){
		return UNIVERSAL_DATA_TYPES;
	}
	
	public List<String> getDataTypes(){
		return dataTypes;
	}
	
	
	/**
	 * Combines List of simulation-specific setting attributes with attributes that all files must contain
	 * 
	 * @return List of all attributes that an XMLParser will look for
	 */
	public abstract List<String> setDataTypes(); 
	
	
}
