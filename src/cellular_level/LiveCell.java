/**
 * Class representing live cells in the Game of Life Simulation
 * Capable of active updates based on surroundings
 * --> Checks for overpopulation
 * --> Checks for underpopulation
 * 
 * @author maddiebriere
 */

package cellular_level;

import javafx.scene.paint.Color;
import util.CellData;

import java.util.ArrayList;
import java.util.List;

public class LiveCell extends LifeSimCell {
	private static int underpopulation = 2;
	private static int overpopulation = 3;
	private static Color liveColor = Color.BLACK;
	
	public LiveCell(){
		super();
		setMyState(liveColor);
	}
	
	public LiveCell(int row, int col){
		super(row,col, liveColor);
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

	public static int getUnderpopulation() {
		return underpopulation;
	}

	public static void setUnderpopulation(int underpopulation) {
		LiveCell.underpopulation = underpopulation;
	}

	public static int getOverpopulation() {
		return overpopulation;
	}

	public static void setOverpopulation(int overpopulation) {
		LiveCell.overpopulation = overpopulation;
	}

	public static Color getLiveColor() {
		return liveColor;
	}

	public static void setLiveColor(Color liveColor) {
		LiveCell.liveColor = liveColor;
	}
	
	
	

}
