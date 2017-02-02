package file_handling;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {

	public static final String SIMULATION_ATTRIBUTE = "simulation";
	private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();
	
	
	
//	public SimulationType getSimulation(File dataFile){
//		 Element root = getRootElement(dataFile);
//		 
//		 Map<String, String> data = new HashMap<String, String>();
//		 
//	}
	
	// Get root element of an XML file
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
	
	private static DocumentBuilder getDocumentBuilder () {
		try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
	}
	
}
