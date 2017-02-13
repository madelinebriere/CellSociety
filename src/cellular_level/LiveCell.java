
package cellular_level;
import javafx.scene.paint.Color;
import data_structures.CellData;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing live cells in the Game of Life Simulation
 * Capable of active updates based on surroundings
 * --> Checks for overpopulation
 * --> Checks for underpopulation
 * 
 * @author maddiebriere
 */

public class LiveCell extends LifeSimCell {
	private static Color LIVE_COLOR = Color.BLACK;
	public static int UNDER_POP = 2;
	public static int OVER_POP=3;
	
	
	private static int underpopulation;
	private static int overpopulation;
	
	public LiveCell(){
		this(0,0);
	}
	
	public LiveCell(int row, int col){
		this(row,col, LIVE_COLOR);
	}
	
	public LiveCell(int row, int col, Color color){
		this(row,col,color, UNDER_POP, OVER_POP);
	}
	public LiveCell(int row, int col, Color color, int under, int over){
		super(row,col,color);
		setUnderpopulation(under);
		setOverpopulation(over);
	}
	
	protected void changeState(CellData data, ArrayList<Cell> newGen){
		int numLive = data.countSameNeighbors(this);
		if(isOverpopulated(numLive)||isUnderpopulated(numLive)){
			generateDeadCell(newGen);
		}
		else{
			stayAlive(newGen);
		}
	}
	
	private void generateDeadCell(ArrayList<Cell>newGen){
		DeadCell child = new DeadCell();
		child.copyLocation(this);
		newGen.add(child);
	}
	
	private void stayAlive(ArrayList<Cell> newGen){
		newGen.add(this);
	}
	
	
	private boolean isOverpopulated(int numLive){
		return numLive>overpopulation;
	}
	
	private boolean isUnderpopulated(int numLive){
		return numLive<underpopulation;
	}
	public int getUnderpopulation() {
		return underpopulation;
	}
	public void setUnderpopulation(int underpopulation) {
		LiveCell.underpopulation = underpopulation;
	}
	public int getOverpopulation() {
		return overpopulation;
	}
	public  void setOverpopulation(int overpopulation) {
		LiveCell.overpopulation = overpopulation;
	}
	
	
}