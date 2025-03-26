package shapes;

import java.awt.Graphics;

public class GOval extends GShape {

	public GOval(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void draw(Graphics g) {
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        g.drawOval(startX, startY, width, height);
    }

}
