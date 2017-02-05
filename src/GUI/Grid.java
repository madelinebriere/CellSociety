//Author Talha Koc

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
	private final int GRID_SIZE; 
	
	private Rectangle[][] _cells;
    private int _currentGeneration = 0;

	public Grid(int numberOfCells, int screenSize, Color[][] colors){
		super();
		NUMBER_OF_CELLS = numberOfCells;
		GRID_SIZE = screenSize;
		setGridBackground();
		setupGridView(colors);
	}

    public void updateTileColors(Color[][] colors){
    	//TODO: check if correct size
    	System.out.println(NUMBER_OF_CELLS);
    	System.out.println(_cells.length);
    	System.out.println(colors.length);
		for(int i=0; i<NUMBER_OF_CELLS; i++){
    		for(int j=0; j<NUMBER_OF_CELLS; j++){
    			_cells[i][j].setFill(colors[i][j]);
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
    	Rectangle backgroundRec = new Rectangle(-1, -1, GRID_SIZE + 2, GRID_SIZE + 2);
    	backgroundRec.setFill(Color.rgb(40,40,40));
    	backgroundRec.setArcHeight(10);
    	backgroundRec.setArcWidth(10);
		getChildren().add(backgroundRec);
    }
    private void setupGridView(Color[][] colors){
    	_cells = new Rectangle[NUMBER_OF_CELLS][NUMBER_OF_CELLS];
    	int size = (GRID_SIZE/NUMBER_OF_CELLS);
    	double pixelMarginError = GRID_SIZE - size*NUMBER_OF_CELLS;
    	double inset = pixelMarginError/2.0;
    	System.out.println(size + "\t" + GRID_SIZE + "\t" + NUMBER_OF_CELLS + "\t" + pixelMarginError);
    	for(int x=0; x<NUMBER_OF_CELLS;x++){
    		for(int y=0; y<NUMBER_OF_CELLS;y++){
        		Rectangle cell = plainCellWithColor(colors[x][y], inset + x * size + 0.5, inset + y * size + 0.5, size - 1.0);
        		_cells[x][y] = cell;
        		getChildren().add(cell);
        	}
    	}
    }
    private Rectangle plainCellWithColor(Color color, double xPos, double yPos, double length){
    	Rectangle cell = new Rectangle(xPos, yPos, length, length);
    	cell.setFill(color);
//    	cell.setArcHeight(0);
//    	cell.setArcWidth(0);
    	return cell;
    }
    
}
