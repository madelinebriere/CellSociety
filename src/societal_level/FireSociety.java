package societal_level;

import java.util.ArrayList;
import java.util.Collection;

import cellular_level.*;
import file_handling.*;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety representing the forest fire simulation
 * 
 * @author maddiebriere
 *
 */

public class FireSociety extends CellSociety {
	private static final int DEFAULT_SIZE = 10;
	private static final Color EMPTY_COLOR = Color.YELLOW;

	// default
	public FireSociety() {
		this(DEFAULT_SIZE);

	}

	public FireSociety(int size) {
		super(makeCells(size), size, EMPTY_COLOR);
	}

	public FireSociety(SimulationType sim) {
		super(sim);
		if (sim instanceof FireSimulation) {
			setVariables((FireSimulation) sim);
		}

	}

	public FireSociety(Collection<Cell> currentCells, int size, Color emptyColor) {
		super(currentCells, size, emptyColor);
	}

	@Override
	public Collection<Cell> makeBorderCells(int oldSize, int newSize) {
		ArrayList<Cell> newCells = new ArrayList<Cell>();
		for (int i = 0; i < newSize; i++) {
			for (int j = 0; j < newSize; j++) {
				if (i >= oldSize || j >= oldSize)
					newCells.add(new TreeCell(i, j));
			}
		}
		return newCells;
	}

	private static ArrayList<Cell> makeCells(int size) {
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		int center = size / 2;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!(i == center && j == center)) {
					makeCells.add(new TreeCell(i, j));
				} else {
					makeCells.add(new BurnCell(i, j));
				}
			}
		}
		return makeCells;
	}

	private void setVariables(FireSimulation sim) {
		TreeCell.setProbCatch(sim.getProbability());
		BurnCell.setStepsToBurn(sim.getSteps());
		setEmptyColor(EMPTY_COLOR);
	}

	@Override
	public Color[][] step() {
		return totalStep();
	}

	@Override
	public Collection<Cell> neighbors(Cell c) {
		return getNeighbors(c);
	}

}
