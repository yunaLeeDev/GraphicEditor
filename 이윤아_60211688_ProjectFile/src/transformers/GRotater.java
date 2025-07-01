package transformers;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import shapes.GShape;

public class GRotater extends GTransformer {
    private GShape shape;
    private Vector<GShape> groupShapes;
    private int px, py;

    public GRotater(GShape shape) {
        super(shape);
        this.shape = shape;
        this.groupShapes = null;
    }

    public GRotater(Vector<GShape> groupShapes) {
        super(null);
        this.shape = null;
        this.groupShapes = groupShapes;
    }

    @Override
    public void start(Graphics2D g2d, int x, int y) {
        this.px = x;
        this.py = y;
    }

    @Override
    public void drag(Graphics2D g2d, int x, int y) {
        double rotation;

        if (shape != null) {
            Rectangle2D bounds = shape.getTransformedShape().getBounds2D();
            double cx = bounds.getCenterX();
            double cy = bounds.getCenterY();

            double angle1 = Math.atan2(py - cy, px - cx);
            double angle2 = Math.atan2(y - cy, x - cx);
            rotation = angle2 - angle1;

            applyRotation(shape, rotation, cx, cy);
        } else if (groupShapes != null && !groupShapes.isEmpty()) {
            // 공통 회전각 계산을 위해 첫 도형 중심 사용
            GShape ref = groupShapes.get(0);
            Rectangle2D refBounds = ref.getTransformedShape().getBounds2D();
            double refCx = refBounds.getCenterX();
            double refCy = refBounds.getCenterY();

            double angle1 = Math.atan2(py - refCy, px - refCx);
            double angle2 = Math.atan2(y - refCy, x - refCx);
            rotation = angle2 - angle1;

            for (GShape s : groupShapes) {
                Rectangle2D bounds = s.getTransformedShape().getBounds2D();
                double cx = bounds.getCenterX();
                double cy = bounds.getCenterY();
                applyRotation(s, rotation, cx, cy);
            }
        }

        this.px = x;
        this.py = y;
    }

    private void applyRotation(GShape s, double rotation, double cx, double cy) {
        AffineTransform rotateTransform = new AffineTransform();
        rotateTransform.translate(cx, cy);
        rotateTransform.rotate(rotation);
        rotateTransform.translate(-cx, -cy);
        s.getAffineTransform().preConcatenate(rotateTransform);
    }

    @Override
    public void finish(Graphics2D g2d, int x, int y) {
        // Optional: finalize
    }

    @Override
    public void addPoint(Graphics2D g2d, int x, int y) {
        // 사용 안 함
    }
}
