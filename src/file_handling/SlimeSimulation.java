package file_handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import data_structures.SimulationName;

public class SlimeSimulation extends SimulationType {

	private static final List<String> SETTING_TYPES = Arrays.asList(new String[] {
			"threshold",
			"evaporationRate",
			"diffusionRate",
			"depositRate"
	    });

	public SlimeSimulation(Map<String, String> values, List<String> cells) {
		super(values, cells);
		this.settingTypes = SETTING_TYPES;
		this.dataTypes = combineDataTypes();
	}

	public Double getThreshold(){
		try{
			return Double.parseDouble(getDataValues().get(SETTING_TYPES.get(0)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(0)));
		}
	}
	
	public Integer getEvaporationRate(){
		try{
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(1)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(1)));
		}
	}
	
	public Integer getDiffusionRate(){
		try{
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(2)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(2)));
		}
	}
	
	public Integer getDepositRate(){
		try{
			return Integer.parseInt(getDataValues().get(SETTING_TYPES.get(3)));
		}catch(Exception e){
			throw new XMLException(e, String.format(myResources.getString("InvalidData"), SETTING_TYPES.get(3)));
		}
	}

	@Override
	protected List<Integer> getIntegerData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		//data.add(get());
		return data;
	}

	@Override
	protected List<Double> getDoubleData() {
		ArrayList<Double> data = new ArrayList<Double>();
		//data.add(getProbability());
		return data;
	}

	@Override
	protected SimulationName getSimulationName() {
		return SimulationName.SLIME_SOCIETY;
	}
}
