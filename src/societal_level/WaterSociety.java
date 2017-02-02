package societal_level;

import java.util.ArrayList;
import java.util.Random;

import cellular_level.*;
import javafx.scene.paint.Color;

public class WaterSociety extends CellSociety{
	Random rnd = new Random();
	
	public WaterSociety(){
		setSize(10);
		setEmptyColor(Color.LIGHTBLUE);
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				if(rnd.nextBoolean()){
					makeCells.add(rnd.nextBoolean()? 
							new SharkCell(i,j): new FishCell(i,j));
				}
				else {
					if(rnd.nextBoolean()){
						makeCells.add(new EmptyCell(i,j));
					}
					else{
						makeCells.add(new FishCell(i,j));
					}
				}
			}
		}
		setCurrentCells(makeCells);
	}
	
	public Color[][] step() {
		System.out.println("SIZE: " + getEmptyCells().size());
		updateAllCells(getEmptyCells());
		return getCurrentColors();
	}

}
