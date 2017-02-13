//Author Talha Koc
package societal_level;

import data_structures.Dimensions;
import data_structures.PatchName;
import patch_level.EmptyPatch;
import patch_level.Patch;
import patch_level.SlimePatch;
import util.Location;

public class SocietyResizer {
	/**
	 * 
	 * @param patches
	 *            : some patches have cells, some dont
	 * @param patchName
	 *            : type of patch that should be added to new grid
	 * @param range
	 *            : length to edges that the checker will look in for cells
	 * @param expandBy
	 *            : the amount each side should be expanded by
	 * @return new patch array with updated dimensions if there are any cells
	 *         within the given range else: returns null
	 */
	public static Patch[][] checkEdgesForExpansion(Patch[][] patches, PatchName patchName, int range, int expandBy) {
		// check if cells exists
		boolean cellExistsWithinRange = false;
		int maxX = patches.length - 1;
		int maxY = patches[0].length - 1;
		for (int r = 0; r < range; r++) {
			// check left edge:::iterate downwards from top to bottom for q =
			// 0...vertical length
			for (int q = 0; q <= maxY; q++) {
				if (patches[r][q].getMyCell() != null)
					cellExistsWithinRange = true;
			}

			// check right edge
			for (int w = 0; w <= maxY; w++) {
				if (patches[maxX - r][w].getMyCell() != null)
					cellExistsWithinRange = true;
			}

			// check top edge
			for (int e = 0; e <= maxX; e++) {
				if (patches[e][r].getMyCell() != null)
					cellExistsWithinRange = true;
			}
			for (int k = 0; k <= maxX; k++) {
				if (patches[k][maxY - r].getMyCell() != null)
					cellExistsWithinRange = true;
			}
		}
		if (!cellExistsWithinRange) {
			return null;
		} else {
			System.out.println("detected cell within a range of " + range + " units to an edge ");
			return expandGridToDimensions(
					new Dimensions(patches.length + expandBy * 2, patches[0].length + expandBy * 2),
					patchName, patches,
					expandBy);
		}
	}

	private static Patch[][] expandGridToDimensions(Dimensions newDimensions, 
			PatchName patchName, 
			Patch[][] patches,
			int emptyCellWidth) 
	{
		Patch[][] newPatches = new Patch[newDimensions.getX()][newDimensions.getY()];

		for (int i = 0; i < newDimensions.getX(); i++) {
			for (int j = 0; j < newDimensions.getY(); j++) {
				if (i >= emptyCellWidth && i <= (newDimensions.getX() - emptyCellWidth * 2 ) 
						&& j>= emptyCellWidth && j<= (newDimensions.getY() - emptyCellWidth*2)) {
					newPatches[i][j] = patches[i-emptyCellWidth][j-emptyCellWidth];
					Location old = newPatches[i][j].getMyLocation();
					if  (newPatches[i][j].getMyCell() != null){
						newPatches[i][j].getMyCell().setMyLocation(new Location(old.getMyRow() + emptyCellWidth, old.getMyCol() + emptyCellWidth));
					}
					newPatches[i][j].setMyLocation(new Location(old.getMyRow() + emptyCellWidth, old.getMyCol() + emptyCellWidth));
				} else {
					newPatches[i][j] = getPatchForName(patchName);
					newPatches[i][j].setMyLocation(new Location(i,j));
				}
			}
		}
		return newPatches;
	}

	private static Patch getPatchForName(PatchName name) {
		switch (name) {
		case SLIME_PATCH:
			return new SlimePatch();
		default:
			return new EmptyPatch();
		}
	}
}
