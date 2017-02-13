//author Talha Koc

package GUI;

import data_structures.Dimensions;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Intended use: subclass of GridView that implements a hexagonal grid
 * 
 * Meant to hold display a grid to the user
 * 
 * @author talha koc
 *
 */
public class HexagonalGridView extends GridView {

	private Polygon[][] _cells;
	double unit;
	double starting_y;
	double starting_x;

	public HexagonalGridView(Frame bounds, Dimensions dimensions, Color[][] colors) {
		super(bounds, dimensions, new Polygon[dimensions.getX()][dimensions.getY()]);
		this.setBackgroundColor(Color.DARKSLATEGRAY);

		double numberOfUnits = Math.floor(dimensions.getX()+1)*3 - 1;
		unit = (bounds.getWidth())/numberOfUnits;

		double gridWidth;
		double gridHeight;

		starting_x = unit * 1.25;
		starting_y = unit / 2;
		setupGrid(colors);
	}

	@Override
	protected void setupGrid(Color[][] colors) {
		for (int i = 0; i < _dimensions.getX(); i++) {
			for (int j = 0; j < _dimensions.getY(); j++) {
				Polygon hex;
				if (j % 2 == 0) {
					hex = plainHexagon(starting_x + i * unit * 3, starting_y + j * unit);
				} else {
					hex = plainHexagon(starting_x + i * unit * 3 + 1.5 * unit, starting_y + j * unit);
				}
				hex.setFill(colors[i][j]);
				this.setShapeAtXY(i, j, hex);
				this.getChildren().add(hex);
			}
		}
	}

	private Polygon plainHexagon(double x, double y) {
		double center = getH(unit) + 1.0;
		double side = unit - .5;
		Polygon p = new Polygon(new double[] { x, y, x + side, y, x + side + side / 2, y + center, x + side,
				y + center * 2, x, y + center * 2, x - side / 2, y + center });
		return p;
	}

	private double getH(double side) {
		return ((Math.sqrt(3) / 2) * side);
	}

}
