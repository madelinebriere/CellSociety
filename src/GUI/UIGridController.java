// This entire file is part of my masterpiece.
// Talha Koc

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
		_currentGeneration = 0;
		if(_gridView != null){
			clearGridFromScreen();
		}
		setSimData(simData);
		setGridView(simData.getShape(), simData, colors);
		
	}
	  
	public void setNewGridFromFile(SimulationType s, Color[][] colors){
		setSimData( new SimulationData(s.getBoardData(), getSimData().getRatios()));
		setNewSimulation(colors,getSimData());
	}
	
	public void step(Color[][] newColors, Dimensions newDimensions){
		if(!getSimData().getDimensions().equals(newDimensions)){
			setNewGridWithDimension(newDimensions, newColors);
		}else{
			_gridView.updateGrid(newColors);
			_currentGeneration ++;
		}
	}
	
	public int getCurrentGeneration(){
		return _currentGeneration;
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
		_root.getChildren().remove(_gridView);
		_gridView = null;
	}
	
	private void setSimData(SimulationData s){
		this._currentSimData = s;
	}
	
	private SimulationData getSimData(){
		return this._currentSimData;
	}
	
}