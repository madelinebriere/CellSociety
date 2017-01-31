/**
 * Non-burning tree cell in the forest fire CA simulation
 * Either remains healthy with the update or
 * changes into a burning cell (BurnCell)
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

import util.Location;

public class TreeCell extends Cell {
	private static double probCatch;
	private static Color treeColor = Color.FORESTGREEN;
	private Random randy = new Random();

	
	public TreeCell(){
		super();
		setMyState(treeColor);
	}
	
	public TreeCell(int row, int col){
		super(row, col, treeColor);
	}
	
	/**
	 * @param neighbors Cell neighbors
	 * @param nullCells Cells with no current occupants, stored as nulls
	 * @return An ArrayList of Cells for the next generation of Cells. This ArrayList will
	 * either contain the current Cell, if it does not catch fire, or a new BurnCell in the
	 * same location (if the tree has caught fire)
	 */
	@Override
	protected ArrayList<Cell> update(ArrayList<Cell> neighbors, ArrayList<EmptyCell> nullCells, int size) {
		ArrayList <Cell> nextGen = new ArrayList<Cell>();
		if(numberBurningTrees(neighbors)>=1 && catchFire()){
			BurnCell child = new BurnCell();
			child.copyLocation(this);
			nextGen.add(child);
		}
		else{
			nextGen.add(this);
		}
		return null;
	}
	
	private int numberBurningTrees(ArrayList<Cell> neighbors){
		int numBurn = 0;
		for(Cell c: neighbors){
			if(c instanceof BurnCell){
				numBurn++;
			}
		}
		return numBurn;
	}
	
	private boolean catchFire(){
		int random = randy.nextInt(100);
		return random<=(probCatch*100);
	}

	public static double getProbCatch() {
		return probCatch;
	}

	public static void setProbCatch(double probCatch) {
		TreeCell.probCatch = probCatch;
	}

	public static Color getTreeColor() {
		return treeColor;
	}

	public static void setTreeColor(Color treeColor) {
		TreeCell.treeColor = treeColor;
	}

	public Random getRandy() {
		return randy;
	}

	public void setRandy(Random randy) {
		this.randy = randy;
	}
	
	

}