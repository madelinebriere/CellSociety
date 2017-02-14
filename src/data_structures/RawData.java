package data_structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic class to pass variables in Each type of society will handle this
 * differently, parsing information and checking it for validity before
 * assigning it to a variable.
 * 
 * USE NOTE: THE ORDER OF PARSING IS DICTATED IN EACH CELLSOCIETY CLASS -- REFER
 * TO THESE TO LEARN HOW TO PASS INFORMATION THROUGH THIS DATA STRUCTURE
 * 
 * @author maddiebriere
 *
 */

public class RawData {
	private List<Integer> integerVariables;
	private List<Double> doubleVariables;

	public RawData() {
		integerVariables = new ArrayList<Integer>();
		doubleVariables = new ArrayList<Double>();
	}

	public RawData(List<Integer> integerVariables, List<Double> doubleVariables) {
		this.integerVariables = integerVariables;
		this.doubleVariables = doubleVariables;
	}

	public List<Integer> getIntegerVariables() {
		return integerVariables;
	}

	public void setIntegerVariables(List<Integer> integerVariables) {
		this.integerVariables = integerVariables;
	}

	public List<Double> getDoubleVariables() {
		return doubleVariables;
	}

	public void setDoubleVariables(List<Double> doubleVariables) {
		this.doubleVariables = doubleVariables;
	}

}
