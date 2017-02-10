//author Talha Koc

package data_structures;

import javafx.scene.paint.Color;
import sim_rules.SimRules;

public class SimulationData {
	private SimulationName name;
	private BoardData board;
	private SocietyData society;
	private SimRules sim;

	
	public SimulationData(SimulationName id, BoardData b, SocietyData s, SimRules sr){
		name = id;
		board = b;
		society = s;
		sim=sr;
	}

	public SimulationName getName() {
		return name;
	}

	public BoardData getBoard() {
		return board;
	}

	public SocietyData getSociety() {
		return society;
	}
	
	public Dimensions getDimensions() {
		return board.getDimensions();
	}
	
	public BorderType getBorder() {
		return board.getBorder();
	}
	
	public CellShape getShape(){
		return board.getShape();
	}

	public boolean isGetAllNeighbors() {
		return society.isGetAllNeighbors();
	}

	public Color getEmptyCellColor() {
		return society.getEmptyCellColor();
	}

	public CellRatioMap getRatios() {
		return society.getRatios();
	}

	public SimRules getSim() {
		return sim;
	}
	
	
	
}

