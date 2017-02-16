// This entire file is part of my masterpiece.
// Talha Koc

/*
 * This is the refactored version of GridView that I had in Sprint 2.
 * Previously, the GridView was simply a Pane with square Rectangles.
 * Now, it's an abstract class that can be implemented using different 
 * kinds of shapes. 
 * 
 * Some of the improved design features of this class are:
 * 1- 2D-array of Shapes rather than Rectangles. This leaves it open
 * to extension.
 * 2- 2D-array _cells is private. Subclasses do not need direct access
 * except when initializing the grid and adding the Shapes to _cells.
 * This ensures that subclasses do not modify _cells since that can 
 * cause a disconnect between the GridController and GridView.
 * 3- updateGrid method is implemented here rather than in the subclasses.
 * At first, it was abstract. The reason I changed it is that subclasses
 * will have the same implementation of updateGrid since all of them use
 * the same 2D-array data structure. This reduced duplicate code and made
 *  it easier for me to implement hexagonal and triangular grid view.
 * 
 * In the GridController class, I had said that having a 2D-array 
 * violated the open-closed principle. However in this class, I found 
 * it very useful to have a 2D-array as opposed to something general 
 * like Collection. Since all subclasses of GridView need to use a 
 * 2D-array to store shapes, I was able to implement the updateGrid
 * method in here rather than keeping it abstract and letting the 
 * subclasses decided how to implement it. This made it much easier
 * to subclass GridView when I was developing the hexagonal and 
 * triangular grid view.
 */

package GUI;

import data_structures.Dimensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Intended use: Subclass this to create gridViews with different shapes
 * 
 * Meant to hold the shape nodes on display and update their colors on step in guimain
 * 
 * @author talha koc
 *
 */
public abstract class GridView extends Pane{
	
	protected Frame _bounds;
	protected Dimensions _dimensions;
	private Shape[][] _cells;
	
	public GridView(Frame bounds, Dimensions dimensions, Shape[][] shapeArray){
		super();
		_bounds = bounds;
		_dimensions = dimensions;
		_cells = shapeArray;
	}

	protected abstract void setupGrid(Color[][] colors);
	
	public void updateGrid(Color[][] newColors) {
		for(int x=0; x<_dimensions.getX(); x++){
			for(int y=0; y<_dimensions.getY(); y++){
				_cells[x][y].setFill(newColors[x][y]);
			}
		}
	}
	
	protected void setBackgroundColor(Color color){
		Rectangle rec = new Rectangle();
		rec.setX(-1);
		rec.setY(-1);
		rec.setWidth(_bounds.getWidth()+2);
		rec.setHeight(_bounds.getHeight()+2);
		rec.setFill(color);
		rec.setArcHeight(8);
		rec.setArcWidth(8);
		this.getChildren().add(rec);
	}
	
	protected void setShapeAtXY(int x, int y, Shape s){
		this._cells[x][y] = s;
	}
	
}