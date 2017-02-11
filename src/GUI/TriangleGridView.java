//author Talha Koc

package GUI;

import data_structures.Dimensions;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleGridView extends GridView{
	
	private Polygon[][] _cells;
	private double unit;
	private double starting_y;
	private double starting_x;
	
	public TriangleGridView(Frame bounds, Dimensions dimensions, Color[][] colors){
		super(bounds, dimensions, colors);
		this.setBackgroundColor(Color.BLACK);
		setupGrid(colors);
	}
	@Override
	public void updateGrid(Color[][] newColors) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void setupGrid(Color[][] colors) {
		_cells = new Polygon[_dimensions.getX()][_dimensions.getY()];
		
		for(int i=0; i<this._dimensions.getX(); i++){
			for(int j =0; j<_dimensions.getY(); j++){
				Polygon p;
				if(i%2==0){
					p = bottomTriangle(starting_x + i*unit, starting_y + j*unit);
				}else{
					p = topTriangle(starting_x + i*unit, starting_y + j*unit);
				}
				_cells[i][j] = p;
        		getChildren().add(p);
			}
		}
	}
	
	private Polygon bottomTriangle(double x, double y){
		double side = unit;
		Polygon t = new Polygon(new double[] {
				x,y,
				x, y+side,
				x+side, y+side
				});
		
		return t;
	}
	private Polygon topTriangle(double x, double y){
		double side = unit;
		Polygon t = new Polygon(new double[] {
				x,y,
				x+side, y,
				x+side, y+side
				});
		return t;
	}



}
