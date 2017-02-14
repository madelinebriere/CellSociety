package cellular_level;

import java.util.ArrayList;
import java.util.List;

import data_structures.CellData;
import javafx.scene.paint.Color;
import patch_level.Patch;
import util.Location;

/**
 * Extension of the Cell class specific to the actors in the SugarScape
 * simulation
 * 
 * @author maddiebriere
 *
 */

public class SugarCell extends Cell {
	public static final Color SUGAR_COLOR=Color.PALEVIOLETRED;
	
	public static final int SUGAR_INIT = 10;
	public static final int SUGAR_META = 3;
	public static final int VISION = 1;

	private int sugar; // amount of sugar
	private int sugarMetabolism; /// sugar metabolism
	private int vision; // number of cells it can "see"

	public SugarCell(){
		this(0,0);
	}
	
	public SugarCell(int row, int col){
		this(row, col, SUGAR_COLOR);
	}
	
	public SugarCell(int row, int col, Color color){
		this(row,col,color, SUGAR_INIT, SUGAR_META, VISION);
	}
	
	public SugarCell(int row, int col, Color color, int init, int meta, int vision){
		super(row,col,color);
		setSugar(init);
		setSugarMetabolism(meta);
		setVision(vision);
	}
	
	@Override
	public Cell createCopy() {
		SugarCell copy = new SugarCell();
		copy.basicCopy(this);
		copy.setSugar(this.getSugar());
		copy.setSugarMetabolism(this.getSugarMetabolism());
		copy.setVision(this.getVision());
		return copy;
	}

	@Override
	public List<Cell> update(CellData data) {
		Patch current = data.getCurrentPatch(this);
		decrementSugar(sugarMetabolism);
		incrementSugar(current.getConcentration());
		current.setConcentration(0);
		move(data);
		ArrayList<Cell> ret = new ArrayList<Cell>();
		if(getSugar()>0)
			ret.add(this);
		return ret;
	}
	
	private void move(CellData data) {
		ArrayList<Patch> sight = data.getRadialPatches(this,vision);
		if (sight.size() == 0) {
			return;
		}
		Location target = data.getTargetLocation(sight, this, 0);
		if(target == null){
			return;
		}
		Location newSpot = data.spotNearTarget(target, this);
		if (newSpot != null) {
			this.setMyLocation(newSpot);
		}

	}

	private void decrementSugar(int dec){
		if(sugar-dec>=0){
			sugar-=dec;
		}
		else{
			sugar=0;
		}
	}
	
	private void incrementSugar(int inc){
		sugar+=inc;
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
