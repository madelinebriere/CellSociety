package file_handling;



import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PopUp{

	public Stage myStage;
	
	public PopUp(Stage stage){
		myStage = stage;
	}
	
	public SimulationType getSimulation(){
		XMLParser parser = new XMLParser();
		return parser.getSimulation(getFile());	
	}

	private File getFile(){
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select XML File");
		return chooser.showOpenDialog(myStage);
	}
	
}
