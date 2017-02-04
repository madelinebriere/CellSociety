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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private Label _generationLabel;
    private Label _societyTitleLabel;
    private Slider _slider;
    private Button _pauseButton;
    private Button _playButton;
    
    public GUIMain(){
		this(new WaterSociety());
    }
    public GUIMain(CellSociety model){
    	_model = model;
    	_root =  new Pane();
    	
		_scene = new Scene(_root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);
		setupTopLabels();
		setupGrid();
		setupAnimationTimeLine(MILLISECOND_DELAY);
        setupUserControls();
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
    	_grid.setLayoutY(60);
    	_root.getChildren().add(_grid);
    }
    /**
     * sets up frame and timeline
     */
    private void setupAnimationTimeLine(double delay){
    	KeyFrame frame = new KeyFrame(Duration.millis(delay),
        		e -> step());
    	_animation = new Timeline();
        _animation.setCycleCount(Timeline.INDEFINITE);
        _animation.getKeyFrames().add(frame);
    }
    
    /*
     * setup views 
     */
    private void setupTopLabels(){
    	_generationLabel = plainLabel("Generation 0", 15);
    	_generationLabel.setLayoutX(16);
    	_generationLabel.setPrefHeight(80);
    	_generationLabel.setAlignment(Pos.CENTER_LEFT);
    	
    	_societyTitleLabel = plainLabel("Cell Society", 15);
    	_societyTitleLabel.setLayoutX(SCREEN_WIDTH/2);
    	_societyTitleLabel.setPrefHeight(80);
    	_societyTitleLabel.setPrefWidth(SCREEN_WIDTH/2 - 20);
    	_societyTitleLabel.setAlignment(Pos.CENTER_RIGHT);
    	
    	_root.getChildren().add(_generationLabel);
    	_root.getChildren().add(_societyTitleLabel);

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
    	_pauseButton = plainButton("Pause");
    	_playButton = plainButton("Play");
    	Button stepButton = plainButton("Step");
    	Button resetButton = plainButton("Reset");
    	
    	//simulation starts in paused state
    	setButtonToSelected(_pauseButton);
    	
    	//add functions to buttons
    	_pauseButton.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setButtonToHighlightedState(_pauseButton);
    	    }
    	});
    	_pauseButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	        pauseAnimation();
    	    }
    	});
    	_playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setButtonToHighlightedState(_playButton);
    	    }
    	});
    	_playButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	        playAnimation();
    	    }
    	});
    	resetButton.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setButtonToHighlightedState(resetButton);
    	    }
    	});
    	resetButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	        resetAnimation();
    	    	setButtonToUnhighlightedState(resetButton);
    	    }
    	});
    	stepButton.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setButtonToHighlightedState(stepButton);
    	        pauseAnimation();
    	    }
    	});
    	stepButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setButtonToUnhighlightedState(stepButton);
    	        step();
    	    }
    	});

    	hbox1.getChildren().add(_pauseButton);
        hbox1.getChildren().add(_playButton);
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
    	button.setTextFill(Color.rgb(60, 60, 60));
    	button.setBackground(Background.EMPTY);
    	setButtonToUnhighlightedState(button);
    	return button;
    }
    private Label plainLabel(String text, int fontSize){
    	Label label = new Label();
    	label.setText(text);
    	label.setFont(Font.font("HelveticaNeue", FontWeight.BOLD, fontSize));
    	label.setAlignment(Pos.CENTER);
    	return label;
    }
    private void setButtonToHighlightedState(Button button){
    	BorderStroke[] bs = {new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(3))};
    	Border b = new Border(bs);
    	button.setBorder(b);
    	button.setFont(Font.font("HelveticaNeue", FontWeight.BOLD, 14));
    }
    private void setButtonToUnhighlightedState(Button button){
    	BorderStroke[] bs = {new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(1))};
    	Border b = new Border(bs);
    	button.setBorder(b);
    	button.setFont(Font.font("HelveticaNeue", FontWeight.EXTRA_LIGHT, 14));
    }
    private void setButtonToSelected(Button button){
    	BorderStroke[] bs = {new BorderStroke(Color.DODGERBLUE, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))};
    	Border b = new Border(bs);
    	button.setBorder(b);
    	button.setFont(Font.font("HelveticaNeue", FontWeight.MEDIUM, 14));

    }
    private void setupSpeedSlider(){
    	
    	_slider = new Slider();
    	_slider.setMin(100);
    	_slider.setMax(1000);
    	_slider.setValue(400);
    	_slider.setShowTickLabels(true);
    	_slider.setShowTickMarks(false);
    	_slider.setMajorTickUnit(300);
    	_slider.setMinorTickCount(300);
    	_slider.setBlockIncrement(300);
    	_slider.setPrefWidth(SCREEN_WIDTH*0.6);
    	_slider.setLayoutY(SCREEN_HEIGHT - 120); //bad way to set this
    	_slider.setLayoutX(SCREEN_WIDTH *1/6);
    	_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		changeAnimationSpeed(newValue);
    	});
//    	slider.onDragDoneProperty().addListener((something)->{
//    	    System.out.println("Slider Value Changed (newValue: " + something.toString() + ")");
//    	});
//    	slider.onMouseDragExitedProperty().addListener((observable, oldValue, newValue) -> {
//    	    System.out.println("Slider Value Changed (newValue: " + newValue.toString() + ")");
//    	});
    	Label label = plainLabel("Animation Speed", 12);
    	label.setLayoutY(_slider.getLayoutY() - 24);
    	label.setPrefWidth(SCREEN_WIDTH);
		label.setAlignment(Pos.CENTER);
    	
    	_root.getChildren().add(_slider);
    	_root.getChildren().add(label);
    }
	
    private void pauseAnimation(){
    	setButtonToSelected(_pauseButton);
    	setButtonToUnhighlightedState(_playButton);
    	_animation.pause();
    }
    private void playAnimation(){
    	setButtonToSelected(_playButton);
    	setButtonToUnhighlightedState(_pauseButton);
    	_animation.play();
    }
    private void resetAnimation(){
    	pauseAnimation();
    	_model = new FireSociety(); //TODO: change this
    	_grid.updateTileColors(_model.getCurrentColors());
    }
    private void changeAnimationSpeed(Number newValue){
    		pauseAnimation();
        	setupAnimationTimeLine(newValue.doubleValue());
        	playAnimation();
    }
    
    private void updateGenerationLabel(){
    	_generationLabel.textProperty().set( "Generation " + ((Integer) _grid.getCurrentGeneration()).toString());
    }

    /**
     * updates grid using model (CellSociety object) to retrieve 2d-array of colors
     */
	private void step(){
		_grid.updateTileColors(_model.step());
		updateGenerationLabel();
	}

}

