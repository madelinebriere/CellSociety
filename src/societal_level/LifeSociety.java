package societal_level;

import java.util.ArrayList;
import java.util.Random;

import cellular_level.*;
import javafx.scene.paint.Color;

public class LifeSociety extends CellSociety {

	/**
	 * Need to instantiate
	 */
	public LifeSociety(){
		setSize(10);
		setEmptyColor(Color.BLUE); //catch any errors
		Random rnd = new Random();
		ArrayList<Cell>makeCells = new ArrayList<Cell>();
		
		//RANDOM
		/**for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
					makeCells.add( rnd.nextBoolean()? new LiveCell(i,j) : new DeadCell(i,j));
				
			}
		}*/
		
		//BLINKER
		/**for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				if(!((i==4) && (j==3 || j==4 || j==5)))
					makeCells.add(new DeadCell(i,j));
				else
					makeCells.add(new LiveCell(i,j));
				
			}
		}*/
		
		//BEACON
		/**for(int i=0; i<getSize(); i++){
			for(int j=0; j<getSize(); j++){
				if(!(( (i==4 || i==5)  && ( j==4 || j==5 ) )|| 
					 ( (i==6 || i==7) && (j==6 || j==7) )))
					makeCells.add(new DeadCell(i,j));
				else
					makeCells.add(new LiveCell(i,j));
				
			}
		}*/
		
		setCurrentCells(makeCells);
	}
	

}
