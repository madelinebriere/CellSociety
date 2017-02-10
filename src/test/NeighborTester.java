package test;
import societal_level.*;
import util.CellData;
import cellular_level.*;

/**
 * Class used for testing that each cell (especially border cells) is
 * recording the correct number of neighbors (e.g., while LifeSociety allows
 * diagonal neighbors, allowing up to 8 neighbors, WaterSociety should not
 * allow diagonal neighbors, but should allow wrapped neighbors, meaning that every 
 * cell should have exactly four neighbors)
 * 
 * @author maddiebriere
 *
 */

public class NeighborTester {
	private static CellSociety mySociety;
	
	public static void setSociety(CellSociety s){
		mySociety=s;
	}
	public static void main (String[]args){
		//tests();
	}
	
	public static void tests(){
		/*System.out.println("LifeSociety");
		neighborsTest(new LifeSociety());
		System.out.println("\nFireSociety");
		neighborsTest(new FireSociety());
		System.out.println("\nPopSociety");
		neighborsTest(new PopSociety());
		System.out.println("\nWaterSociety");
		neighborsTest(new WaterSociety());*/
	}
	
	public static void neighborsTest(CellSociety s){
		printNeighbors(new CellData(s));
	}
	
	public static void printNeighbors(CellData d){
		for(Cell c: d.getCurrentCellsCopy()){
			System.out.print("Row: "+ c.getMyRow());
			System.out.print(" Col: " + c.getMyCol());
			System.out.print(" Neighbors: " + d.getNumberNeighbors(c));
			System.out.print("     ");
			if(c.getMyRow()==0){
				System.out.print("*Top Row ");
			}
			if(c.getMyRow()==d.getY()-1){
				System.out.print("*Bottom Row ");
			}
			if(c.getMyCol()==0){
				System.out.print("*Left Col ");
			}
			if(c.getMyCol()==d.getX()-1){
				System.out.print("*Right Col ");
			}
			System.out.println();
		}
	}
}
