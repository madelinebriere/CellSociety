/**
 * Class made abstract solely to keep
 * users from creating instances of the
 * superclass
 */
package societal_level;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import cellular_level.*;
import javafx.scene.paint.Color;

public abstract class CellSociety {
	private Collection<Cell> currentCells;
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
	
	/**
	 * Basic step, can override in subclass
	 * @return
	 */
	public abstract Color[][] step();
	
	
	public Color [][] totalStep(){
		updateAllCells(null);
		return getCurrentColors();
	}
	
	public Color[][] guidedStep(){
		updateAllCells(new ArrayList<EmptyCell> (getEmptyCells()));
		return getCurrentColors();
	}
	
	public Color [][] orderedStep(Collection<String>types){
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		ArrayList<EmptyCell> emptyCells = new ArrayList<EmptyCell>(getEmptyCells());
		for(String s: types){
			nextGen.addAll(updateSomeCells(getCategoryCells(s), emptyCells));
		}
		System.out.println("SIZE: "+ nextGen.size());
		fillEmptySpots(nextGen);
		setCurrentCells(nextGen);
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
	public Collection<EmptyCell> getEmptyCells(){
		Collection<Cell> currentCells = getCurrentCells();
		ArrayList <EmptyCell>toRet = new ArrayList<EmptyCell>();
		for(Cell c: currentCells){
			if(c instanceof EmptyCell){
				toRet.add((EmptyCell)c);
			}
		}
		return toRet;
	}
	
	
	private void updateAllCells(ArrayList<EmptyCell> available){
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		//Collections.shuffle(getCurrentCells());
		for(Cell c: getCurrentCells()){
			ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(c, available));
			removeUsedSpots(available, cells);
			nextGen.addAll(cells);
		}
		fillEmptySpots(nextGen);
		setCurrentCells(nextGen);
	}
	
	private Collection<Cell> updateSomeCells(ArrayList<Cell> targetCells, ArrayList<EmptyCell>available){
		if(targetCells.size()==0){return new ArrayList<Cell>();}
		ArrayList<Cell> nextGen = new ArrayList<Cell>();
		//Collections.shuffle(getCurrentCells());
		for(Cell c: targetCells){
			ArrayList<Cell> cells = new ArrayList<Cell>(updateCell(c, available));
			removeUsedSpots(available, cells);
			nextGen.addAll(cells);
		}
		return nextGen;
	}
	
	public void fillEmptySpots(Collection<Cell> nextGen){
		int [][] filled = new int[size][size];
		for(Cell c: nextGen){
			filled[c.getMyRow()][c.getMyCol()]+=1;
		}
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(filled[i][j]>1){System.out.println("COPY: " + filled[i][j] + " "+
						i + " " + j);}
				if(filled[i][j]==0)
					nextGen.add(new EmptyCell(i,j));
			}
		}
	}
	
	
	private void removeUsedSpots(ArrayList<EmptyCell> available, ArrayList<Cell> newCells){
		if(available ==null || newCells ==null || available.size()==0 
				|| newCells.size()==0){return;}
		
		for(int i=0; i<available.size(); i++){
			for(int j=0; j<newCells.size(); j++){
				if(available.get(i).getMyLocation().
						equals(newCells.get(j).
						getMyLocation())){
					available.remove(i);
					if(i>0)
						i--;
					else
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
	private Collection<Cell> updateCell(Cell c, Collection<EmptyCell> available){
		int size = getSize();
		Collection<Cell> newCells =  c.update(getCurrentCells(), available, size);
		return newCells;
	}
	
	public Collection<Cell> getCurrentCellsCopy(){
		ArrayList<Cell> copy = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			Cell newCell = c.createCopy();
			copy.add(newCell);
		}
		return copy;
	}
	
	private ArrayList<Cell> getCategoryCells(String className){
		ArrayList<Cell> toRet = new ArrayList<Cell>();
		for(Cell c: getCurrentCells()){
			if(c.getClass().getName().equals("cellular_level." + className)){
				toRet.add(c);
			}
		}
		return toRet;
	}
	
	public Collection<Cell> getCurrentCells(){
		return currentCells;
	}
	
	public void setCurrentCells(Collection<Cell> current){
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
