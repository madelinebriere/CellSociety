package GUI;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import societal_level.CellSociety;
import societal_level.*;

public class GUIMain{

	private static final int FRAMES_PER_SECOND = 3;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final int SCREEN_WIDTH = 424;
    private static final int SCREEN_HEIGHT = 600;

    
    private CellSociety _model;
    private Timeline _animation;
    private Scene _scene;
    private Pane _root;
    private Grid _grid;
    
    public GUIMain(){
		this(new FireSociety());
    }
    public GUIMain(CellSociety model){
    	_model = model;
    	_root =  new Pane();
		_scene = new Scene(_root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);
		setupGrid();
		setupAnimationTimeLine();
    }
    
    /*
     * getters and setters
     */
    public Scene getScene () {
        return _scene;
    }
    public void setCellSociety(CellSociety model){
    	_model = model;
    }
    
    private void setupGrid(){
    	_grid = new Grid(_model.getSize(), SCREEN_WIDTH - 40, _model.getCurrentColors());
    	_grid.setLayoutX(20);
    	_grid.setLayoutY(80);
    	_root.getChildren().add(_grid);
    }
    /**
     * sets up frame and timeline
     */
    private void setupAnimationTimeLine(){
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
        		e -> step());
    	_animation = new Timeline();
        _animation.setCycleCount(Timeline.INDEFINITE);
        _animation.getKeyFrames().add(frame);
        setupUserControls();
    }
    
    /*
     * setup views 
     */
   
    private void setupUserControls(){
    	setupTopMenu();
    	setupButtons();
    	setupSpeedSlider();
    }
    
    private void setupTopMenu(){
    	//TODO:
    }
    private void setupButtons(){
    	HBox hbox1 = new HBox();
    	Button pauseButton = plainButton("Pause");
    	Button stepButton = plainButton("Step");
    	Button playButton = plainButton("Play");
    	Button resetButton = plainButton("Reset");
    	
    	pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	        pauseAnimation();
    	    }
    	});
    	playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	        playAnimation();
    	    }
    	});
    	resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	        resetAnimation();
    	    }
    	});
    	stepButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	        pauseAnimation();
    	        step();
    	    }
    	});

    	hbox1.getChildren().add(pauseButton);
        hbox1.getChildren().add(playButton);
        hbox1.getChildren().add(resetButton);
        hbox1.getChildren().add(stepButton);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);
    	hbox1.setLayoutX(24);
    	hbox1.setPrefWidth(SCREEN_WIDTH - 48);
    	hbox1.setLayoutY(SCREEN_HEIGHT - 100); 
    	hbox1.setPrefHeight(100);
    	_root.getChildren().add(hbox1);
    }
    private Button plainButton(String text){
    	Button button = new Button(text);
    	button.setPrefSize(80, 40);
    	button.setTextFill(Color.BLACK);
    	return button;
    }
    private void setupSpeedSlider(){
    	//TODO:
    }
	
    private void pauseAnimation(){
    	_animation.pause();
    }
    private void playAnimation(){
    	_animation.play();
    }
    private void resetAnimation(){
    	pauseAnimation();
    	_grid.resetGrid();
    	//TODO: model.reset();
    }

    /**
     * updates grid using model (CellSociety object) to retrieve 2d-array of colors
     */
	private void step(){
		_grid.updateTileColors(_model.step());
	}

}


