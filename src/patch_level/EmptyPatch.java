package patch_level;

import cellular_level.Cell;
import javafx.scene.paint.Color;

public class EmptyPatch extends Patch{

	public EmptyPatch(){
		super();
	}
	public EmptyPatch(int col, int row){
		super(col,row);
	}
	public EmptyPatch(int col, int row, Color color){
		super(col, row, color);
	}
	@Override
	public void update() {
		return; //Empty patch does nothing to update	
	}
	@Override
	public Patch createCopy() {
		EmptyPatch copy = new EmptyPatch();
		copy.basicCopy(this);
		return copy;
	}
}
