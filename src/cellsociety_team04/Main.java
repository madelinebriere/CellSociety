package cellsociety_team04;

import java.util.HashMap;
import java.util.Map;

import GUI.GUIMain;
import cellular_level.Cell;
import data_structures.CellName;
import data_structures.CellRatio;
import data_structures.CellRatioMap;
import data_structures.CellShape;
import data_structures.Dimensions;
import data_structures.SimulationData;
import data_structures.SimulationName;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import societal_level.*;

public class Main extends Application {
	private static final String TITLE = "Team 04 - Cell Society";

	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		CellSociety model = new CellSociety();
		GUIMain display = new GUIMain(model);
		
		//
		
		stage.setTitle(TITLE);
        // add our user interface components to Frame and show it
        stage.setScene(display.getScene());
        stage.show();
        
        //display.showPopup()
	}

}
