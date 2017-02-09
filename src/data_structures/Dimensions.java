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
	public boolean equals(Dimensions other){
		return (this != null && other != null) && (other.getX() == getX() && other.getY() == getY());
	}
}
