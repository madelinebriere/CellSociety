package cellular_level;

import java.util.ArrayList;
import java.util.List;


import data_structures.CellData;
import javafx.scene.paint.Color;
import patch_level.Patch;
import util.CellGenerator;

public class SlimeCell extends Cell {
	private static final Color SLIME_COLOR = Color.RED;
	
	private int mySteps;

	public SlimeCell(){
		this(0,0);
	}
	
	public SlimeCell(int row, int col){
		this(0, 0, SLIME_COLOR);
	}
	
	public SlimeCell(int row, int col, Color c){
		super(row, col, c);
		mySteps = 0;
	}
	
	
	@Override
	public Cell createCopy() {
		SlimeCell copy = new SlimeCell();
		copy.basicCopy(this);
		return copy;
	}

	@Override
	public List<Cell> update(CellData data) {
		List<Cell> toRet = new ArrayList<Cell>();
		return toRet;
	}
	
	public int getMySteps() {
		return mySteps;
	}

	public void setMySteps(int mySteps) {
		this.mySteps = mySteps;
	}

	
	
	
}
