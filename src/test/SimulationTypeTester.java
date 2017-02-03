package test;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

import file_handling.*;

public class SimulationTypeTester {

	public static void main(String[] args) {
		TreeMap<String, String> data = new TreeMap<String, String>();
		ArrayList<String> cells = new ArrayList<String>();
		
		fillLife(data, cells);
		SimulationType sim = new LifeSimulation(data, cells);
		printLife(sim);
		
		fillFire(data, cells);
		sim = new FireSimulation(data, cells);
		printFire(sim);
		
		fillPop(data, cells);
		sim = new PopSimulation(data, cells);
		printPop(sim);
		
		fillWater(data, cells);
		sim = new WaterSimulation(data, cells);
		printWater(sim);

	}
	
	public static void fillLife(Map<String, String> map, List<String> array){
		map.clear();
		map.put("title", "LifeSim");
		map.put("author", "Stone Mathers");
		map.put("dimension", "10");
		
		array.clear();
		array.add("1 2 LIVE");
		array.add("4 5 DEAD");
		array.add("3 1 LIVE");
	}
	
	public static void fillFire(Map<String, String> map, List<String> array){
		map.clear();
		map.put("title", "FireSim");
		map.put("author", "Stone Mathers");
		map.put("dimension", "10");
		map.put("probability", "0.35");
		map.put("steps", "5");
		
		array.clear();
		array.add("1 2 Tree");
		array.add("4 5 bUrN");
		array.add("3 1 treE");
	}
	
	public static void fillPop(Map<String, String> map, List<String> array){
		map.clear();
		map.put("title", "PopSim");
		map.put("author", "Stone Mathers");
		map.put("dimension", "10");
		map.put("threshold", "0.6");
		
		array.clear();
		array.add("1 2 BluE");
		array.add("4 5 red");
		array.add("3 1 BLUE");
	}
	
	public static void fillWater(Map<String, String> map, List<String> array){
		map.clear();
		map.put("title", "WaterSim");
		map.put("author", "Stone Mathers");
		map.put("dimension", "10");
		map.put("fish breed", "3");
		map.put("shark breed", "5");
		map.put("shark starve", "10");
		
		array.clear();
		array.add("1 2 SHArk");
		array.add("4 5 fiSH");
		array.add("3 1 SHARK");
	}
	
	public static void printLife(SimulationType sim){
		LifeSimulation lifeSim = (LifeSimulation)sim;
		System.out.println(lifeSim.getTitle());
		System.out.println(lifeSim.getAuthor());
		System.out.println(lifeSim.getDimension());
		System.out.println(lifeSim.getCells());
		System.out.println();
	}
	
	public static void printFire(SimulationType sim){
		FireSimulation fireSim = (FireSimulation)sim;
		System.out.println(fireSim.getTitle());
		System.out.println(fireSim.getAuthor());
		System.out.println(fireSim.getDimension());
		System.out.println(fireSim.getCells());
		System.out.println(fireSim.getProbability());
		System.out.println(fireSim.getSteps());
		System.out.println();
	}
	
	public static void printPop(SimulationType sim){
		PopSimulation popSim = (PopSimulation)sim;
		System.out.println(popSim.getTitle());
		System.out.println(popSim.getAuthor());
		System.out.println(popSim.getDimension());
		System.out.println(popSim.getCells());
		System.out.println(popSim.getThreshold());
		System.out.println();
	}
	
	public static void printWater(SimulationType sim){
		WaterSimulation waterSim = (WaterSimulation)sim;
		System.out.println(waterSim.getTitle());
		System.out.println(waterSim.getAuthor());
		System.out.println(waterSim.getDimension());
		System.out.println(waterSim.getCells());
		System.out.println(waterSim.getFishBreed());
		System.out.println(waterSim.getSharkBreed());
		System.out.println(waterSim.getSharkStarve());
		System.out.println();
	}

}
