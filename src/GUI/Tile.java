package GUI;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

final class Tile extends StackPane{
	private Rectangle rect;
	public Tile(int x, int y, int size){
		rect = new Rectangle(size - 1, size - 1);
		rect.setStroke(Color.LIGHTGRAY);
		this.getChildren().add(rect);
		setTranslateX(x * size);
		setTranslateY(y * size);
	}
	public void setColor(Color color){
		rect.setFill(color);
	} 
	
}