//Author Talha Koc

package GUI;

import java.util.HashMap;
import java.util.Random;

import data_structures.BoardData;
import data_structures.BorderType;
import data_structures.CellShape;
import data_structures.Dimensions;
import data_structures.SimulationData;
import data_structures.SimulationName;
import file_handling.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
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
    private final HashMap<String, SimulationName> simulationNameStringToEnum = new HashMap<String, SimulationName>();
    
    private CellSociety _model;
//    private Class<CellSociety> SOCIETY_TYPE;
    private Timeline _animation;
    private Scene _scene; 
    private Pane _root;
    private UIGridController _gridController;
    private Pane _gridContainer;
    private Label _generationLabel;
    private Label _societyTitleLabel;
    private Slider _speedSlider;
    private Slider _sizeSlider;
    private Button _pauseButton;
    private Button _playButton;
    private Button _fileButton;
    private SimulationType _currentSimulationType;
    private SimulationData _currentSimulationData;

    
    public GUIMain(CellSociety model){
    	_model = model;
		_root =  new Pane();
		_scene = new Scene(_root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);
		_currentSimulationData = _model.getSimulationData();
		simulationNameStringToEnum.put("Fire Society", SimulationName.FIRE_SOCIETY);
		simulationNameStringToEnum.put("Wa-Tor Society", SimulationName.WATER_SOCIETY);
		simulationNameStringToEnum.put("Segregation Society", SimulationName.POPULATION_SOCIETY);
		simulationNameStringToEnum.put("Life Society", SimulationName.GAME_OF_LIFE);
		simulationNameStringToEnum.put("Slime Society", SimulationName.SLIME_SOCIETY);
		setupTopLabels();
		setupGrid();
		setupAnimationTimeLine(MILLISECOND_DELAY);
        setupUserControls();
    }
    public Scene getScene () {
        return _scene;
    }
    /**
     * initialze gridController 
     */
    private void setupGrid(){
    	_gridContainer = new Pane(); //controller will add its views to this view
    	_gridContainer.setLayoutX(20);
    	_gridContainer.setLayoutY(60);
    	_gridContainer.setPrefWidth(GRID_WIDTH);
    	_gridContainer.setPrefHeight(GRID_WIDTH);
    	_root.getChildren().add(_gridContainer);
    	_gridController = new UIGridController(
    			_gridContainer,
    			new Frame(0,0,GRID_WIDTH,GRID_WIDTH), 
    			_model.getCurrentColors(), 
    			_currentSimulationData);
    }
    /**
     * sets up KeyFrame and timeline for animatio through a step function
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
    	
    	//TODO:
    	_societyTitleLabel = plainLabel(_model.getName().toString(), 15);
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
    	setupShapeButtons(_gridContainer.getLayoutY() + 16);
    	double sliderWidth = SCREEN_WIDTH - GRID_WIDTH - 60;
    	setupSpeedSlider(sliderWidth, GRID_WIDTH + 30, _gridContainer.getLayoutY() + 160);
    	setupSizeSlider(sliderWidth, GRID_WIDTH + 30, _speedSlider.getLayoutY() + 80);
    }
    
    private void setupTopMenu(){
    	ComboBox<String> menu = new ComboBox<String>();
    	double width = 200;
    	menu.getItems().addAll(simulationNameStringToEnum.keySet());
    	menu.setLayoutY(20);
    	menu.setLayoutX((SCREEN_WIDTH-width)/2);
    	menu.setPrefWidth(width);
    	menu.editableProperty().set(false);
    	menu.setOnAction((event) -> {
    	    String s = menu.getSelectionModel().getSelectedItem();
    	    SimulationName name = this.simulationNameStringToEnum.get(s);
    	    _currentSimulationData = new SimulationData(new BoardData(name),SocietyMaker.getDefaultCellRatioValues(name));
    	    setFileToNull();
    	    resetAnimation();
    	});
    	_root.getChildren().add(menu);
    }
    private void setupButtons(){
    	HBox hbox1 = new HBox();
    	_pauseButton = plainButton("Pause");
    	_playButton = plainButton("Play");
    	Button stepButton = plainButton("Step");
    	Button resetButton = plainButton("Reset");
    	_fileButton = plainButton("New File");
    	setButtonTheme(_fileButton, Color.rgb(220, 180, 30), Color.rgb(240, 200, 70), FontWeight.EXTRA_LIGHT, 11, BorderStrokeStyle.DASHED, 1);

    	
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
    	_fileButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	pauseAnimation();
    	    	PopUp p = new PopUp((Stage) _scene.getWindow());
    	    	_currentSimulationType = p.getSimulation();
    	    	_fileButton.setText(_currentSimulationType.getTitle());
    	    	resetSimulationToType(_currentSimulationType);
    	    }
    	}); 

    	hbox1.getChildren().add(_pauseButton);
        hbox1.getChildren().add(_playButton);
        hbox1.getChildren().add(resetButton);
        hbox1.getChildren().add(stepButton);
        hbox1.getChildren().add(_fileButton);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);
    	hbox1.setLayoutX(24);
    	hbox1.setPrefWidth(GRID_WIDTH - 24);
    	
    	hbox1.setLayoutY(GRID_WIDTH + 40); 
    	hbox1.setPrefHeight(SCREEN_HEIGHT - GRID_WIDTH - 40);
    	_root.getChildren().add(hbox1);
    }
    private void setupShapeButtons(double topY){
    	double bottomY = topY + 50;
    	double width = 60;
    	double x = _gridContainer.getLayoutX() + _gridContainer.getPrefWidth() + 45;
    	Polygon triangle = new Polygon(new double[]{
    			x + width/2, topY,
    			x + width, bottomY,
    			x, bottomY
    	});
    	x += width + 25;
    	Rectangle square = new Rectangle(x, topY, 50, 50);
    	double center = 25;
    	double side = center/(Math.sqrt(3)/2);
    	x += 50 + 25 + side/2;
    	Polygon hexagon = new Polygon(new double[] {
				x, topY,
				x + side, topY,
				x + side + side/2, topY + center,
				x + side, topY + center*2,
				x, topY+center*2,
				x - side/2, topY + center
		});
    	
    	triangle.setFill(Color.rgb(229,57,53)); // red
    	square.setFill(Color.rgb(30,136,229));  // blue
    	hexagon.setFill(Color.rgb(0,137,123)); // green
    	setShapeDeselected(triangle);
    	setShapeDeselected(hexagon);
    	setShapeSelected(square);
    	triangle.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setShapeHighlighted(triangle);;
    	    }
    	});
    	square.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setShapeHighlighted(square);;
    	    }
    	});
    	hexagon.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setShapeHighlighted(hexagon);;
    	    }
    	});
    	triangle.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setNewGridShape(CellShape.TRIANGLE);
    	    	setShapeSelected(triangle);
    	    }
    	});
    	square.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setNewGridShape(CellShape.SQUARE);
    	    	setShapeSelected(square);
    	    }
    	});
    	hexagon.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent me) {
    	    	setNewGridShape(CellShape.HEXAGON);
    	    	setShapeSelected(hexagon);
    	    }
    	});
    	
    	_root.getChildren().add(triangle);
    	_root.getChildren().add(square);
    	_root.getChildren().add(hexagon);
    	
    }
    private void setShapeSelected(Shape s){
    	setShapeDesign(s, Color.rgb(40, 40, 40), 3);
    	s.setScaleX(1.);
    	s.setScaleY(1.);
    }
    private void setShapeDeselected(Shape s){
    	setShapeDesign(s, Color.rgb(80, 80, 80), 2);
    
    }
    private void setShapeHighlighted(Shape s){
    	setShapeDesign(s, Color.rgb(8, 8, 8), 4);
    	s.setScaleX(1.3);
    	s.setScaleY(1.3);
    }
    private void setShapeDesign(Shape s, Color strokeColor, double strokeWidth){
    	s.setStroke(strokeColor);
    	s.setStrokeWidth(strokeWidth);
    	s.setStrokeType(StrokeType.OUTSIDE);
    	s.setStrokeLineCap(StrokeLineCap.ROUND);
    }
    private void setNewGridShape(CellShape s){
    	this._currentSimulationData.getData().setShape(s);
    	resetAnimation();
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
    	setButtonTheme(button, Color.rgb(70, 70, 70), Color.rgb(30, 30, 30), FontWeight.SEMI_BOLD, 14, BorderStrokeStyle.SOLID, 2);
    }
    private void setButtonToUnhighlightedState(Button button){
    	setButtonTheme(button, Color.DARKGRAY, Color.rgb(90, 90, 90), FontWeight.LIGHT, 14, BorderStrokeStyle.DASHED, 1);
    }
    private void setButtonToSelected(Button button){
    	setButtonTheme(button, Color.rgb(0, 122, 255), Color.rgb(56, 56, 56),  FontWeight.MEDIUM, 14, BorderStrokeStyle.SOLID, 2);
    }
    private void setButtonTheme(Button button, Color borderStokeColor, Color textColor, 
    		FontWeight fontWeight, int fontSize, 
    		BorderStrokeStyle borderStrokeStyle, double borderWidth){
    	
    	BorderStroke[] bs = {new BorderStroke(borderStokeColor, borderStrokeStyle, new CornerRadii(4), new BorderWidths(borderWidth))};
    	Border b = new Border(bs);
    	button.setBorder(b);
    	button.setTextFill(textColor);
    	button.setFont(Font.font("HelveticaNeue", fontWeight, fontSize));
    	
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
    	_sizeSlider = new Slider();
    	_sizeSlider.setMin(10);
    	_sizeSlider.setMax(100);
    	_sizeSlider.setValue(this._currentSimulationData.getDimensions().getX());
    	_sizeSlider.setShowTickLabels(true);
    	_sizeSlider.setShowTickMarks(true);
    	_sizeSlider.setMajorTickUnit(10);
    	_sizeSlider.setMinorTickCount(0);
    	_sizeSlider.setPrefWidth(sliderWidth);
    	_sizeSlider.setLayoutY(startY); //bad way to set this
    	_sizeSlider.setLayoutX(startX);
    	_sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		_sizeSlider.setValue(_sizeSlider.getValue() - _sizeSlider.getValue() %  _sizeSlider.getMajorTickUnit());
    		double minValue = Math.max(_currentSimulationData.getDimensions().getX(), _currentSimulationData.getDimensions().getY());
    		if(_sizeSlider.getValue() % _sizeSlider.getMajorTickUnit() == 0 && 
    				_sizeSlider.getValue() != minValue){
    			setFileToNull();
    			System.out.println("Grid Size " + _sizeSlider.getValue() + "\tmin val " + minValue);
//    			this._currentSimulationData.getDimensions().setX((int) _sizeSlider.getValue());
//    			this._currentSimulationData.getDimensions().setY((int) _sizeSlider.getValue());
        		resetAnimation();
    		}
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
    	updateGridDimensionsForShape(_currentSimulationData.getShape());
    	//System.out.println("done updating");
    	_model = SocietyMaker.generateCellSociety(_currentSimulationData);
    	//System.out.println("done making model");
    	_gridController.setNewSimulation(_model.getCurrentColors(), _currentSimulationData);
    	resetGUIComponents();
    }
    private void updateGridDimensionsForShape(CellShape s){
    	int sliderValue = (int) _sizeSlider.getValue();
    	Dimensions d;
    	switch(s){
    	case TRIANGLE:
    		d = new Dimensions(sliderValue, sliderValue/2);
    		break;
    	case HEXAGON:
    		d = new Dimensions(sliderValue/3, sliderValue);
    		break;
    	default:
    		d = new Dimensions(sliderValue, sliderValue);
    		break;
    	}
    	this._currentSimulationData.getData().setDimensions(d);
    }
    private void resetSimulationToType(SimulationType s){
    	//_model=new CellSociety(s); //TODO: Figure out how to deal with abstract CellSociety -- sorry Talha!
//    	switch(s.getBoardData().getName()){
//    		
//    	}
    	_gridController.setNewGridFromFile(s, _model.getCurrentColors());
    	resetGUIComponents();
    }
    private void resetGUIComponents(){
    	updateGenerationLabel();
    }
    private void setFileToNull(){
		_currentSimulationType = null; //TODO: do this more elegantly
		_fileButton.setText("New File");
    }
    private void enableSliders(){
    	//TODO:
    }
    private void disableSliders(){
    	//TODO:
    }

    private void changeAnimationSpeed(Number newValue){
    		pauseAnimation();
        	setupAnimationTimeLine(1000./newValue.doubleValue());
        	playAnimation();
    }
    
    private void updateGenerationLabel(){
    	_generationLabel.textProperty().set( "Generation " + ((Integer) _gridController.getCurrentGeneration()).toString());
    }

    /**
     * updates grid using model (CellSociety object) to retrieve 2d-array of colors and current grid dimensions
     * the the new dimensions will be used to dynamically change the grid size
     */
	private void step(){
		updateGenerationLabel();
		_gridController.step(_model.step(), _model.getSize());
	}

}
