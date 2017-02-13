package GUI;

import java.util.HashMap;
import java.util.Map;

import data_structures.CellName;
import data_structures.CellRatio;
import data_structures.CellRatioMap;
import data_structures.SimulationData;
import data_structures.SimulationName;
import societal_level.*;

public class SocietyMaker {
	public static CellSociety generateCellSociety(SimulationData simData) {
		switch (simData.getSimulationName()) {
		case FIRE_SOCIETY:
			System.out.println("Fire");
			return new FireSociety(simData);
		case WATER_SOCIETY:
			System.out.println("Water");
			return new WaterSociety(simData);
		case GAME_OF_LIFE:
			System.out.println("Life");
			return new LifeSociety(simData);
		case SLIME_SOCIETY:
			System.out.println("Slime");
			return new SlimeSociety(simData);
		default:
			System.out.println("Segregation");
			return new PopSociety(simData);
		}
	}

	public static CellRatioMap getDefaultCellRatioValues(SimulationName name) {
		Map<CellName, CellRatio> ratios = new HashMap<CellName, CellRatio>();
		switch (name) {
		case FIRE_SOCIETY:
			ratios.put(CellName.BURN_CELL, new CellRatio(0.1));
			ratios.put(CellName.TREE_CELL, new CellRatio(0.9));
			break;
		case WATER_SOCIETY:
			ratios.put(CellName.FISH_CELL, new CellRatio(0.6));
			ratios.put(CellName.SHARK_CELL, new CellRatio(0.1));
			break;
		case GAME_OF_LIFE:
			ratios.put(CellName.LIVE_CELL, new CellRatio(0.4));
			ratios.put(CellName.DEAD_CELL, new CellRatio(0.6));
			break;
		case POPULATION_SOCIETY:
			ratios.put(CellName.HOUSE_CELL_1, new CellRatio(0.25));
			ratios.put(CellName.HOUSE_CELL_2, new CellRatio(0.25));
			ratios.put(CellName.HOUSE_CELL_3, new CellRatio(0.25));
			break;
		case SLIME_SOCIETY:
			ratios.put(CellName.SLIME_CELL, new CellRatio(.15));
			break;
		}
		return new CellRatioMap(ratios);
	}
}
