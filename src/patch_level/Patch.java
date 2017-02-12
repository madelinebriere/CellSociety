package patch_level;

import cellular_level.Cell;
import javafx.scene.paint.Color;
import util.Location;

public abstract class Patch {
	private final static Color DEFAULT_COLOR = Color.WHITE;
	private final static double MAX_DARK = 1;
	private final static double DEFAULT_HUE = .5;
	
	private Color myColor;
	private Location myLocation;
	private Cell myCell;
	private double shade; 	//represent the darkness (from 0 - 10) 


	public Patch(){
		this(0,0);
	}
	
	public Patch(int row, int col){
		this(row,col,DEFAULT_COLOR);
	}
	
	public Patch(int row, int col, Color color){
		this(row,col,color, null, DEFAULT_HUE);
	}
	
	public Patch(int row, int col, Color color, Cell cell, double shade){
		myLocation = new Location(row,col);
		myColor=color;
		myCell=cell;
		this.shade = shade;
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
	
	public double getShade() {
		return shade;
	}

	public void setShade(double shade) {
		if(shade>=0 && shade<=MAX_DARK){
			this.shade = shade;
		}
		else if(shade>=0){
			this.shade=MAX_DARK;
		}
		else{
			this.shade = 0;
		}
	}
	
	
}
