package transformers;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

import shapes.GShape;

public class GMover extends GTransformer {

    private GShape shape;
    private Vector<GShape> groupShapes;
    private int px, py;

    public GMover(GShape shape) {
        super(shape);
        this.shape = shape;
        this.groupShapes = null;
    }

    public GMover(Vector<GShape> groupShapes) {
        super(null);
        this.groupShapes = groupShapes;
        this.shape = null;
    }

    @Override
    public void start(Graphics2D g2d, int x, int y) {
        this.px = x;
        this.py = y;
    }

    @Override
    public void drag(Graphics2D g2d, int x, int y) {
        int dx = x - px;
        int dy = y - py;

        if (groupShapes != null && !groupShapes.isEmpty()) {
            for (GShape s : groupShapes) {
                moveShape(s, dx, dy);
            }
        } else if (shape != null) {
            moveShape(shape, dx, dy);
        }

        px = x;
        py = y;
    }

    private void moveShape(GShape s, int dx, int dy) {
        try {
            AffineTransform inverse = s.getAffineTransform().createInverse();
            Point2D.Double delta = new Point2D.Double(dx, dy);
            inverse.deltaTransform(delta, delta);
            s.getAffineTransform().translate(delta.x, delta.y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish(Graphics2D g2d, int x, int y) {
    }

    @Override
    public void addPoint(Graphics2D g2d, int x, int y) {
        if (shape != null) {
            shape.addPoint(x, y);
        }
    }
}
