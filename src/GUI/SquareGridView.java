package GUI;

import data_structures.Dimensions;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareGridView extends GridView {
	
	public SquareGridView(Frame bounds, Dimensions dimensions){
		super(bounds, dimensions);
		this.setBackgroundColor(Color.BLUE);
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
	
	private Rectangle plainCellWithColor(Color color, double xPos, double yPos, double length){
    	Rectangle cell = new Rectangle(xPos, yPos, length, length);
    	cell.setFill(color);
    	cell.setArcHeight(4);
    	cell.setArcWidth(4);
    	return cell;
    }


}
