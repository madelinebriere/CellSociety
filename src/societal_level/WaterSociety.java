package societal_level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import cellular_level.*;
import file_handling.*;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety representing the Water World simulation
 * 
 * @author maddiebriere
 *
 */

public class WaterSociety extends CellSociety {
	private static Random rnd = new Random();
	private static final int DEFAULT_SIZE = 40;
	private static final Color EMPTY_COLOR = Color.LIGHTBLUE;

	public WaterSociety() {
		this(DEFAULT_SIZE);
	}

	public WaterSociety(int size) {
		super(makeCells(size), size, EMPTY_COLOR);
	}

	public WaterSociety(SimulationType water) {
		super(water);
		if (water instanceof WaterSimulation) {
			setVariables((WaterSimulation) water);
		}
		setEmptyColor(EMPTY_COLOR);
	}

	public WaterSociety(Collection<Cell> currentCells, int size, Color emptyColor) {
		super(currentCells, size, emptyColor);
	}

	@Override
	public Collection<Cell> makeBorderCells(int oldSize, int newSize) {
		ArrayList<Cell> newCells = new ArrayList<Cell>();
		for (int i = 0; i < newSize; i++) {
			for (int j = 0; j < newSize; j++) {
				if (i >= oldSize || j >= oldSize)
					newCells.add(new EmptyCell(i, j));
			}
		}
		return newCells;
	}

	private static ArrayList<Cell> makeCells(int size) {
		ArrayList<Cell> makeCells = new ArrayList<Cell>();
		for (int i = 0; i < size; i++) {
			makeCells.add(new SharkCell(0, i));
		}
		for (int i = 1; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (rnd.nextBoolean()) {
					makeCells.add(rnd.nextBoolean() ? new FishCell(i, j) : new FishCell(i, j));
				} else {
					makeCells.add(new FishCell(i, j));
				}
			}
		}
		return makeCells;
	}

	public void setVariables(WaterSimulation water) {
		FishCell.setStepsToBreed(water.getFishBreed());
		SharkCell.setStepsToBreed(water.getSharkBreed());
		SharkCell.setStepsToStarve(water.getSharkStarve());
	}

	public Color[][] step() {
		return orderedStep();
	}

	@Override
	public Collection<Cell> neighbors(Cell c) {
		return getWrappedCardinalNeighbors(c);
	}

}
