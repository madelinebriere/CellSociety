/**
 * Class made abstract solely to keep
 * users from creating instances of the
 * superclass
 */
package societal_level;
import java.util.ArrayList;
import java.util.Collections;

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
		updateAllCells(null);
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
	
	
	public void updateAllCells(ArrayList<EmptyCell> available){
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		Collections.shuffle(getCurrentCells());
		for(Cell c: getCurrentCells()){
			ArrayList<Cell> cells = updateCell(c, available);
			removeUsedSpots(available, cells);
			nextGen.addAll(cells);
		}
		fillEmptySpots(nextGen);
		setCurrentCells(nextGen);
	}
	
	public void fillEmptySpots(ArrayList<Cell> nextGen){
		int [][] filled = new int[size][size];
		for(Cell c: nextGen){
			filled[c.getMyRow()][c.getMyCol()]=1;
		}
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(filled[i][j]!=1)
					nextGen.add(new EmptyCell(i,j));
			}
		}
	}
	
	
	public void removeUsedSpots(ArrayList<EmptyCell> available, ArrayList<Cell> newCells){
		if(available ==null || newCells ==null || available.size()==0 || newCells.size()==0){return;}
		for(int i=0; i<available.size(); i++){
			for(int j=0; j<newCells.size(); j++){
				if(available.get(i).getMyLocation().
						equals(newCells.get(j).
						getMyLocation())){
					available.remove(i);
					break;
				}
			}
		}
	}
	
	/**
	 * Duplicate method from CellSociety
	 * 
	 * 
	 * @param currentCells
	 * @return
	 */
	public ArrayList<EmptyCell> getEmptyCells(ArrayList<Cell> currentCells){
		ArrayList <EmptyCell>toRet = new ArrayList<EmptyCell>();
		for(Cell c: currentCells){
			if(c instanceof EmptyCell){
				toRet.add((EmptyCell)c);
			}
		}
		return toRet;
	}

	
	/**
	 * NOTE: DEFAULT AVAILABLE IS NULL UNLESS YOU SPECIFY OTHERWISE
	 * 
	 * Basic update: Can override in subclass
	 * @param c
	 * @return
	 */
	private ArrayList<Cell> updateCell(Cell c, ArrayList<EmptyCell> available){
		int size = getSize();
		ArrayList<Cell> newCells =  c.update(getCurrentCellsCopy(), available, size);
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
