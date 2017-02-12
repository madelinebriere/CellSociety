package cellular_level;

import java.util.List;



import data_structures.CellData;
import javafx.scene.paint.Color;

public class SlimeCell extends Cell {
	private static final Color SLIME_COLOR = Color.RED;
	private static final int DIFFUSE_RATE = 2; //steps
	
	private int diffuseRate;
	private int mySteps;

	public SlimeCell(){
		this(0,0);
	}
	
	public SlimeCell(int row, int col){
		this(0, 0, SLIME_COLOR);
	}
	
	public SlimeCell(int row, int col, Color c){
		this(row, col, c, DIFFUSE_RATE);
	}
	
	public SlimeCell(int row, int col, Color c, int diffuse){
		super(row,col,c);
		diffuseRate = diffuse;
		mySteps = 0;
	}
	
	
	@Override
	public Cell createCopy() {
		SlimeCell copy = new SlimeCell();
		copy.basicCopy(this);
		copy.setDiffuseRate(this.getDiffuseRate());
		return copy;
	}

	@Override
	public List<Cell> update(CellData data) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDiffuseRate() {
		return diffuseRate;
	}

	public void setDiffuseRate(int diffuseRate) {
		this.diffuseRate = diffuseRate;
	}

}
