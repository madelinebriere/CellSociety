package cellsociety_team04;

import GUI.GUIMain;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String TITLE = "Team 04 - Cell Society";

	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		GUIMain display = new GUIMain();
		stage.setTitle(TITLE);
        // add our user interface components to Frame and show it
        stage.setScene(display.getScene());
        stage.show();
        
        //display.showPopup()
	}

}
