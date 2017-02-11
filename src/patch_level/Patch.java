package patch_level;

import cellular_level.Cell;
import javafx.scene.paint.Color;
import util.Location;

public abstract class Patch {
	private final static Color DEFAULT_COLOR = Color.WHITE;
	
	private Color myColor;
	private Location myLocation;
	private Cell myCell;
	
	public Patch(){
		this(0,0);
	}
	
	public Patch(int row, int col){
		this(row,col,DEFAULT_COLOR);
	}
	
	public Patch(int row, int col, Color color){
		this(row,col,color, null);
	}
	
	public Patch(int row, int col, Color color, Cell cell){
		myLocation = new Location(row,col);
		myColor=color;
		myCell=cell;
	}
	
	public abstract void update();

	public Color getMyColor() {
		return myColor;
	}

	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}

	public Location getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	public Cell getMyCell() {
		return myCell;
	}

	public void setMyCell(Cell myCell) {
		this.myCell = myCell;
	}
	
	
}
