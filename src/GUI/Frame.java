//author Talha Koc

package GUI;

public class Frame {
	private int x;
	private int y;
	private int width;
	private int height;

	public Frame(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return ("x: " + x + "\ty: " + y + "\twidth: " + width + "\theight: " + height);
	}
}
