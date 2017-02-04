package GUI;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid extends Pane{
	
	private final int NUMBER_OF_CELLS;
	private final int SCREEN_SIZE; 
	
	private Tile[][] _tiles;
    private int _currentGeneration = 0;

	public Grid(int numberOfCells, int screenSize, Color[][] colors){
		super();
		NUMBER_OF_CELLS = numberOfCells;
		SCREEN_SIZE = screenSize;
		setGridBackground();
		setupGridView(colors);
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
    
    public int getCurrentGeneration(){
    	return _currentGeneration;
    }
    public void setCurrentGeneration(int gen){
    	_currentGeneration = gen;
    }
    private void setGridBackground(){
    	Rectangle rec = new Rectangle();
    	rec.setFill(Color.LIGHTGRAY);
    	rec.setWidth(SCREEN_SIZE + 6);
    	rec.setHeight(SCREEN_SIZE + 6);
    	rec.setY(-5);
    	rec.setX(-5);
    	rec.setArcHeight(10);
    	rec.setArcWidth(10);
		getChildren().add(rec);
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
