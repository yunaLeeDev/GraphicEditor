package shapes;

import java.awt.Graphics;

public class GPolygon extends GShape {

	public GPolygon(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void draw(Graphics g) {
        int[] xPoints = {x1, x2, (x2 + 20), (x1 - 20), x1};
        int[] yPoints = {y1, y1, y2, y2, y1};
        g.drawPolygon(xPoints, yPoints, 5);
    }

}
