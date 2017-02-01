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
	
	
	//private List<String> cellData = new ArrayList<String>();  //Would be used to store each initial cell's string of data
																	//Instead of holding it as one long String in myDataValues
																	//Would have to remove "cells" from UNIVERSAL_DATA_TYPES
	private List<String> dataTypes = setDataTypes();
	private Map<String, String> myDataValues;
	
	public SimulationType(Map<String, String> values){
		myDataValues = values;
	}
	
//	public SimulationType(Map<String, String> values, ArrayList<String> cells){
//		myDataValues = values;
//		cellData = cells
//	}
	
	public String getTitle(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(0));
	}
	
	public String getArtist(){
		return myDataValues.get(UNIVERSAL_DATA_TYPES.get(1));
	}
	
	public int getDimension(){
		return Integer.parseInt(myDataValues.get(UNIVERSAL_DATA_TYPES.get(2)));
	}
	
	public ArrayList<Cell> getCells(){
		String cellDataString = myDataValues.get(UNIVERSAL_DATA_TYPES.get(3));
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		//TODO: implement
		//return createCells();
		
		return cells;
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
	
	protected abstract ArrayList<Cell> createCells();
	//Example implementation of LifeSimulation
	
//	for(String data: cellData){
//		String[] vars = data.split(" ");
//		if(vars[2].toUpperCase().equals("LIVE")){
//			cells.add(new LiveCell(vars[0], vars[1]));
//		}
//		else{
//			cells.add(new DeadCell(vars[0], vars[1]));
//		}
//	}
	
	
}
