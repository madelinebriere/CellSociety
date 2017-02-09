package GUI;

import data_structures.CellShape;
import data_structures.Dimensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;




public class UIGridController {
	
	private GridView _gridView;
	private Pane _root;
	private Dimensions _currentGridDimensions;
	private CellShape _currentShape;
	private final Frame _bounds;
	private int _currentGeneration = 0;
	
	public UIGridController(Pane root, Frame frame) {
		this(root, CellShape.SQUARE, new Dimensions(10,10), frame);
	}
	public UIGridController(Pane root, Frame frame, Dimensions dimensions) {
		this(root, CellShape.SQUARE, dimensions, frame);
	}
	public UIGridController(Pane root, Frame frame, CellShape shape) {
		this(root, shape, new Dimensions(10,10), frame);
	}
	public UIGridController(Pane root, CellShape shape, Dimensions dimensions, Frame frame) {
		_root = root;
		_currentGridDimensions = dimensions;
		_currentShape = shape;
		_bounds = frame;
		setNewGridWithShape(shape);
	}
	
	public void setNewGridWithShape(CellShape shape){
		if(_gridView != null){
			clearGridFromScreen();
		}
		switch(shape){
		case SQUARE:
			_gridView = new SquareGridView(_bounds, _currentGridDimensions);
			break;
		case TRIANGLE:
			_gridView = new TriangleGridView(_bounds, _currentGridDimensions);
			break;
		case HEXAGON:
			_gridView = new HexagonalGridView(_bounds, _currentGridDimensions);
			break;
		default:
			_gridView = new SquareGridView(_bounds, _currentGridDimensions);
			break;
		}
		_root.getChildren().add(_gridView);
		
	}
	public void setNewGridWithDimension(Dimensions dimensions){
		_currentGridDimensions = dimensions;
		setNewGridWithShape(_currentShape);
	}
	
	public void step(Color[][] newColors, Dimensions newDimensions){
		if(newDimensions != null && _currentGridDimensions.equals(newDimensions)){
			setNewGridWithDimension(newDimensions);
		}
		_gridView.updateGrid(newColors);
	}
	
	public int getCurrentGeneration(){
		return _currentGeneration;
	}
	
	private void clearGridFromScreen(){
		_root.getChildren().remove(_gridView);
		_gridView = null;
	}
	
}
