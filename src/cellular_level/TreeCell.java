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
import java.util.List;
import java.util.Random;
import util.CellData;
public class TreeCell extends Cell {
	private static final double PROB_CATCH = .3;
	private static final Color TREE_COLOR = Color.FORESTGREEN;
	
	private double probCatch;
	private Random randy = new Random();
	
	public TreeCell(){
		this(0,0);
	}
	
	public TreeCell(int row, int col){
		this(row, col, TREE_COLOR);
	}
	
	public TreeCell(int row, int col, Color color){
		this(row, col, color, PROB_CATCH);
	}
	
	public TreeCell(int row, int col, Color color, double prob){
		super(row, col, color);
		setProbCatch(prob);
	}
	
	@Override
	public Cell createCopy(){
		TreeCell copy = new TreeCell();
		copy.basicCopy(this);
		return copy;
	}
	
	/**
	 * @param data CellData object holding info for Cell
	 * @return An ArrayList of Cells for the next generation of Cells. This ArrayList will
	 * either contain the current Cell, if it does not catch fire, or a new BurnCell in the
	 * same location (if the tree has caught fire)
	 */
	@Override
	public List<Cell> update(CellData data) {
		ArrayList <Cell> nextGen = new ArrayList<Cell>();
		if(countBurnTrees(data)>=1 && catchFire()){
			BurnCell child = new BurnCell();
			child.copyLocation(this);
			nextGen.add(child);
		}
		else{
			nextGen.add(this);
		}
		return nextGen;
	}
	
	private int countBurnTrees(CellData data){
		int count=0;
		for(Cell c: data.getNeighbors(this)){
			if(c instanceof BurnCell){
				count++;
			}
		}
		return count;
	}
	
	private boolean catchFire(){
		int random = randy.nextInt(100);
		return random<=(probCatch*100);
	}
	public double getProbCatch() {
		return probCatch;
	}
	public void setProbCatch(double probCatch) {
		this.probCatch = probCatch;
	}
	
	
}