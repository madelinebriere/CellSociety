package societal_level;

import java.util.ArrayList;
import cellular_level.*;
import javafx.scene.paint.Color;

public class FireSociety extends CellSociety{
	
	public FireSociety(){
		setSize(10);
		setEmptyColor(Color.YELLOW);
		
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				if(!(i==5 && j==5)){
					makeCells.add(new TreeCell(i,j));
				}
				else{
					makeCells.add(new BurnCell(i,j));
				}
			}
		}
		setCurrentCells(makeCells);
	}

}
