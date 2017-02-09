package GUI;

import data_structures.Dimensions;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GridView extends Pane{
	protected Frame _bounds;
	protected Dimensions _dimensions;
	public GridView(Frame bounds, Dimensions dimensions){
		super();
		_bounds = bounds;
		_dimensions = dimensions;
	}
	protected void setBackgroundColor(Color color){
		Rectangle rec = new Rectangle();
		rec.setX(0);
		rec.setY(0);
		rec.setWidth(_bounds.getWidth());
		rec.setHeight(_bounds.getHeight());
		rec.setFill(color);
		this.getChildren().add(rec);
	}
	protected abstract void setupGrid();
	public abstract void updateGrid(Color[][] newColors);
}
