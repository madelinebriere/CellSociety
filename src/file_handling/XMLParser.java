/**
 * TODO: Add description
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

	public static final String SIMULATION_ATTRIBUTE = "simulation";
	public static final String CELLS = "cells";
	private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();	
	private static final String[] POSSIBLE_SIM_STRINGS = {"game of life", "segregation", "fire", "water"};
	private static final SimulationType[] POSSIBLE_SIM_TYPES = {
			new LifeSimulation(null, null),
			new PopSimulation(null, null),
			new FireSimulation(null, null),
			new WaterSimulation(null, null)
	};
	
	private HashMap<String, SimulationType> mySimulationMap = new HashMap<String, SimulationType>();
	
	public XMLParser(){
		fillSimulationMap();
	}
	
	public SimulationType getSimulation(File dataFile){
		 Element root = getRootElement(dataFile);		 
		 HashMap<String, String> data = new HashMap<String, String>();
		 ArrayList<String> cells = new ArrayList<String>();
		 SimulationType tempSim = chooseSimulationType(root);
		 
		 for(String field: tempSim.getDataTypes()){
			 if(field.equals(CELLS)){
				 fillCellData(root, cells);
			 } else {
				 data.put(field, getTextValue(root, field));
			 }
		 }
		  

		 return createSimulation(tempSim, data, cells);
		 
		 //return (tempSim.getClass())(new SimulationType(data, cells));
		 
	}

	/**
	 * Instantiates a subclass of SimulationType that matches
	 * the class of the given SimulationType, using the given Map and List.
	 * 
	 * @param sim
	 * @param map
	 * @param list
	 * @return
	 */
	private SimulationType createSimulation(SimulationType sim, Map<String, String> map, List<String> list) { //TODO: Refactor
		if(sim instanceof LifeSimulation){
			return new LifeSimulation(map, list);
		}else if(sim instanceof PopSimulation){
			return new PopSimulation(map, list);
		}else if(sim instanceof FireSimulation){
			return new FireSimulation(map, list);
		}else if(sim instanceof WaterSimulation){
			return new WaterSimulation(map, list);
		}else{
			throw new RuntimeException();
		}
	}

	/**
     * Gets the root element of the given XML file.
     * 
     * @param xmlFile
     * @return
     */
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * Creates a DocumentBuilder.
	 * 
	 * @return
	 */
	private static DocumentBuilder getDocumentBuilder () {
		try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
	}
	
    /**
     * Gets the value of the given Element's text.
     * 
     * @param e
     * @param tagName
     * @return
     */
    private String getTextValue (Element e, String tagName) { //TODO: Refactor this and fillCellData
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            throw new RuntimeException();
        }
    }
	
	/**
	 * Gets the attribute of the root element of the XML file and
	 * uses it to return the desired SimulationType.
	 * 
	 * @param root element of the XML file
	 * @return subclass of SimulationType identified by the XML file
	 */
	private SimulationType chooseSimulationType(Element root){
		String type = root.getAttribute(SIMULATION_ATTRIBUTE);
		return mySimulationMap.get(type);
		
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
			throw new RuntimeException();
		}
	}
	
	/**
	 * Fills a List with the initial cell states given in the XML file 
	 * 
	 * @param list
	 */
	private void fillCellData(Element e, List<String> list) {  //TODO: Refactor this and getTextValue
		NodeList nodeList = e.getElementsByTagName(CELLS);
		if (nodeList != null && nodeList.getLength() > 0) {
            for(int i = 0; i < nodeList.getLength(); i++){
            	list.add(nodeList.item(i).getTextContent());
            }
        }
        else {
            throw new RuntimeException();
        }
	}
}
