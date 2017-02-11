package util;

import patch_level.*;

import data_structures.PatchName;

public class PatchGenerator {
	//Add this to SimulationType as well -- replace duplicate code with this
	
	public static Patch newPatch(PatchName c){
		if(c == PatchName.SLIME_PATCH)
			return new SlimePatch();
		else
			return new EmptyPatch();
	}
	
}
