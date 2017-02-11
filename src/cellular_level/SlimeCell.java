package cellular_level;

import java.util.List;



import data_structures.CellData;
import javafx.scene.paint.Color;

public class SlimeCell extends Cell {
	private static final Color SLIME_COLOR = Color.LIMEGREEN;
	private static final int FOLLOW_THRESH = 6;
	private static final int RELEASE_THRESH = 10;
	
	private int followThresh;
	private int releaseThresh;

	public SlimeCell(){
		this(0,0);
	}
	
	public SlimeCell(int row, int col){
		this(0, 0, SLIME_COLOR);
	}
	
	public SlimeCell(int row, int col, Color c){
		this(row, col, c, FOLLOW_THRESH, RELEASE_THRESH);
	}
	
	public SlimeCell(int row, int col, Color c, int f, int r){
		super(row,col,c);
		followThresh=f;
		releaseThresh=r;
	}
	
	
	@Override
	public Cell createCopy() {
		SlimeCell copy = new SlimeCell();
		copy.basicCopy(this);
		copy.setFollowThresh(followThresh);
		copy.setReleaseThresh(releaseThresh);
		return copy;
	}

	@Override
	public List<Cell> update(CellData data) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFollowThresh() {
		return followThresh;
	}

	public void setFollowThresh(int followThresh) {
		this.followThresh = followThresh;
	}

	public int getReleaseThresh() {
		return releaseThresh;
	}

	public void setReleaseThresh(int releaseThresh) {
		this.releaseThresh = releaseThresh;
	}
}
