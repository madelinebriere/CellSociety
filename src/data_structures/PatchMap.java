package data_structures;

import java.util.List;

import patch_level.Patch;

/**
 * Intended use: Similar to CellRatioMap
 * 
 * Meant to hold patch information to be conveyed to the CellSociety
 * 
 * @author maddiebriere
 *
 */

public class PatchMap {
	private List<Patch> patches;

	public PatchMap(List<Patch> patches) {

	}

	public List<Patch> getPatches() {
		return patches;
	}

	public void setPatches(List<Patch> patches) {
		this.patches = patches;
	}

}
