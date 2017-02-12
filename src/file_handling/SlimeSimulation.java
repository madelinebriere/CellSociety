package file_handling;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
}
