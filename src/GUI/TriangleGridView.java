package GUI;

import data_structures.Dimensions;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class TriangleGridView extends GridView{

	
	public TriangleGridView(Frame bounds, Dimensions dimensions){
		super(bounds, dimensions);
		this.setBackgroundColor(Color.RED);
		setupGrid();
	}
	@Override
	public void updateGrid(Color[][] newColors) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void setupGrid() {
		// TODO Auto-generated method stub
		
	}



}
