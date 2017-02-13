package cellular_level;

import javafx.scene.paint.Color;
import data_structures.CellData;
import util.Location;

/**
 * Super class to the Fish and Shark Cells, performs common functions such as
 * finding breed spots and moving the cell to an open spot
 * 
 * @author maddiebriere
 *
 */
public abstract class WaterWorldCell extends Cell {

	public WaterWorldCell() {
		super();
	}

	public WaterWorldCell(int row, int col, Color color) {
		super(row, col, color);
	}

	protected Location getBreedSpot(CellData data) {
		Location target = data.getAvailableNeighbor(this);
		return target;
	}

	protected void move(CellData data) {
		Location target = data.getAvailableNeighbor(this);
		if (target != null)
			this.setMyLocation(target);
	}

}