/**
 * The purpose of XMLParser is to take in and pares an XML file.
 * It then creates a new SimulationType that holds the file's data
 * and returns it in a usable format.
 * 
 * XMLParser currently depends on the user passing it a file that
 * conforms to the formatting rules of our available simulations.
 * 
 * @author Stone Mathers
 * @author Robert C. Duvall
 */

package file_handling;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

	public static final String SIMULATION_ATTRIBUTE = "simulation";
	public static final String DIMENSION = "dimension";
	public static final String CELLS = "cells";
	public static final String CELL = "cell";
	public static final String CELL_DATA_TYPE = "cellDataType";
	public static final String[] POSSIBLE_CELL_DATA = {"location", "number", "probability", "percentage"};
	private static final String[] POSSIBLE_SIM_STRINGS = {"game of life", "population", "fire", "water"};
	private static final SimulationType[] POSSIBLE_SIM_TYPES = {
			new LifeSimulation(null, null),
			new PopSimulation(null, null),
			new FireSimulation(null, null),
			new WaterSimulation(null, null)
	};
	public static final String ERROR_BUNDLE = "resources/Errors";
	public static final ResourceBundle myResources = ResourceBundle.getBundle(ERROR_BUNDLE);
	private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();
	
	private HashMap<String, SimulationType> mySimulationMap = new HashMap<String, SimulationType>();
	
	public XMLParser(){
		fillSimulationMap();
	}
	
	/**
	 * Parses the given XML file and creates and returns a SimulationType
	 * containing the data represented by the file.
	 * 
	 * Assumes that the given file conforms to the formatting rules
	 * of our available simulations. Also depends on SimulationType and
	 * all of its subclasses being correctly implemented and containing
	 * the correct identifiers for each piece of data.
	 * 
	 * @param dataFile File to be parsed.
	 * @return SimulationType holding dataFile's data.
	 */
	public SimulationType getSimulation(File dataFile){
		try{
			Element root = getRootElement(dataFile);		 
			HashMap<String, String> data = new HashMap<String, String>();
			ArrayList<String> cells = new ArrayList<String>();
			SimulationType tempSim = readSimulationType(root);  //Created to access a specific SimulationType's required values
		 
			for(String field: tempSim.getDataTypes()){
				if(!field.equals(CELLS)){  //Cells are uniquely handled before, as they depend on the dimension being retrieved
					data.put(field, getTextValue(root, field).get(0));
				}
			}
			fillCellData(root, cells, Integer.parseInt(data.get(DIMENSION)));
		  
			return createSimulation(tempSim, data, cells);
		}catch(IllegalArgumentException e){
			throw new XMLException(e, myResources.getString("NoFile"));
		}
		 
	}

	/**
	 * Instantiates a subclass of SimulationType that matches
	 * the class of the given SimulationType, using the given Map and List.
	 * 
	 * @param sim Subclass of SimulationType that the user wants to make a new instance of.
	 * @param map Map to be passed into the new SimulationType's constructor.
	 * @param list List to be passed into the new SimulationType's constructor.
	 * @return SimulationType
	 */
	@SuppressWarnings({"rawtypes"})  //Now Class[] parameters does not need to be parameterized
	private SimulationType createSimulation(SimulationType sim, Map<String, String> map, List<String> list) {
		try{
			Class<? extends SimulationType> simClass = sim.getClass();
			Class[] parameters = new Class[2];
			parameters[0] = Map.class;
			parameters[1] = List.class;
			Constructor<? extends SimulationType> ct = simClass.getConstructor(parameters);
			Object[] argList = new Object[2];
			argList[0] = map;
			argList[1] = list;
			return (SimulationType)ct.newInstance(argList);
			} catch (Throwable e){
				throw new XMLException(e);
			}
	}

	/**
     * Gets the root element of the given XML file.
     * 
     * @param xmlFile
     * @return Element
     */
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }
	
	/**
	 * Creates a DocumentBuilder.
	 * 
	 * @return DocumentBuilder
	 */
	private static DocumentBuilder getDocumentBuilder () {
		try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
	}
	
    /**
     * Gets the value of the given Element's text.
     * 
     * @param e
     * @param tagName
     * @return
     */
    private List<String> getTextValue (Element e, String tagName) {
        ArrayList<String> values = new ArrayList<String>();
    	NodeList nodeList = e.getElementsByTagName(tagName);
    	
        if (nodeList != null && nodeList.getLength() > 0){
        	for(int i = 0; i < nodeList.getLength(); i++){
            	values.add(nodeList.item(i).getTextContent());
            }
        }
        else {
            throw new XMLException(String.format(myResources.getString("TagMissing"), tagName));
        }
        
        if(values.size() > 1 && !tagName.equals(CELL)){
        	throw new XMLException(String.format(myResources.getString("RepeatTag"), tagName));
        }else{ 
        	return values;
        }
    }
	
	/**
	 * Gets the attribute of the root element of the XML file and
	 * uses it to return the desired SimulationType.
	 * 
	 * @param root element of the XML file
	 * @return subclass of SimulationType identified by the XML file
	 */
	private SimulationType readSimulationType(Element root){
		String type = root.getAttribute(SIMULATION_ATTRIBUTE);
		if(mySimulationMap.containsKey(type)){
			return mySimulationMap.get(type);
		}else{
			throw new XMLException(myResources.getString("InvalidSimulation"));
		}
		
	}
	
	/**
	 * Fills mySimulationMap, mapping a String representing each possible SimulationType
	 * to an instance of that SimulationType.
	 */
	private void fillSimulationMap(){
		if(POSSIBLE_SIM_STRINGS.length == POSSIBLE_SIM_TYPES.length){  //Provides a check for anybody adding more SimulationTypes
			for(int i = 0; i < POSSIBLE_SIM_STRINGS.length; i++){
				mySimulationMap.put(POSSIBLE_SIM_STRINGS[i], POSSIBLE_SIM_TYPES[i]);
			}
		} else {
			throw new XMLException(myResources.getString("FinalsIncorrect"));
		}
	}
	
	/**
	 * Determines how the Strings of Cell data should be used to
	 * generate data usable by a SimulationType.
	 * 
	 * @param e
	 * @param gen
	 * @param dimension of grid to be filled by Cells
	 */
	private List<String> decodeData(Element e, CellDataGenerator gen) {
		String cellType = getTextValue(e, CELL_DATA_TYPE).get(0);
		if(cellType.equals(POSSIBLE_CELL_DATA[0])){
			return gen.generateLocationData();
		}else if(cellType.equals(POSSIBLE_CELL_DATA[1])){
			return gen.generateNumberData();
		}else if(cellType.equals(POSSIBLE_CELL_DATA[2])){
			return gen.generateProbabilityData();
		}else if(cellType.equals(POSSIBLE_CELL_DATA[3])){
			return gen.generatePercentageData();
		}else{
			throw new XMLException(myResources.getString("InvalidCellDataType"));
		}
	}
	
	/**
	 * Fills a List with the initial cell states given in the XML file.
	 * 
	 * @param e
	 * @param list
	 * @param dimension of grid to be filled by Cells
	 */
	private void fillCellData(Element e, List<String> list, int dimension) {
		List<String> values = getTextValue(e, CELL);
		for(String text: values){
			list.add(text);
		}
		
		CellDataGenerator gen = new CellDataGenerator(list, dimension);
		List<String> newData = decodeData(e, gen);
		
		list.clear();
		list.addAll(newData);
	}
}
