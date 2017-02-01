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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import societal_level.CellSociety;
import societal_level.LifeSociety;

public class GUIMain{

	private static final int FRAMES_PER_SECOND = 3;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final int SCREEN_WIDTH = 424;
    private static final int SCREEN_HEIGHT = 600;
    private static final Insets GRID_INSETS = new Insets(60,12,0,12);
    private final int NUMBER_OF_CELLS;
    private final Color[][] INITITAL_GRID_STATE; 
    
    private CellSociety _model;
    private Timeline _animation;
    private Scene _scene;
    private Pane _root;
    private Tile[][] _cellGrid;
    
    public GUIMain(){
		this(new LifeSociety());
    }
    public GUIMain(CellSociety model){
    	_model = model;
		NUMBER_OF_CELLS = _model.getSize();
		INITITAL_GRID_STATE = _model.getCurrentColors();
    	_root =  new Pane();
		_scene = new Scene(_root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);
		setupAnimationTimeLine();
    }
    
    final class Tile extends StackPane{
    	private Rectangle rect;
    	public Tile(int x, int y){
        	int size = (int) ((SCREEN_WIDTH - GRID_INSETS.getRight() -GRID_INSETS.getLeft())/NUMBER_OF_CELLS);
    		rect = new Rectangle(size - 1, size - 1);
    		rect.setStroke(Color.LIGHTGRAY);
    		this.getChildren().add(rect);
    		setTranslateX(x * size + GRID_INSETS.getLeft());
    		setTranslateY(y * size + GRID_INSETS.getTop());
    	}
    	public void setColor(Color color){
    		rect.setFill(color);
    	}
    	
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
    
    /**
     * sets up frame and timeline
     */
    private void setupAnimationTimeLine(){
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
        		e -> step());
    	_animation = new Timeline();
        _animation.setCycleCount(Timeline.INDEFINITE);
        _animation.getKeyFrames().add(frame);
        setupGridView();
        setupUserControls();
    }
    
    /*
     * setup views 
     */

    private void setupGridView(){
    	_cellGrid = new Tile[NUMBER_OF_CELLS][NUMBER_OF_CELLS];
    	Color[][] colors = _model.getCurrentColors();
    	for(int i=0; i<NUMBER_OF_CELLS;i++){
    		for(int j=0; j<NUMBER_OF_CELLS;j++){
    			System.out.println(i + " " + j);
        		Tile cell = new Tile(i,j);
        		cell.setColor(colors[i][j]);
        		_cellGrid[i][j] = cell;
        		_root.getChildren().add(cell);
        	}
    	}
    }
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
    	updateTileColors(INITITAL_GRID_STATE);
    	//TODO: model.reset();
    }
    
    private void updateTileColors(Color[][] colors){
    	//TODO: check if correct size
		for(int i=0; i<NUMBER_OF_CELLS; i++){
    		for(int j=0; j<NUMBER_OF_CELLS; j++){
    			_cellGrid[i][j].setColor( colors[i][j]);
    		}
    	}
    }
	
	private void step(){
		System.out.println("STEP");
		//TODO:
		//colors[][] = model.nextStep();
		updateTileColors(_model.step());
		
//		//placeholder code for random colors
//		Random rand = new Random();
//		for(int i=0; i<NUMBER_OF_CELLS;i++){
//    		for(int j=0; j<NUMBER_OF_CELLS;j++){
//        		_cellGrid[i][j].setColor(rand.nextBoolean() ? Color.BLACK:Color.BLUE);
//        	}
//    	}
	}

}


