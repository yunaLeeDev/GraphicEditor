package shapes;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class GTriangle extends GShape {
    private Path2D triangle;
    private Rectangle2D bounds;

    public GTriangle() {
        super(new Path2D.Double());
        this.triangle = (Path2D) this.getShape();
        this.bounds = new Rectangle2D.Double(0, 0, 0, 0);
    }

    @Override
    public void setPoint(int x, int y) {
        this.bounds.setFrame(x, y, 0, 0);
    }

    @Override
    public void dragPoint(int x, int y) {
        double ox = bounds.getX();
        double oy = bounds.getY();
        double w = x - ox;
        double h = y - oy;

        bounds.setFrame(ox, oy, w, h);

        double left = bounds.getX();
        double top = bounds.getY();
        double width = bounds.getWidth();
        double height = bounds.getHeight();

        double x1 = left + width / 2;
        double y1 = top;
        double x2 = left;
        double y2 = top + height;
        double x3 = left + width;
        double y3 = top + height;

        triangle.reset();
        triangle.moveTo(x1, y1); // 맨 위 가운데 점
        triangle.lineTo(x2, y2); // 왼쪽 점
        triangle.lineTo(x3, y3); // 우측 점
        triangle.closePath();
    }

    @Override
    public void addPoint(int x, int y) {
    }
    
    @Override
    public GShape clone() {
        GTriangle cloned = new GTriangle();
        cloned.bounds = (Rectangle2D) this.bounds.clone();
        cloned.dragPoint(
            (int)(cloned.bounds.getX() + cloned.bounds.getWidth()),
            (int)(cloned.bounds.getY() + cloned.bounds.getHeight())
        );
        return cloned;
    }

    @Override
    public void moveBy(int dx, int dy) {
        bounds.setFrame(
            bounds.getX() + dx,
            bounds.getY() + dy,
            bounds.getWidth(),
            bounds.getHeight()
        );
        dragPoint(
            (int)(bounds.getX() + bounds.getWidth()),
            (int)(bounds.getY() + bounds.getHeight())
        );
    }

}
