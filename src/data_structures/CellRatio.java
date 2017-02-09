//author Talha Koc
//Just an object that holds an integer with value greater than zero and less than or equal to 1

package data_structures;


public class CellRatio {
	private double r;
	public CellRatio(double r){
		if(r>0 && r<=1){
			this.r = r;
		}else{
			//TODO: error handling,
		}
	}
	public double getRatio(){
		return r;
	}
}
