/**
 * This class uses a given cell data type (either within an Element or as a String)
 * to determine how the given data should be altered so that a SimulationType can 
 * generate a List of Cells.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CellDataDecoder {

	public static final String CELL_DATA_TYPE = "cellDataType";
	public static final String[] POSSIBLE_CELL_DATA = {"location", "number", "probability", "percentage"};
	private static final String ERROR_BUNDLE = "resources/Errors";
	
	public ResourceBundle myResources = ResourceBundle.getBundle(ERROR_BUNDLE);
	private CellDataGenerator myGenerator;
	
	public CellDataDecoder(CellDataGenerator gen){
		myGenerator = gen;
	}

	
	/**
	 * Determines how the Strings of Cell data should be used to
	 * generate data usable by a SimulationType.
	 * 
	 * @param e
	 */
	public List<String> decodeData(Element e) {
		String cellType = getTextValue(e, CELL_DATA_TYPE).get(0);
		return decodeData(cellType);
	}
	/**
	 * Determines how the Strings of Cell data should be used to
	 * generate data usable by a SimulationType.
	 * 
	 * @param cellType
	 */
	public List<String> decodeData(String cellType) {
		if(cellType.equals(POSSIBLE_CELL_DATA[0])){
			return myGenerator.generateLocationData();
		}else if(cellType.equals(POSSIBLE_CELL_DATA[1])){
			return myGenerator.generateNumberData();
		}else if(cellType.equals(POSSIBLE_CELL_DATA[2])){
			return myGenerator.generateProbabilityData();
		}else if(cellType.equals(POSSIBLE_CELL_DATA[3])){
			return myGenerator.generatePercentageData();
		}else{
			throw new XMLException(myResources.getString("InvalidCellDataType"));
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
    	
        if (nodeList != null && nodeList.getLength() > 0){  //If this is not satisfied, an error is not thrown, because default values are used
        	for(int i = 0; i < nodeList.getLength(); i++){
        		values.add(nodeList.item(i).getTextContent());
            }
        }
        	return values;
    }
}
