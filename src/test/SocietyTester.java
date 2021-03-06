package test;

import java.util.ArrayList;
import cellular_level.Cell;
import societal_level.CellSociety;
/**
 * Basic test class for CellSocieties to bring out the number of each CellType 
 * and that the number of Cells in not to small or too big
 * 
 * NOTE: This tester was phased out in the second implementation 
 * sprint because it focused solely on adding the right numbers of cells
 * to the population -- the paradigm shifted with the second implementation,
 * to Maps and Lists, and this tester became outdated.
 * 
 * 
 * @author maddiebriere
 *
 */

public class SocietyTester{
	private static CellSociety mySociety;
	
	/*public static void setSociety(CellSociety c){
		mySociety=c;
	}
	
	public static void main(String [] args){
		//tests();
	}
	
	private static void tests(){
		//setSociety(new PopSociety());
		
		while(true){
			mySociety.step();
			checkCellSize();
			checkNumbers();
			sleep();
		}
	}
	
	private static void sleep(){
		Thread t = new Thread();
		try{
			t.sleep(1000);
		}
		catch(Exception e){}
	}
	
	private static void checkCellSize(){
		int expected = mySociety.getX()*mySociety.getY();
		int current = mySociety.getCurrentCells().size();
		checkEquals(current, expected);
	}
	
	private static void checkEquals(int current, int expected){
		if(current==expected){
			System.out.println("Consistent cell updates");
		}
		else if(current>expected){
			System.out.println("Too many cells being added in updates");
		}
		else{
			System.out.println("Not even cells being added in updates");
		}
	}
	
	private static void checkNumbers(){
		ArrayList<Cell> copy = new ArrayList<Cell>(mySociety.getCurrentCells());
		while(!copy.isEmpty()){
			Cell cell = copy.get(0);
			int typeCounter=0;
			for(int i=0; i<copy.size(); i++){
				if(copy.get(i).getClass().equals(cell.getClass())){
					typeCounter++;
					copy.remove(i);
					i--;
				}
			}
			System.out.println("Number of " + getTruncatedName(cell.getClass().getName()) + ": " +
					typeCounter);
		}
		
	}
	
	private static String getTruncatedName(String className){
		return className.substring(className.indexOf('.')+1);
	}*/
}
