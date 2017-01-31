package GUI;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GUIMain{

	private static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final int SCREEN_WIDTH = 424;
    private static final int SCREEN_HEIGHT = 600;
    private static final int NUMBER_OF_CELLS = 20;
    private static final Insets GRID_INSETS = new Insets(60,12,0,12);
    private static final int TILE_SIZE = (int) ((SCREEN_WIDTH - GRID_INSETS.getRight() -GRID_INSETS.getLeft())/NUMBER_OF_CELLS);
    
    private Timeline _animation;
    private Scene _scene;
    private Pane _root;
    private Tile[][] _cellGrid = new Tile[NUMBER_OF_CELLS][NUMBER_OF_CELLS];
    
    public GUIMain(){
    	_root =  new Pane();
		_scene = new Scene(_root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);
		setupAnimationTimeLine();
        
    }
    
    final class Tile extends StackPane{
    	private Rectangle rect;
    	public Tile(int x, int y){
    		rect = new Rectangle(TILE_SIZE - 1, TILE_SIZE - 1);
    		rect.setStroke(Color.LIGHTGRAY);
    		this.getChildren().add(rect);
    		setTranslateX(x * TILE_SIZE + GRID_INSETS.getLeft());
    		setTranslateY(y * TILE_SIZE + GRID_INSETS.getTop());
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
    
    /**
     * sets up frame and timeline
     */
    private void setupAnimationTimeLine(){
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
        		e -> step());
    	_animation = new Timeline();
        _animation.setCycleCount(Timeline.INDEFINITE);
        _animation.getKeyFrames().add(frame);
        _animation.play();
        setupGridView();
        setupUserControls();
    }
    
    /*
     * setup views 
     */

    private void setupGridView(){
    	//TODO:
    	for(int i=0; i<NUMBER_OF_CELLS;i++){
    		for(int j=0; j<NUMBER_OF_CELLS;j++){
        		Tile cell = new Tile(i,j);
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
    	//TODO:
    }
    private void setupSpeedSlider(){
    	//TODO:
    }
	

	
	private void step(){
		System.out.println("STEP");
		//TODO:
		//colors[][] = model.nextStep();
		Random rand = new Random();
		for(int i=0; i<NUMBER_OF_CELLS;i++){
    		for(int j=0; j<NUMBER_OF_CELLS;j++){
        		_cellGrid[i][j].setColor(rand.nextBoolean() ? Color.BLACK:Color.BLUE);
        	}
    	}
	}

}


