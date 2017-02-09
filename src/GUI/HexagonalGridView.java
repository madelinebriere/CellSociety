//author Talha Koc

package GUI;

import data_structures.Dimensions;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class HexagonalGridView extends GridView {

	
	public HexagonalGridView(Frame bounds, Dimensions dimensions, Color[][] colors){
		super(bounds, dimensions, colors);
		this.setBackgroundColor(Color.BLACK);
		setupGrid(colors);
	}
	
	@Override
	public void updateGrid(Color[][] newColors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setupGrid(Color[][] newColors) {
		// TODO Auto-generated method stub
		
	}


}
