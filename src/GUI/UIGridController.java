//author Talha Koc

package GUI;

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
		System.out.println("setting new simulation with shape " + simData.getShape());
		_currentGeneration = 0;
		if(_gridView != null){
			clearGridFromScreen();
		}
		setSimData(simData);
		switch(simData.getShape()){
		case TRIANGLE:
			_gridView = new TriangleGridView(_bounds, simData.getDimensions(), colors);
			break;
		case HEXAGON:
			_gridView = new HexagonalGridView(_bounds, simData.getDimensions(), colors);
			break;
		default:
			_gridView = new SquareGridView(_bounds, simData.getDimensions(), colors);
			break;
		}
		_root.getChildren().add(_gridView);
		
	}
	public void setNewGridWithDimension(Dimensions dimensions, Color[][] colors){
		getSimData().getData().setDimensions(dimensions);
		setNewSimulation(colors,getSimData());
	}
	public void setNewGridFromFile(SimulationType s, Color[][] colors){
		setSimData( new SimulationData(s.getBoardData(), getSimData().getRatios()));
		setNewSimulation(colors,getSimData());
	}
	public void step(Color[][] newColors, Dimensions newDimensions){
		if(!getSimData().getDimensions().equals(newDimensions)){
			System.out.println(getSimData().getDimensions().getX() + " " + getSimData().getDimensions().getY());
			System.out.println(newDimensions.getX() + " " + newDimensions.getY());
			setNewGridWithDimension(newDimensions, newColors);
			System.out.println("setting new grid of size" + newDimensions.toString());
		}else{
			System.out.println("Generation: " + _currentGeneration);
			System.out.println(newColors.length + "x" + newColors[0].length);
			_gridView.updateGrid(newColors);
			_currentGeneration ++;
		}
	}
	
	public int getCurrentGeneration(){
		return _currentGeneration;
	}
	
	private void clearGridFromScreen(){
		_root.getChildren().remove(_gridView);
		_gridView = null;
	}
	private void setSimData(SimulationData s){
		System.out.println("setting sim data to shape " + getSimData().getShape());
		this._currentSimData = s;
	}
	private SimulationData getSimData(){
		return this._currentSimData;
	}
	
}