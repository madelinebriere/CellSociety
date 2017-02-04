package cellsociety_team04;

import GUI.GUIMain;
import javafx.application.Application;
import javafx.stage.Stage;
import societal_level.*;

public class Main extends Application {
	private static final String TITLE = "Team 04 - Cell Society";

	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		CellSociety model = new WaterSociety();
		GUIMain display = new GUIMain(model);
		
		//
		
		stage.setTitle(TITLE);
        // add our user interface components to Frame and show it
        stage.setScene(display.getScene());
        stage.show();
        
        //display.showPopup()
	}

}
