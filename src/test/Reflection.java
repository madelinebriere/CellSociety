/**
 * This class was created to learn how to use Java Reflection.
 * 
 * @author Stone Mathers
 */

package test;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import file_handling.FireSimulation;
import file_handling.SimulationType;

public class Reflection {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		List<String> list = Arrays.asList(new String[]{"4 5 LIVE"});
		SimulationType sim;
		
		try{
		Class fire = FireSimulation.class;
		Class[] parameters = new Class[2];
		parameters[0] = Map.class;
		parameters[1] = List.class;
		Constructor<SimulationType> ct = fire.getConstructor(parameters);
		Object[] argList = new Object[2];
		argList[0] = map;
		argList[1] = list;
		sim = (SimulationType)ct.newInstance(argList);
		} catch (Throwable e){
			throw new RuntimeException(e);
		}
		
		System.out.println(sim.getClass());
		System.out.println(sim.getDataTypes());

	}

}
