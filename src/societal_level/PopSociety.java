/**
 * 
 * Bug with current version:
 * Can't get people to stay
 * on screen
 * 
 * Some sort of overriding happening
 * 
 */


package societal_level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import cellular_level.*;
import javafx.scene.paint.Color;



public class PopSociety extends CellSociety {
	private Random rnd = new Random();
	
	public PopSociety(){
		setSize(10);
		setEmptyColor(Color.LIGHTBLUE);
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				if(rnd.nextBoolean()){
					makeCells.add(rnd.nextBoolean()? 
							new HouseCell(i,j, Color.BLUE): new HouseCell(i,j,Color.RED));
				}
				else
					if(rnd.nextBoolean()){
						makeCells.add(new HouseCell(i,j, Color.BLUE));
					}
					else
						makeCells.add(new EmptyCell(i,j));
			}
		} 
		setCurrentCells(makeCells);
	}
	
	@Override
	public Color[][] step() {
		return guidedStep();
		
	}
	
	@Override
	public Collection<Cell> neighbors(Cell c) {
		return getNeighbors(c);
	}
	


}
