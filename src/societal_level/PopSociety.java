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
import file_handling.*;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety representing the 
 * population society simulation
 * 
 * @author maddiebriere
 *
 */

public class PopSociety extends CellSociety {
	private static Random rnd = new Random();
	private static final int DEFAULT_SIZE = 20;
	private static final Color EMPTY_COLOR = Color.LIGHTBLUE;
	
	public PopSociety(){
		this(DEFAULT_SIZE);;
	}
	
	public PopSociety(int size){
		super(makeCells(size), size, EMPTY_COLOR);
	}
	
	public PopSociety(Collection<Cell> currentCells, int size, Color emptyColor){
		super(currentCells, size, emptyColor);
	}
	
	public PopSociety(SimulationType pop){
		super(pop);
		if(pop instanceof PopSimulation){
			HouseCell.setSatisfiedThreshold(((PopSimulation)pop).getThreshold());
		}
	}
	
	private static ArrayList<Cell> makeCells(int size){
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(rnd.nextBoolean()){
					makeCells.add(rnd.nextBoolean()? 
							new HouseCell(i,j, Color.BLUE): new HouseCell(i,j,Color.RED));
				}
				else
					if(rnd.nextBoolean()){
						makeCells.add(new HouseCell(i,j, Color.GREEN));
					}
					else
						makeCells.add(new EmptyCell(i,j));
			}
		} 
		return makeCells;
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
