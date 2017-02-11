package util;

import borders.*;
import data_structures.BoardData;

public class BorderChooser {
	
	public static Border chooseBorder(BoardData sim){
		Border border;
		switch(sim.getBorder()){
		case TOROIDAL:
			border = new ToroidalBorder(sim.getDimensions());
			break;
		case INFINITE:
			border = new InfiniteBorder(sim.getDimensions());
			break;
		default:
			border = new FiniteBorder(sim.getDimensions());
			break;
		}
		return border;
	}
	
}
