//Author Talha Koc

package GUI;

import java.util.Random;

import file_handling.*;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import societal_level.*;

public class GUIMain{
	
    private static final int MILLISECOND_DELAY = 1000/5;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int GRID_WIDTH = SCREEN_WIDTH - 350;
    
    private CellSociety _model;
    private Class<CellSociety> SOCIETY_TYPE;
    private Timeline _animation;
    private Scene _scene; 
    private Pane _root;
    private Grid _grid;
    private Label _generationLabel;
    private Label _societyTitleLabel;
    private Slider _speedSlider;
    private Slider _sizeSlider;
    private Button _pauseButton;
    private Button _playButton;
    private SimulationType _currentSimulationType;
    
    public GUIMain(){
    	//default society
		this(new FireSociety());
    }
    public GUIMain(CellSociety model){
    	_model = model;
		SOCIETY_TYPE = (Class<CellSociety>) model.getClass();
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
    
    private void setupGrid(){
    	_grid = new Grid(_model.getSize(), GRID_WIDTH, _model.getCurrentColors());
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
    	_generationLabel.setTextFill(Color.rgb(60, 60, 60));
    	
    	_societyTitleLabel = plainLabel(SOCIETY_TYPE.getSimpleName(), 15);
    	_societyTitleLabel.setLayoutX(SCREEN_WIDTH/2);
    	_societyTitleLabel.setPrefHeight(80);
    	_societyTitleLabel.setPrefWidth(SCREEN_WIDTH/2 - 20);
    	_societyTitleLabel.setAlignment(Pos.CENTER_RIGHT);
    	_societyTitleLabel.setTextFill(Color.rgb(60, 60, 60));

    	_root.getChildren().add(_generationLabel);
    	_root.getChildren().add(_societyTitleLabel);

    }
    private void setupUserControls(){
    	setupTopMenu();
    	setupButtons();
    	double sliderWidth = SCREEN_WIDTH - GRID_WIDTH - 60;
    	setupSpeedSlider(sliderWidth, GRID_WIDTH + 30, _grid.getLayoutY() + 30);
    	setupSizeSlider(sliderWidth, GRID_WIDTH + 30, _speedSlider.getLayoutY() + 80);
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
    	Button fileButton = plainButton("New File");
    	
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
    	fileButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	pauseAnimation();
    	    	PopUp p = new PopUp((Stage) _scene.getWindow());
    	    	_currentSimulationType = p.getSimulation();
    	    	resetSimulationToType(_currentSimulationType);
    	    }
    	}); 

    	hbox1.getChildren().add(_pauseButton);
        hbox1.getChildren().add(_playButton);
        hbox1.getChildren().add(resetButton);
        hbox1.getChildren().add(stepButton);
        hbox1.getChildren().add(fileButton);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);
    	hbox1.setLayoutX(24);
    	hbox1.setPrefWidth(GRID_WIDTH - 24);
    	
    	hbox1.setLayoutY(GRID_WIDTH + 40); 
    	hbox1.setPrefHeight(SCREEN_HEIGHT - GRID_WIDTH - 40);
    	_root.getChildren().add(hbox1);
    }
    private void resetSimulationToType(SimulationType s){
    	//TODO update model
    	Class<? extends SimulationType> type = s.getClass();
    	if(type.equals(FireSimulation.class)){
    		_model = new FireSociety(s);
    	}else if(type.equals(WaterSimulation.class)){
    		_model = new WaterSociety(s);
    	}else if(type.equals(PopSimulation.class)){
    		_model = new PopSociety(s);
    	}else if(type.equals(LifeSimulation.class)){
    		_model = new LifeSociety(s);
    	}else{
    		//TODO handle incorrect file
        	System.out.println(type);
    		System.out.println("Error casting simulation to a type");
    	}
    	SOCIETY_TYPE = (Class<CellSociety>) _model.getClass();
    	resetGrid();
    	_grid.setCurrentGeneration(0);
    	updateGenerationLabel();
    	_grid.updateTileColors(_model.getCurrentColors());
    }
    private Button plainButton(String text){
    	Button button = new Button(text);
    	button.setPrefSize(80, 40);
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
    	BorderStroke[] bs = {new BorderStroke(Color.rgb(70, 70, 70), BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))};
    	Border b = new Border(bs);
    	button.setBorder(b);
    	button.setTextFill(Color.rgb(30, 30, 30));
    	button.setFont(Font.font("HelveticaNeue", FontWeight.SEMI_BOLD, 13));
    }
    private void setButtonToUnhighlightedState(Button button){
    	BorderStroke[] bs = {new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.DASHED, new CornerRadii(4), new BorderWidths(1))};
    	Border b = new Border(bs);
    	button.setBorder(b);
    	button.setTextFill(Color.rgb(100, 100, 100));
    	button.setFont(Font.font("HelveticaNeue", FontWeight.EXTRA_LIGHT, 13));
    }
    private void setButtonToSelected(Button button){
    	BorderStroke[] bs = {new BorderStroke(Color.rgb(0, 122, 255), BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))};
    	Border b = new Border(bs);
    	button.setBorder(b);
    	button.setTextFill(Color.rgb(70, 70, 70));
    	button.setFont(Font.font("HelveticaNeue", FontWeight.MEDIUM, 13));

    }
    private void setupSpeedSlider(double sliderWidth, double startX, double startY){
    	_speedSlider = new Slider();
    	_speedSlider.setMin(1);
    	_speedSlider.setMax(25);
    	_speedSlider.setValue(6);
    	_speedSlider.setShowTickLabels(true);
    	_speedSlider.setShowTickMarks(true);
    	_speedSlider.setMajorTickUnit(6);
    	_speedSlider.setMinorTickCount(5);
    	_speedSlider.setPrefWidth(sliderWidth);
    	_speedSlider.setLayoutY(startY); //bad way to set this
    	_speedSlider.setLayoutX(startX);
    	_speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		changeAnimationSpeed(newValue);
    	});
    	Label label = plainLabel("Animation Speed", 12);
    	label.setLayoutY(startY - 24);
    	label.setPrefWidth(sliderWidth);
    	label.setLayoutX(startX);
		label.setAlignment(Pos.CENTER);
    	
    	_root.getChildren().add(_speedSlider);
    	_root.getChildren().add(label);
    }
    private void setupSizeSlider(double sliderWidth, double startX, double startY){
    	//TODO:
    	_sizeSlider = new Slider();
    	_sizeSlider.setMin(10);
    	_sizeSlider.setMax(100);
    	_sizeSlider.setValue(_model.getSize());
    	_sizeSlider.setShowTickLabels(true);
    	_sizeSlider.setShowTickMarks(true);
    	_sizeSlider.setMajorTickUnit(10);
    	_sizeSlider.setMinorTickCount(1);
    	_sizeSlider.setPrefWidth(sliderWidth);
    	_sizeSlider.setLayoutY(startY); //bad way to set this
    	_sizeSlider.setLayoutX(startX);
    	_sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		//TODO:create new grid of given size
//    		_sizeSlider.setValue((_sizeSlider.getValue()/10) * 10);
    		resetAnimation();
    	});
    	Label label = plainLabel("Grid Size", 12);
    	label.setLayoutY(startY - 24);
    	label.setPrefWidth(sliderWidth);
    	label.setLayoutX(startX);
		label.setAlignment(Pos.CENTER);
    	
    	_root.getChildren().add(_sizeSlider);
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
    	if(_currentSimulationType != null){
    		resetSimulationToType(_currentSimulationType);
    		return;
    	}
    	_grid.setCurrentGeneration(0);
    	updateGenerationLabel();
    	try {
			_model = SOCIETY_TYPE.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    	_model.setNewSizeAndCells((int) _sizeSlider.getValue());
    	resetGrid(); 
//    	_grid.updateTileColors(_model.getCurrentColors());
    }
    private void resetGrid(){
    	_root.getChildren().remove(_grid);
    	setupGrid();
    }
    private void changeAnimationSpeed(Number newValue){
    		pauseAnimation();
        	setupAnimationTimeLine(1000./newValue.doubleValue());
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
