package util;

import borders.*;
import data_structures.SimulationData;

public class BorderChooser {
	
	public static Border chooseBorder(SimulationData sim){
		Border border = new FiniteBorder(sim.getDimensions());
		switch(sim.getBorder()){
		case TOROIDAL:
			border = new ToroidalBorder(sim.getDimensions());
			break;
		case INFINITE:
			border = new InfiniteBorder(sim.getDimensions());
			break;
		}
		return border;
	}
	
}
