package shapes;

import java.awt.Graphics;

public abstract class GShape {
    protected int x1, y1, x2, y2;

    public GShape(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public abstract void draw(Graphics g);

    public void updateSize(int newX2, int newY2) {
        this.x2 = newX2;
        this.y2 = newY2;
    }

    public void moveTo(int dx, int dy) {
        this.x1 += dx;
        this.y1 += dy;
        this.x2 += dx;
        this.y2 += dy;
    }

    public boolean contains(int x, int y) {
        return x >= Math.min(x1, x2) && x <= Math.max(x1, x2) &&
               y >= Math.min(y1, y2) && y <= Math.max(y1, y2);
    }
}
