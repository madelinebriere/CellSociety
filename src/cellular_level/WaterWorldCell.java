package cellular_level;
import javafx.scene.paint.Color;
import data_structures.CellData;
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
	
	public WaterWorldCell(){
		super();
	}
	
	public WaterWorldCell(int row, int col, Color color){
		super(row, col, color);
	}
	
	protected Location getBreedSpot(CellData data){
		Location target = data.getAvailableNeighbor(this);
		return target;
	}
	
	
	protected void move(CellData data){
		Location target = data.getAvailableNeighbor(this);
		if(target!=null)
			this.setMyLocation(target);
	}
	
	
	
}