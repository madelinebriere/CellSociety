package societal_level;

import borders.*;
import data_structures.SimulationData;

public class BorderChooser {
	
	public static Border chooseBorder(SimulationData sim){
		Border border = new FiniteBorder();
		switch(sim.getBorder()){
		case TOROIDAL:
			border = new ToroidalBorder();
			break;
		case INFINITE:
			border = new InfiniteBorder();
			break;
		}
		return border;
	}
	
}
