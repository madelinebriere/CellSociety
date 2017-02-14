/**
 * This class was designed to take the information given by the user's
 * file about the initial Cells, and perform the calculations necessary
 * to generate data that a SimulationType can work with. It was created
 * primarily to hide implementation details from XMLParser.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class CellDataGenerator {	
	public static final String ERROR_BUNDLE = "resources/Errors";
	public static final ResourceBundle myResources = ResourceBundle.getBundle(ERROR_BUNDLE);
	
	private List<String> cellData;
	private int dimension;

	public CellDataGenerator(List<String> data, int dim){
		cellData = data;
		dimension = dim;
	}
	
	/**
	 * If the file gives the cell data in this format, then 
	 * no changes need to be made and it is simply returned.
	 * Created primarily to make XMLParser more readable
	 * as it decides how to take the user's data and generate
	 * data that a SimulationType can use.
	 * 
	 * @return
	 */
	public List<String> generateLocationData(){
		List<String> data = new ArrayList<String>();
		data.addAll(cellData);
		return data;
	}
	
	/**
	 * Generates a list of Cells and their location given
	 * a Cell type and the number of those Cells desired.
	 * Assumes number of cells comes first, then cell type.
	 * 
	 * By making this public method call a private method,
	 * the same procedure can be done within this class without
	 * having to manipulate cellData, but the user does not need to 
	 * pass in their List<String> again.
	 * 
	 * @return List of cell locations and states
	 */
	public List<String> generateNumberData(){
		return generateNumberData(cellData);
	}
	
	/**
	 * Generates a list of Cells and their location given
	 * a Cell type and the number of those Cells desired.
	 * Assumes number of cells comes first, then cell type.
	 * 
	 * @return List of cell locations and states
	 */
	private List<String> generateNumberData(List<String> dataList){
		Boolean[][] filled = new Boolean[dimension][dimension];
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				filled[x][y] = false;
			}
		}
		List<String> result = new ArrayList<String>();
		int totalCells = 0;
		
		for(String data: dataList){
			String[] splitData = data.split(" ");
			totalCells += Integer.parseInt(splitData[0]);
			if(totalCells > dimension*dimension){ //Ensures the user didn't ask for more cells than possible, which would create an infinite loop
				throw new XMLException(myResources.getString("TooManyCells"));
			}
			for(int i = 0; i < Integer.parseInt(splitData[0]); i++){  //A new Cell is added until the desired number is reached
				placeCellRandomly(splitData[1], result, filled);
			}
		}
		
		return result;
	}
	
	/**
	 * Generates a list of Cells and their location given
	 * a Cell type and the probability of that cell occurring.
	 * Assumes probability of cell comes first, then cell type.
	 * 
	 * @return List of cell locations and states
	 */
	public List<String> generateProbabilityData(){
		List<String> result = new ArrayList<String>();
		HashMap<String, Double> probMap = new HashMap<String, Double>();
		double probTotal = 0;
		
		for(String data: cellData){  //Map each Cell to its probability
			String[] splitData = data.split(" ");
			probMap.put(splitData[1], Double.parseDouble(splitData[0]));
			probTotal += Double.parseDouble(splitData[0]);
		}
		
		if(probTotal > 1){  //Ensures that the given probabilities are valid (Do not add up to more than 1)
			throw new XMLException(myResources.getString("InvalidProbabilities"));
		}else{
			String[] cellNames = createNamesArray(probMap);
			placeCellsWithProbability(result, cellNames);			
		}
		
		return result;
	}
	
	/**
	 * Generates a list of Cells and their location given
	 * a Cell type and the percentage of the grid that cell
	 * should fill.
	 * Assumes percentage of cell comes first, then cell type.
	 * 
	 * To prevent duplication of code, the percentage data is
	 * converted to number data, then passed to generateNumberData.
	 * 
	 * @return List of cell locations and states
	 */
	public List<String> generatePercentageData(){
		List<String> midList = new ArrayList<String>();
		
		for(String data: cellData){
			String[] splitData = data.split(" ");			
			midList.add(percentToNumber(splitData[0]) + " " + splitData[1]);
		}
		
		return generateNumberData(midList);
	}
	
	private String percentToNumber(String numString) {
		Double percent = Double.parseDouble(numString);
		Integer num = (int)(Math.round((dimension*dimension)*percent));
		return num.toString();
	}

	/**
	 * Generates random x and y values at which to place a Cell and creates
	 * a new String representing that data, placing it in the given List.
	 * 
	 * @param cellName - name of Cell to be placed
	 * @param list
	 * @param filled - 2d-array representing grid of cells, true if a cell is there, false if not
	 */
	private void placeCellRandomly(String cellName, List<String> list, Boolean[][] filled){
		Random randy = new Random();
		
		boolean placed = false;	 //Ensures that a Cell is placed
		while(!placed){
			int x = randy.nextInt(dimension);
			int y = randy.nextInt(dimension);
			if(!filled[x][y]){  //Prevents two Cells from being placed in the same location
				list.add(x + " " + y + " " + cellName);
				filled[x][y] = true;
				placed = true;
			}
		}
	}
	
	/**
	 * Determines what Cell, if any, should be placed in the grid,
	 * according to a probability distribution.
	 * 
	 * @param list - list to be filled with data
	 * @param names - Array of cell names distributed by specific probabilities
	 */
	private void placeCellsWithProbability(List<String> list, String[] names){
		Random randy = new Random();
		
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				int random = randy.nextInt(100);
				if(!names[random].equals("")){  //If it equals null, then this no Cell will be placed (empty)
					list.add(x + " " + y + " " + names[random]);
				}
			}
		}
	}
	
	/**
	 * Creates and returns a String[] holding the names of 
	 * Cell types according to the probability that each occurs.
	 * 
	 * @param map - Map holding the probabilities of each Cell
	 * @return
	 */
	private String[] createNamesArray(Map<String, Double> map){
		String[] names = new String[100];
		int start = 0;
		int end;
		
		for(String cell: map.keySet()){ //Fills an array with Cell types according to their probability
			end = (int)(Math.round(start + (100 * map.get(cell)))); //Sets probability to an integer between 1 and 100
			for(int i = start; i < end; i++){
				names[i] = cell;
			}
			start = end;
		}
		
		for(int i = start; i < 100; i++){ //Fill remaining indices with an empty String
			names[i] = "";
		}
		
		return names;
	}
	
}
