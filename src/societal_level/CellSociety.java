/**
 * Class made abstract solely to keep
 * users from creating instances of the
 * superclass
 */
package societal_level;
import java.util.ArrayList;
import java.util.HashSet;

import cellular_level.*;
import javafx.scene.paint.Color;

public abstract class CellSociety {
	private ArrayList<Cell> currentCells;
	private int size;
	private Color emptyColor;
	

	public Color[][] getCurrentColors(){
		//Catch to avoid null pointer 
		if(getEmptyColor()==null){
			setEmptyColor(Color.WHITE);
		}
		Color [][] toRet = new Color[size][size];
		for(Cell c: currentCells){
			if(!(c instanceof EmptyCell)){
				toRet[c.getMyRow()][c.getMyCol()]=c.getMyState();
			}
			else{
				toRet[c.getMyRow()][c.getMyCol()]=getEmptyColor();
			}
		}
		return toRet;
	}
	
	public void printCurrentColors(){
		Color[][] curr = getCurrentColors();
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				System.out.print(curr[i][j].toString()+ "  ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Basic step, can override in subclass
	 * @return
	 */
	public Color[][] step() {
		updateAllCells();
		return getCurrentColors();
	}
	
	/**
	 * DUPLICATE METHOD FROM CELL
	 * 
	 * 
	 * FIND FIX!!
	 * @param currentCells
	 * @return
	 */
	public ArrayList<EmptyCell> getEmptyCells(){
		ArrayList<Cell> currentCells = getCurrentCells();
		ArrayList <EmptyCell>toRet = new ArrayList<EmptyCell>();
		for(Cell c: currentCells){
			if(c instanceof EmptyCell){
				toRet.add((EmptyCell)c);
			}
		}
		return toRet;
	}
	
	
	public void updateAllCells(){
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			nextGen.addAll(updateCell(c));
			System.out.println("HERE " + c.getMyState());
		}
		setCurrentCells(nextGen);
	}
	
	/**
	 * Basic update: Can override in subclass
	 * @param c
	 * @return
	 */
	private ArrayList<Cell> updateCell(Cell c){
		int size = getSize();
		ArrayList<Cell> newCells =  c.update(getCurrentCellsCopy(), size);
		return newCells;
	}
	
	
	public ArrayList<Cell> getCurrentCellsCopy(){
		ArrayList<Cell> copy = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			Cell newCell = c.createCopy();
			copy.add(newCell);
		}
		return copy;
	}
	
	public ArrayList<Cell> getCurrentCells(){
		return currentCells;
	}
	
	public void setCurrentCells(ArrayList<Cell> current){
		currentCells=current;
	}
	
	public Color getEmptyColor() {
		return emptyColor;
	}

	public void setEmptyColor(Color emptyColor) {
		this.emptyColor = emptyColor;
	}
	
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
