//author Talha Koc

package data_structures;

public class Dimensions {
	private int x;
	private int y;
	public Dimensions(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void setX(int value){
		this.x = value;
	}
	public void setY(int value){
		this.y = value;
	}
	
	
	
	/**
	 * Methods used in checking boundaries in CellSociety
	 * 
	 * THE ASSUMPTION MADE HERE IS THAT THE GRID IS FILLED IN WITH A BIAS TOWARDS THE 
	 * + axes (EVEN NUMBERS -> ONE EXTRA ON TOP OR RIGHT)
	 */
	public int getPosYBound(){
		return (int)(getY()/2);
	}
	
	public int getNegYBound(){
		if(getY()%2==0)
			return -(getPosYBound()-1);
		else
			return -(getPosYBound());
	}
	
	public int getPosXBound(){
		return (int)(getX()/2);
	}
	
	public int getNegXBound(){
		if(getX()%2==0)
			return -(getPosXBound()-1);
		else
			return -(getPosXBound());
	
	}
}
