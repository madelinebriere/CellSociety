package util;

public class Location {
	private int myRow;
	private int myCol;
	
	public Location(){
		myRow=0;
		myCol=0;
	}
	
	public Location(int row, int col){
		myRow=row;
		myCol=col;
	}

	public int getMyRow() {
		return myRow;
	}

	public void setMyRow(int myRow) {
		this.myRow = myRow;
	}

	public int getMyCol() {
		return myCol;
	}

	public void setMyCol(int myCol) {
		this.myCol = myCol;
	}
	
}
