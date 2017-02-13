package patch_level;

import cellular_level.Cell;
import data_structures.CellName;
import data_structures.PatchName;
import javafx.scene.paint.Color;
import util.Location;

public abstract class Patch {
	private final static Color DEFAULT_COLOR = Color.WHITE;
	private final static int MAX_CONC = 30;
	private final static int DEFAULT_CONC = 1;
	
	private PatchName myPatchType;
	private CellName myCellType;
	private Color myColor;
	private Location myLocation;
	private Cell myCell; 
	private int concentration; 	//A variable for patches with "memory" -- can have specific use
	// implemented within the specific patch


	public Patch(){
		this(0,0);
	}
	
	public Patch(int row, int col){
		this(row,col,DEFAULT_COLOR);
	}
	
	public Patch(int row, int col, Color color){
		this(row,col,color, null, DEFAULT_CONC);
	}
	
	public Patch(int row, int col, Color color, Cell cell, int concentration){
		myLocation = new Location(row,col);
		myColor=color;
		myCell=cell;
		this.concentration = concentration;
	}
	
	public abstract Patch createCopy();
	
	public void basicCopy(Patch copyFrom){
		this.setMyLocation(copyFrom.getMyLocation());
		if(copyFrom.getMyCell()!=null)
			this.setMyCell(copyFrom.getMyCell().createCopy());
		else
			this.setMyCell(null);
		this.setMyColor(copyFrom.getMyColor());
		this.setConcentration(copyFrom.getConcentration());
		this.setMyPatchType(copyFrom.getMyPatchType());
	}
	
	public abstract void update();

	
	public Color getShadedColor(){
		double fraction = ((double)concentration)/(1.5*MAX_CONC); //anywhere from 0 to .25
		double shift = 1-fraction; //Larger concentration --> lower brightness
		if(shift>.9) shift=1;
		return myColor.deriveColor(0, 1, shift, 1);
	}
	

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

	public CellName getMyCellType() {
		return myCellType;
	}

	public void setMyCellType(CellName myCellType) {
		this.myCellType = myCellType;
	}

	public PatchName getMyPatchType() {
		return myPatchType;
	}

	public void setMyPatchType(PatchName myPatchType) {
		this.myPatchType = myPatchType;
	}

	public int getConcentration() {
		return concentration;
	}

	public void setConcentration(int concentration) {
		if(concentration>0 && concentration<=MAX_CONC)
			this.concentration = concentration;
		else if (concentration>0)
			this.concentration = MAX_CONC;
		else
			this.concentration = DEFAULT_CONC;
		
	}
	
	public void incrementConcentration(){
		if(concentration <=MAX_CONC){
			this.concentration++;
		}
	}
	
	public void incrementConcentration(int inc){
		if(concentration + inc <=MAX_CONC){
			this.concentration+=inc;
		}
	}
	
	public void decrementConcentration(){
		if(concentration-1>0){
			this.concentration--;
		}
	}
	

	public void decrementConcentration(int dec){
		if(concentration-dec>0){
			this.concentration-=dec;
		}
	}
	
	
	
}
