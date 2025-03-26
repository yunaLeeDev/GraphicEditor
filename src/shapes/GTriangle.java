package shapes;

import java.awt.Graphics;

public class GTriangle extends GShape {

	public GTriangle(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
	}
	
    @Override
    public void draw(Graphics g) {
        int[] xPoints = {x1, x2, (x1 + x2) / 2};
        int[] yPoints = {y2, y2, y1};
        g.drawPolygon(xPoints, yPoints, 3);
    }
    
}
