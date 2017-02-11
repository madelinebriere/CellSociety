package util;

import data_structures.PatchName;
import patch_level.EmptyPatch;
import patch_level.Patch;
import patch_level.SlimePatch;

public class PatchGenerator {
	public static Patch newPatch(PatchName name){
		switch(name){
		case SLIME_PATCH:
			return new SlimePatch();
		default:
			return new EmptyPatch();
		}
	}
}
