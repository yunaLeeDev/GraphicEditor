package main;

import java.awt.Graphics2D;

public class GTransformer {
	private GRectangle rectangle;
	public void start(Graphics2D graphics, int x, int y) {
		rectangle = new GRectangle();
		rectangle.setPoint1(x, y);
		rectangle.setPoint2(x, y);
	}
	
	public void drag(Graphics2D graphics, int x, int y) {
		rectangle.draw(graphics);
		rectangle.setPoint2(x, y);
		rectangle.draw(graphics);
	}	
	
	public GRectangle finish(Graphics2D graphics, int x, int y) {
		return rectangle;
	}
}
