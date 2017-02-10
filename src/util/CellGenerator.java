package util;

import cellular_level.BurnCell;
import cellular_level.Cell;
import cellular_level.DeadCell;
import cellular_level.EmptyCell;
import cellular_level.FishCell;
import cellular_level.HouseCell;
import cellular_level.LiveCell;
import cellular_level.SharkCell;
import cellular_level.TreeCell;
import data_structures.CellName;
import javafx.scene.paint.Color;

public class CellGenerator {
	private static Color BLUE = Color.BLUE;
	private static Color RED= Color.RED;
	private static Color GREEN = Color.GREEN;
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
			return new HouseCell(BLUE);
		}
		if(c == CellName.HOUSE_CELL_2){
			return new HouseCell(RED);
		}
		if(c == CellName.HOUSE_CELL_3){
			return new HouseCell(GREEN);
		}
		else
			return new EmptyCell();
	}

}
