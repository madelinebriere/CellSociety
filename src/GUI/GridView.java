//author Talha Koc

package GUI;

import data_structures.Dimensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class GridView extends Pane{
	protected Frame _bounds;
	protected Dimensions _dimensions;
	protected Shape[][] _cells;
	public GridView(Frame bounds, Dimensions dimensions, Color[][] colors){
		super();
		_bounds = bounds;
		_dimensions = dimensions;
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
	protected abstract void setupGrid(Color[][] colors);
	public void updateGrid(Color[][] newColors) {
		for(int x=0; x<_dimensions.getX(); x++){
			for(int y=0; y<_dimensions.getY(); y++){
				_cells[x][y].setFill(newColors[x][y]);
			}
		}
	}
}
