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
	private static int numForLive = 3;
	private static Color deadColor = Color.WHITE;
	
	public DeadCell(){
		super();
		setMyState(deadColor);
	}
	
	public DeadCell(int row, int col){
		super(row,col, deadColor);
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
	public static int getNumForLive() {
		return numForLive;
	}
	public static void setNumForLive(int numForLive) {
		DeadCell.numForLive = numForLive;
	}
	public static Color getDeadColor() {
		return deadColor;
	}
	public static void setDeadColor(Color deadColor) {
		DeadCell.deadColor = deadColor;
	}
	
	
	
}