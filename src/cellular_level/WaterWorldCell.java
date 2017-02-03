package cellular_level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javafx.scene.paint.Color;
import util.CellData;
import util.Location;

/**
 * Add wrapped,
 * delete duplicate code
 * 
 * 
 * @author maddiebriere
 *
 */
public abstract class WaterWorldCell extends Cell {
	private Random randy = new Random();
	
	public WaterWorldCell(){
		super();
	}
	
	public WaterWorldCell(int row, int col, Color color){
		super(row, col, color);
	}
	
	protected Location getBreedSpot(CellData data){
		Cell target = data.getCopyAvailableNeighbor(this);
		if(target!=null)
			return target.getMyLocation();
		else
			return null;
	}
	
	
	protected void move(CellData data){
		Cell target = data.getCopyAvailableNeighbor(this);
		if(target!=null)
			this.setMyLocation(target.getMyLocation());
	}
	
	
	
}
