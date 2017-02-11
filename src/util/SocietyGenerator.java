package util;

import data_structures.SimulationName;
import data_structures.*;
import societal_level.CellSociety;

public class SocietyGenerator {

	public static <T extends CellSociety> generateSociety(BoardData board){
		SimulationName name = board.getName();
		switch(name){
		case FIRE_SOCIETY:
			return new FireSociety();
		}
	}
}
