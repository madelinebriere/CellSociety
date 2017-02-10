package neighbors;

import java.util.ArrayList;
import java.util.Collection;

import cellular_level.Cell;
import util.Location;

/**
 * Class to define neighbors
 * @author maddiebriere
 *
 */

public abstract class Neighbors {

	/*
	
	*//**
	 * Normal neighbors function, gets any adjacent cells
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Neighbors of Cell c
	 *//*
	protected Collection<Cell> getNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	*//**
	 * Wrapped neighbors function, gets any adjacent cells, INCLUDING those
	 * cells that may be accessible across the board (moving from the far left
	 * to the far right, or the top to the bottom)
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Wrapped neighbors of Cell c
	 *//*
	protected Collection<Cell> getWrappedNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isAnyAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	*//**
	 * Limited neighbors function, gets any adjacent cells IN THE CARDINAL
	 * DIRECTIONS (N, S, E, W)
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Cardinal neighbors of Cell c
	 *//*
	protected Collection<Cell> getCardinalNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isCardinalAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	*//**
	 * Limited neighbors function, gets any adjacent cells IN THE CARDINAL
	 * DIRECTIONS (N, S, E, W), includes movement across the board
	 * 
	 * @param c
	 *            Cell of interest
	 * @return Cardinal neighbors of Cell c
	 *//*
	protected Collection<Cell> getWrappedCardinalNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Cell possible : currentCells) {
			if (isWrappedCardinalAdjacent(c, possible)) {
				neighbors.add(possible);
			}
		}
		return neighbors;
	}

	*//**
	 * Adjacency in common sense
	 * 
	 * @param c1
	 *            target cell 1
	 * @param c2
	 *            target cell 2 (for comparison)
	 * @return true if adjacent, false otherwise
	 *//*
	public boolean isAdjacent(Cell c1, Cell c2) {
		return isAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isAdjacent(Location l1, Location l2) {
		return isCardinalAdjacent(l1, l2) || (oneAwayVertical(l1, l2) && oneAwayHorizontal(l1, l2));
	}

	*//**
	 * Adjacency, but only in cardinal directions (N, S, E, W)
	 * 
	 * @param c1
	 *            target cell 1
	 * @param c2
	 *            target cell 2 (for comparison)
	 * @return true if cardinal adjacent, false otherwise
	 *//*
	public boolean isCardinalAdjacent(Cell c1, Cell c2) {
		return isCardinalAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	public boolean isCardinalAdjacent(Location l1, Location l2) {
		return (sameColumn(l1, l2) && oneAwayVertical(l1, l2)) || (sameRow(l1, l2) && oneAwayHorizontal(l1, l2));
	}

	*//**
	 * Adjacency EVEN ACROSS BOARD (wrapped sides)
	 * 
	 * @param c1
	 *            target cell
	 * @param c2
	 *            target cell for comparison
	 * @return true if wrapped adjacent, false otherwise
	 *//*
	protected boolean isWrappedAdjacent(Cell c1, Cell c2) {
		return isWrappedAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isWrappedAdjacent(Location l1, Location l2) {
		return (sameColumn(l1, l2) && inRowAcrossBoard(l1, l2)) || (sameRow(l1, l2) && inColAcrossBoard(l1, l2));
	}

	*//**
	 * Adjacency EVEN ACROSS BOARD (wrapped sides), includes cardinal cells
	 * 
	 * @param c1
	 *            target cell
	 * @param c2
	 *            target cell for comparison
	 * @return true if wrapped adjacent, false otherwise
	 *//*
	protected boolean isWrappedCardinalAdjacent(Cell c1, Cell c2) {
		return isWrappedCardinalAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isWrappedCardinalAdjacent(Location l1, Location l2) {
		return isWrappedAdjacent(l1, l2) || isCardinalAdjacent(l1, l2);
	}

	*//**
	 * Includes both common adjacency and wrapped adjacency
	 * 
	 * @param c1
	 *            Target cell 1
	 * @param c2
	 *            Target cell 2
	 * @return true if any type of adjacent, false otherwise
	 *//*
	public boolean isAnyAdjacent(Cell c1, Cell c2) {
		return isAnyAdjacent(c1.getMyLocation(), c2.getMyLocation());
	}

	protected boolean isAnyAdjacent(Location l1, Location l2) {
		return isWrappedAdjacent(l1, l2) || isAdjacent(l1, l2);
	}

	*//**
	 * Check for adjacency by column ACROSS the board
	 * 
	 * @param l1
	 *            location 1
	 * @param l2
	 *            location 2 for comparison
	 * @return true if l1 and l2 are in adjacent columns, only considering
	 *         wrapped motion (e.g., if one cell is in the farthest left column
	 *         and the other is in the farthest right, this method will return
	 *         TRUE).
	 *//*
	private boolean inColAcrossBoard(Location l1, Location l2) {
		return (l1.getMyCol() == 0 && l2.getMyCol() == getX() - 1) || (l2.getMyCol() == 0 && l1.getMyCol() == getX() - 1);
	}

	*//**
	 * Check for adjacency by row ACROSS the board
	 * 
	 * @param l1
	 *            location 1
	 * @param l2
	 *            location 2 for comparison
	 * @return true if l1 and l2 are in adjacent row, only considering wrapped
	 *         motion (e.g., if one cell is in the top row and the other is in
	 *         the lowest row, this method will return TRUE).
	 *//*
	private boolean inRowAcrossBoard(Location l1, Location l2) {
		return (l1.getMyRow() == 0 && l2.getMyRow() == getY() - 1) || (l2.getMyRow() == 0 && l1.getMyRow() == getY() - 1);
	}

	*//**
	 * Check for same column/row
	 *//*

	private boolean sameColumn(Location l1, Location l2) {
		return l2.getMyCol() == l1.getMyCol();
	}

	private boolean sameRow(Location l1, Location l2) {
		return l2.getMyRow() == l1.getMyRow();
	}

	*//**
	 * Check distance in directions
	 *//*

	private boolean oneAwayVertical(Location l1, Location l2) {
		return oneAwayDown(l1, l2) || oneAwayUp(l1, l2);
	}

	private boolean oneAwayHorizontal(Location l1, Location l2) {
		return oneAwayRight(l1, l2) || oneAwayLeft(l1, l2);
	}

	*//**
	 * Check if l1 and l2 are one away in each direction (up, down, right,
	 * left), return true if this is the case, false otherwise
	 *//*

	private boolean oneAwayUp(Location l1, Location l2) {
		return ((l1.getMyRow() - 1) == l2.getMyRow());
	}

	private boolean oneAwayDown(Location l1, Location l2) {
		return ((l1.getMyRow() + 1) == l2.getMyRow());
	}

	private boolean oneAwayRight(Location l1, Location l2) {
		return ((l1.getMyCol() + 1) == l2.getMyCol());
	}

	private boolean oneAwayLeft(Location l1, Location l2) {
		return ((l1.getMyCol() - 1) == l2.getMyCol());
	}*/
}
