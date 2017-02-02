package GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Grid extends Pane{
	
//	private final Color[][] INITIAL_CELL_STATES;
	private final int NUMBER_OF_CELLS;
	private final int SCREEN_SIZE; 
	
	private Tile[][] _tiles;
    private int _currentGeneration = 0;

	public Grid(int numberOfCells, int screenSize, Color[][] colors){
		super();
		System.out.println("super();");
		NUMBER_OF_CELLS = numberOfCells;
		SCREEN_SIZE = screenSize;
		setupGridView(colors);
		System.out.println("setupGridView");
	}
	
    public void updateTileColors(Color[][] colors){
    	//TODO: check if correct size
		for(int i=0; i<NUMBER_OF_CELLS; i++){
    		for(int j=0; j<NUMBER_OF_CELLS; j++){
    			_tiles[i][j].setColor( colors[i][j]);
    		}
    	}
		_currentGeneration ++;
    }
    
//    public void resetGrid(){
//    	updateTileColors(INITIAL_CELL_STATES);
//    	_currentGeneration = 0;
//    }
    public int getCurrentGeneration(){
    	return _currentGeneration;
    }
    public void setCurrentGeneration(int gen){
    	_currentGeneration = gen;
    }
    private void setupGridView(Color[][] colors){
    	_tiles = new Tile[NUMBER_OF_CELLS][NUMBER_OF_CELLS];
    	int size = (SCREEN_SIZE/NUMBER_OF_CELLS);
    	for(int i=0; i<NUMBER_OF_CELLS;i++){
    		for(int j=0; j<NUMBER_OF_CELLS;j++){
    			//System.out.println(i + " " + j);
        		Tile cell = new Tile(i,j, size);
        		cell.setColor(colors[i][j]);
        		_tiles[i][j] = cell;
        		getChildren().add(cell);
        	}
    	}
    }
    
}
