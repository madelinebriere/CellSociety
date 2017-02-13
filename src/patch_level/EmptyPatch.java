package patch_level;

import javafx.scene.paint.Color;

/**
 * Empty Patch, an extension of Patch, has no functionality other than
 * displaying a color and holding a cell.
 * 
 * @author maddiebriere
 *
 */

public class EmptyPatch extends Patch {

	public EmptyPatch() {
		super();
	}

	public EmptyPatch(int col, int row) {
		super(col, row);
	}

	public EmptyPatch(int col, int row, Color color) {
		super(col, row, color);
	}

	@Override
	public void update() {
		return; // Empty patch does nothing to update
	}

	@Override
	public Patch createCopy() {
		EmptyPatch copy = new EmptyPatch();
		copy.basicCopy(this);
		return copy;
	}
}
