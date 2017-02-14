package util;

import data_structures.PatchName;
import patch_level.EmptyPatch;
import patch_level.Patch;
import patch_level.SlimePatch;
import patch_level.SugarPatch;

/**
 * Similar to the CellGenerator class, generates an instance of a Patch from the
 * enum describing it.
 * 
 * @author maddiebriere
 *
 */

public class PatchGenerator {
	public static Patch newPatch(PatchName name) {
		switch (name) {
		case SLIME_PATCH:
			return new SlimePatch();
		case SUGAR_PATCH:
			return new SugarPatch();
		default:
			return new EmptyPatch();
		}
	}
}
