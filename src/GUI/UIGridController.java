//author Talha Koc

package GUI;

import data_structures.CellShape;
import data_structures.Dimensions;
import file_handling.SimulationType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;




public class UIGridController {
	
	private GridView _gridView;
	private Pane _root;
	private Dimensions _currentGridDimensions;
	private CellShape _currentShape;
	private final Frame _bounds;
	private int _currentGeneration = 0;
	
	public UIGridController(Pane root, Frame frame, Color[][] colors) {
		this(root, CellShape.SQUARE, new Dimensions(10,10), frame, colors);
	}
	public UIGridController(Pane root, Frame frame, Color[][] colors, Dimensions dimensions) {
		this(root, CellShape.SQUARE, dimensions, frame, colors);
	}
	public UIGridController(Pane root, Frame frame, Color[][] colors, CellShape shape) {
		this(root, shape, new Dimensions(10,10),frame, colors);
	}
	public UIGridController(Pane root, CellShape shape, Dimensions dimensions, Frame frame, Color[][] colors) {
		_root = root;
		_currentGridDimensions = dimensions;
		_currentShape = shape;
		_bounds = frame;
		setNewGridWithShape(shape, colors);
	}
	
	public void setNewGridWithShape(CellShape shape, Color[][] colors){
		if(_gridView != null){
			clearGridFromScreen();
		}
		switch(shape){
		case SQUARE:
			_gridView = new SquareGridView(_bounds, _currentGridDimensions, colors);
			break;
		case TRIANGLE:
			_gridView = new TriangleGridView(_bounds, _currentGridDimensions, colors);
			break;
		case HEXAGON:
			_gridView = new HexagonalGridView(_bounds, _currentGridDimensions, colors);
			break;
		default:
			_gridView = new SquareGridView(_bounds, _currentGridDimensions, colors);
			break;
		}
		_root.getChildren().add(_gridView);
		
	}
	public void setNewGridWithDimension(Dimensions dimensions, Color[][] colors){
		_currentGridDimensions = dimensions;
		setNewGridWithShape(_currentShape, colors);
	}
	public void setNewGridFromFile(SimulationType s, Color[][] colors){
		//TODO:
		_currentGridDimensions = new Dimensions(s.getDimension(), s.getDimension());
		setNewGridWithDimension(_currentGridDimensions, colors);
	}
	public void step(Color[][] newColors, Dimensions newDimensions){
		if(!_currentGridDimensions.equals((Dimensions) newDimensions)){
			setNewGridWithDimension(newDimensions, newColors);
			System.out.println("setting new grid of size" + newDimensions.toString());
		}else{
			_gridView.updateGrid(newColors);
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
