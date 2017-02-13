package cellular_level;

import java.util.List;

import data_structures.CellData;

/**
 * Extension of the Cell class specific to the actors 
 * in the SugarScape simulation
 * 
 * @author maddiebriere
 *
 */

public class SugarCell extends Cell{
	public static final int SUGAR_INIT  = 10;
	public static final int SUGAR_META = 3;
	public static final int VISION = 1;
	
	private int sugar; //amount of sugar
	private int sugarMetabolism; ///sugar metabolism
	private int vision; //number of cells it can "see"
	
	//TODO: Constructors
	
	@Override
	public Cell createCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cell> update(CellData data) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSugar() {
		return sugar;
	}

	public void setSugar(int sugar) {
		this.sugar = sugar;
	}

	public int getSugarMetabolism() {
		return sugarMetabolism;
	}

	public void setSugarMetabolism(int sugarMetabolism) {
		this.sugarMetabolism = sugarMetabolism;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	
	
}
