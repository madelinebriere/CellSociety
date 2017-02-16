// This entire file is part of my masterpiece.
// Talha Koc

//Implementation of GridView that lays out square Rectangles
//and stores them in the _cells array

package GUI;

import data_structures.Dimensions;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Intended use: subclass of GridView that implements a regular grid
 * 
 * Meant to hold and display a grid to the user
 * 
 * @author talha koc
 *
 */
public class SquareGridView extends GridView {

	public SquareGridView(Frame bounds, Dimensions dimensions, Color[][] colors){
		super(bounds, dimensions, new Rectangle[dimensions.getX()][dimensions.getY()]);
		this.setBackgroundColor(Color.BLACK);
		setupGrid(colors);
	}

	@Override
	protected void setupGrid(Color[][] colors) {
		
		//variables required to determine the position
    	int cellWidth = (_bounds.getWidth()/_dimensions.getX());
    	int cellHeight = (_bounds.getHeight()/_dimensions.getY());
    	double pixelMarginErrorWidth = _bounds.getWidth() - cellWidth*_dimensions.getX();
    	double hInset = pixelMarginErrorWidth/2.0;
    	double pixelMarginErrorHeight = _bounds.getHeight() - cellHeight*_dimensions.getY();
    	double vInset = pixelMarginErrorHeight/2.0;
    	
    	for(int x=0; x<_dimensions.getX();x++){
    		for(int y=0; y<_dimensions.getY();y++){
        		Rectangle cell = plainRectangleWithColor(colors[x][y], hInset + x * cellWidth + 0.5, vInset + y * cellHeight + 0.5, cellWidth -1, cellHeight -1);
				this.setShapeAtXY(x, y, cell);
        		getChildren().add(cell);
        	}
    	}
		
	}
	
	private Rectangle plainRectangleWithColor(Color color, double xPos, double yPos, double width, double height){
    	Rectangle cell = new Rectangle(xPos, yPos, width, height);
    	cell.setFill(color);
    	cell.setArcHeight(height/8.0);
    	cell.setArcWidth(width/8.0);
    	return cell;
    }

}