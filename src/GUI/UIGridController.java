//author Talha Koc

package GUI;

import data_structures.CellShape;
import data_structures.Dimensions;
import data_structures.SimulationData;
import file_handling.SimulationType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;




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
		_currentSimData.getData().setDimensions(dimensions);
		setNewSimulation(colors,_currentSimData);
	}
	public void setNewGridFromFile(SimulationType s, Color[][] colors){
		_currentSimData = new SimulationData(s.getBoardData(), _currentSimData.getRatios());
		setNewSimulation(colors,_currentSimData);
	}
	public void step(Color[][] newColors, Dimensions newDimensions){
		if(!_currentSimData.getDimensions().equals(newDimensions)){
			System.out.println(_currentSimData.getDimensions().getX() + " " + _currentSimData.getDimensions().getY());
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
	
}
