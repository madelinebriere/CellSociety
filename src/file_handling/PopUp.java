/**
 * The purpose of this class is to obtain a file from the user, then pass this file to
 * an XMLParser to obtain a SimulationType containing the data that the file holds.
 * It first creates and displays a FileChooser in the given Scene, which prompts the user
 * to select a file. It then uses an XMLParser to obtain the SimulationType.
 * 
 * It is assumed that this file is of the correct format for this program: and XML
 * file holding the correct data. It is also assumed that a file will be selected at all.
 * However, this dependency should be solved by error detection in the XMLParser class,
 * for PopUp does not have to know the specific formatting rules for the file.
 * 
 * @author Stone Mathers
 */

package file_handling;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PopUp {

	private Stage myStage;

	public PopUp(Stage stage) {
		myStage = stage;
	}

	/**
	 * Retrieves the SimulationType represented by the selected file.
	 * 
	 * @return SimulationType
	 */
	public SimulationType getSimulation() {
		XMLParser parser = new XMLParser();
		return parser.getSimulation(getFile());
	}

	/**
	 * Displays a FileChooser which prompts the user to select a file.
	 * 
	 * @return File
	 */
	private File getFile() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select XML File");
		return chooser.showOpenDialog(myStage);
	}

}
