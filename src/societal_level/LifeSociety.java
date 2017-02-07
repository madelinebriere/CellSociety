package societal_level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import cellular_level.*;
import file_handling.*;
import javafx.scene.paint.Color;

/**
 * Extension of CellSociety representing the Game of Life simulation
 * 
 * @author maddiebriere
 *
 */

public class LifeSociety extends CellSociety {
	private static final int DEFAULT_SIZE = 10;
	private static final Color EMPTY_COLOR = Color.WHITE;

	public LifeSociety() {
		this(DEFAULT_SIZE);
	}

	public LifeSociety(SimulationType life) {
		super(life);
		setEmptyColor(EMPTY_COLOR);
	}

	public LifeSociety(int size) {
		super(makeCells(size), size, EMPTY_COLOR);
	}

	public LifeSociety(Collection<Cell> currentCells, int size, Color emptyColor) {
		super(currentCells, size, emptyColor);
	}

	private static ArrayList<Cell> makeCells(int size) {
		Random rnd = new Random();
		ArrayList<Cell> makeCells = new ArrayList<Cell>();

		// RANDOM
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				makeCells.add(rnd.nextBoolean() ? new LiveCell(i, j) : new DeadCell(i, j));

			}
		}

		// BLINKER
		/*
		 * for(int i=0; i<size; i++){ for(int j=0; j<size; j++){ if(!((i==4) &&
		 * (j==3 || j==4 || j==5))) makeCells.add(new DeadCell(i,j)); else
		 * makeCells.add(new LiveCell(i,j));
		 * 
		 * } }
		 */

		// BEACON
		/*
		 * for(int i=0; i<size; i++){ for(int j=0; j<size; j++){ if(!(( (i==4 ||
		 * i==5) && ( j==4 || j==5 ) )|| ( (i==6 || i==7) && (j==6 || j==7) )))
		 * makeCells.add(new DeadCell(i,j)); else makeCells.add(new
		 * LiveCell(i,j));
		 * 
		 * } }
		 */
		return makeCells;
	}

	@Override
	public Collection<Cell> makeBorderCells(int oldSize, int newSize) {
		ArrayList<Cell> newCells = new ArrayList<Cell>();
		for (int i = 0; i < newSize; i++) {
			for (int j = 0; j < newSize; j++) {
				if (i >= oldSize || j >= oldSize)
					newCells.add(new DeadCell(i, j));
			}
		}
		return newCells;
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
