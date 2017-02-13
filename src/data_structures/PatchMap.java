//author talha koc
package data_structures;

import java.util.List;

import patch_level.*;

/**
 * Intended use: Setup a 2d array of patches in gradient order
 * 
 * Meant to hold patch information to be conveyed to the CellSociety
 * 
 * @author talha koc
 *
 */

public class PatchMap {
	private int _layerLength;
	public static Patch[][] generateSugarPatchMap(int x, int y, PatchName patchName, int layerLength) {
		//init an array of patches
		Patch[][] patches = new Patch[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				patches[i][j] = newPatch(patchName, i, j);
			}
		}
		
		//
		int originX = x/2;
		int originY = y/2;
		int numberOfLayers = 3;
		int totalLayerLength = numberOfLayers * layerLength;
		for (int i=-totalLayerLength; i<totalLayerLength; i++){
			for (int j=-totalLayerLength; j<totalLayerLength; j++){
				patches[i][j].setConcentration(
						numberOfLayers + 1 - Math.max(Math.abs(i/numberOfLayers), Math.abs(j/numberOfLayers))
						);
			}
		}
		return patches;
	}

	private static Patch newPatch(PatchName patchName, int i, int j) {
		switch (patchName) {
		case SLIME_PATCH:
			return new SlimePatch(i,j);
		case SUGAR_PATCH:
			return new SugarPatch();
		default:
			return new EmptyPatch(i,j);
		}
	}
}
