package cellular_level;

import data_structures.CellName;
import javafx.scene.paint.Color;

public class CellGenerator {
	private static Color COLOR_1 = Color.BLUE;
	private static Color COLOR_2= Color.RED;
	private static Color COLOR_3 = Color.GREEN;
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
		if(c == CellName.HOUSE_CELL_1){
			return new HouseCell(COLOR_1);
		}
		if(c == CellName.HOUSE_CELL_2){
			return new HouseCell(COLOR_2);
		}
		if(c == CellName.HOUSE_CELL_3){
			return new HouseCell(COLOR_3);
		}
		else
			return new EmptyCell();
	}
}
