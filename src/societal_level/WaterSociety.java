package societal_level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import cellular_level.*;
import javafx.scene.paint.Color;

public class WaterSociety extends CellSociety{
	private ArrayList<String> types = new ArrayList<String>();
	Random rnd = new Random();
	
	public WaterSociety(){
		setSize(40);
		setEmptyColor(Color.LIGHTBLUE);
		types.add("SharkCell");
		types.add("FishCell");
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for (int i=0; i<getSize(); i++){
			makeCells.add(new SharkCell(0,i));
		}
		for(int i=1; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				if(rnd.nextBoolean()){
					makeCells.add(rnd.nextBoolean()? 
							new FishCell(i,j): new FishCell(i,j));
				}
				else {
						makeCells.add(new FishCell(i,j));
				}
			}
		}
		setCurrentCells(makeCells);
	}
	
	public Color[][] step() {
		System.out.println("Cell list: " + getCurrentCells().size());
		int empty =0;
		int fish = 0;
		int shark =0;
		for(Cell c: getCurrentCells()){
			if(c instanceof EmptyCell){empty++;}
			if(c instanceof FishCell){fish++;}
			if(c instanceof SharkCell){shark++;}
		}
		System.out.println("Empty: " + empty);
		System.out.println("Fish: " + fish);
		System.out.println("Shark: " + shark);
		return orderedStep(types);
	}

	@Override
	public Collection<Cell> neighbors(Cell c) {
		Collection<Cell> cells = getWrappedNeighbors(c);
		return cells;
	}


}
