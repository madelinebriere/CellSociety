/**
 * This class was created to test the functionality of XMLParser.
 * It uses printing methods from SimulationTypeTester to ensure the
 * only difference are a result of reading in the file.
 * 
 * @author Stone Mathers
 */

package test;

import java.io.File;
import file_handling.*;

public class XMLParserTester {

	public static void main(String[] args) {
		XMLParser parser = new XMLParser();
		File lifeFile = new File("data/LifeSimData.xml");
		File fireFile = new File("data/FireSimData.xml");
		File popFile = new File("data/PopSimData.xml");
		File waterFile = new File("data/WaterSimData.xml");

		SimulationType sim = parser.getSimulation(lifeFile);
		SimulationTypeTester.printLife(sim);

		sim = parser.getSimulation(fireFile);
		SimulationTypeTester.printFire(sim);

		sim = parser.getSimulation(popFile);
		SimulationTypeTester.printPop(sim);

		sim = parser.getSimulation(waterFile);
		SimulationTypeTester.printWater(sim);
	}

}
