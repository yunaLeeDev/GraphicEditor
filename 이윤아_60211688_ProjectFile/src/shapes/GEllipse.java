package shapes;

import java.awt.geom.Ellipse2D;

public class GEllipse extends GShape {
    private Ellipse2D ellipse;

    public GEllipse() {
        super(new Ellipse2D.Float(0, 0, 0, 0));
        this.ellipse = (Ellipse2D) this.getShape();
    }

    @Override
    public void setPoint(int x, int y) {
        this.ellipse.setFrame(x, y, 0, 0);
    }

    @Override
    public void dragPoint(int x, int y) {
        double ox = ellipse.getX();
        double oy = ellipse.getY();
        double w = x - ox;
        double h = y - oy;

        this.ellipse.setFrame(ox, oy, w, h);
    }

    @Override
    public void addPoint(int x, int y) {
        // Not used for oval
    }
    
    @Override
    public GShape clone() {
        GEllipse cloned = new GEllipse();
        Ellipse2D original = (Ellipse2D) this.getShape();
        Ellipse2D newEllipse = new Ellipse2D.Double(
            original.getX(), original.getY(),
            original.getWidth(), original.getHeight()
        );
        cloned.setShape(newEllipse);
        return cloned;
    }

    @Override
    public void moveBy(int dx, int dy) {
        Ellipse2D ellipse = (Ellipse2D) this.getShape();
        double newX = ellipse.getX() + dx;
        double newY = ellipse.getY() + dy;
        ellipse.setFrame(newX, newY, ellipse.getWidth(), ellipse.getHeight());
    }

}
