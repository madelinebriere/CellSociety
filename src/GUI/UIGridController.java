// This entire file is part of my masterpiece.
// Talha Koc

/* 
 * GridController takes the brunt off of GUIMain in terms
 * of setting up, displaying, and updating the GridView. Before
 * I refactored this code, GUIMain had the responsibility to
 * initialize the GridView. All the methods that related to 
 * updating and maintaining the grid were spread across in 
 * various places. There was no unifying structure to link these
 * various methods and objects together. After I created this class
 * I think that my design improved in the following ways: 
 * 
 * 1- Having a GridController significantly reduced the 
 * amount of code in GUIMain. All the methods and instance
 * variables related to the GridView were taken out and I
 * felt like I had more room to breathe in GUIMain.
 * 2- The structure of the GUI is more clear. GUIMain simply
 * creates an instance of GridController , gives it a pane to display
 * the GridView, and calls the step method in its own step method
 * to notify GridController to update its GridView.
 * 3- Open-Closed Principle: Developers can extend GridView to modify
 * its visual design or change the shape of the units on the grid.
 * This design was very helpful in allowing me to integrate the 
 * hexagonal and triangular GridViews.
 * 
 * Negatives of this class:
 * 1- Somewhat violates the open-closed principle. This class uses
 * a 2D-array of colors to update its GridView. This limits future
 * developers to only one data structure. This is hard to refactor
 * because the backend uses arrays to communicate with the frontend.
 * If I had more time to refactor my code, I would coordinate with 
 * my teammates to take out the 2D-array structure and replace it
 * with a more general data structure like Collection or List.
 */

package GUI;

import data_structures.CellShape;
import data_structures.Dimensions;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



/**
 * Intended use: Takes care of everything related to grid.
 * 
 * Meant to initialize a gridview, 
 * update grid colors, 
 * and setup new gridviews with different shapes, colors, and dimensions
 * 
 * @author talha koc
 *
 */
public class UIGridController {
	
	private GridView _gridView;
	private Pane _root;
	private SimulationData _currentSimData;
	private final Frame _bounds;
	private int _currentGeneration = 0;

	public UIGridController(Pane root, Frame frame, Color[][] colors, SimulationData data){
		_root = root;
		_bounds = frame;
		_currentSimData = data;
		setNewSimulation(colors, data);
	}

	public void setNewSimulation(Color[][] colors, SimulationData simData){
		resetGeneration();
		clearGridFromScreen();
		setSimData(simData);
		setGridView(simData.getShape(), simData, colors);
	}
	  
	public void setNewGridFromFile(SimulationType s, Color[][] colors){
		resetGeneration();
		setSimData( new SimulationData(s.getBoardData(), getSimData().getRatios()));
		setNewSimulation(colors,getSimData());
	}
	
	public void step(Color[][] newColors, Dimensions newDimensions){
		if(!getSimData().getDimensions().equals(newDimensions)){
			setNewGridWithDimension(newDimensions, newColors);
		}else{
			_gridView.updateGrid(newColors);
			incrementGeneration();
		}
	}
	
	//Made this method private because it's used internally for resizing the grid. 
	private void setNewGridWithDimension(Dimensions dimensions, Color[][] colors){
		getSimData().getData().setDimensions(dimensions);
		setNewSimulation(colors,getSimData());
	}
	
	private void setGridView(CellShape s, SimulationData simData, Color[][] colors){
		switch(s){
		case TRIANGLE:
			_gridView = new TriangleGridView(_bounds, simData.getDimensions(), colors);
			break;
		case HEXAGON:
			_gridView =  new HexagonalGridView(_bounds, simData.getDimensions(), colors);
			break;
		default:
			_gridView =  new SquareGridView(_bounds, simData.getDimensions(), colors);
			break;
		}
		_root.getChildren().add(_gridView);
	}
	
	private void clearGridFromScreen(){
		if(_gridView != null){
			_root.getChildren().remove(_gridView);
			_gridView = null;
		}
	}
	
	public int getCurrentGeneration(){
		return _currentGeneration;
	}
	private void resetGeneration(){
		_currentGeneration = 0;
	}
	private void incrementGeneration(){
		_currentGeneration ++;
	}
	private void setSimData(SimulationData s){
		this._currentSimData = s;
	}
	private SimulationData getSimData(){
		return this._currentSimData;
	}
	
}