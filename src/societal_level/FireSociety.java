package societal_level;

import java.util.ArrayList;
import java.util.Collection;

import cellular_level.*;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety representing the 
 * forest fire simulation
 * 
 * @author maddiebriere
 *
 */

public class FireSociety extends CellSociety{
	
	public FireSociety(){
		super(makeCells(10), 10, Color.YELLOW);
	
	}
	
	public FireSociety(Collection<Cell> currentCells, int size, Color emptyColor){
		super(currentCells, size, emptyColor);
	}
	
	private static ArrayList<Cell> makeCells(int size){
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(!(i==5 && j==5)){
					makeCells.add(new TreeCell(i,j));
				}
				else{
					makeCells.add(new BurnCell(i,j));
				}
			}
		}
		return makeCells;
	}
	
	@Override
	public Color[][] step() {
		return totalStep();
	}

	@Override
	public Collection<Cell> neighbors(Cell c) {
		return getNeighbors(c);
	}
	

}
