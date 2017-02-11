/**
 * Class representing dead cells in the Game of Life Simulation
 * Capable of active updates based on surroundings
 * 
 * @author maddiebriere
 */
package cellular_level;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import data_structures.CellData;
import util.Location;
public class DeadCell extends LifeSimCell {
	private static final Color DEAD_COLOR = Color.WHITE;
	private static final int NUM_FOR_LIVE = 3;
	
	private int numForLive;
	
	public DeadCell(){
		this(0,0);
	}
	
	public DeadCell(int row, int col){
		this(row,col, DEAD_COLOR);
	}
	
	public DeadCell(int row, int col, Color color){
		this(row,col,color, NUM_FOR_LIVE);
	}
	
	public DeadCell(int row, int col, Color color, int num){
		super(row,col,color);
		setNumForLive(num);
	}
	protected void changeState(CellData data, ArrayList<Cell> newGen){
		int numLive = data.countDiffNeighbors(this);
		if(isReadyToLive(numLive)){
			generateLiveCell(newGen);
		}
		else{
			stayDead(newGen);
		}
	}
	
	private void generateLiveCell(ArrayList<Cell>newGen){
		LiveCell child = new LiveCell();
		child.copyLocation(this);
		newGen.add(child);
	}
	
	private void stayDead(ArrayList<Cell> newGen){
		newGen.add(this);
	}
	
	private boolean isReadyToLive(int numLive){
		return numLive == numForLive;
	}
	public int getNumForLive() {
		return numForLive;
	}
	public void setNumForLive(int numForLive) {
		this.numForLive = numForLive;
	}
	
	
	
}