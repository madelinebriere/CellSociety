package cellular_level;

import data_structures.CellName;
import javafx.scene.paint.Color;

public class CellGenerator {

	//Add this to SimulationType as well -- replace duplicate code with this
	
	public static Cell newCell(CellName c){
		if(c == CellName.FISH_CELL){
			return new FishCell();
		}
		if(c == CellName.SHARK_CELL){
			return new SharkCell();
		}
		if(c == CellName.BURN_CELL){
			return new BurnCell();
		}
		if(c == CellName.TREE_CELL){
			return new TreeCell();
		}
		if(c == CellName.LIVE_CELL){
			return new LiveCell();
		}
		if(c == CellName.DEAD_CELL){
			return new DeadCell();
		}
		if(c == CellName.HOUSE_CELL_BLUE){
			return new HouseCell(Color.BLUE);
		}
		if(c == CellName.HOUSE_CELL_RED){
			return new HouseCell(Color.RED);
		}
		if(c == CellName.HOUSE_CELL_GREEN){
			return new HouseCell(Color.GREEN);
		}
		if(c == CellName.HOUSE_CELL_YELLOW){
			return new HouseCell(Color.YELLOW);
		}
		else
			return new EmptyCell();
	}
}
