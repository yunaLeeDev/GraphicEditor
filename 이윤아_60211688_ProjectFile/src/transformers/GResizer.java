package transformers;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Vector;

import global.GConstants.EAnchor;
import shapes.GShape;

public class GResizer extends GTransformer {

    private GShape shape;
    private Vector<GShape> groupShapes;

    private EAnchor eResizerAnchor;
    private int cx, cy;
    private int px, py;

    public GResizer(GShape shape) {
        super(shape);
        this.shape = shape;
        this.groupShapes = null;
        this.eResizerAnchor = null;
    }

    public GResizer(Vector<GShape> groupShapes, GShape selectedShape) {
        super(null);
        this.groupShapes = groupShapes;
        this.shape = selectedShape; 
        this.eResizerAnchor = null;
    }

    public void start(Graphics2D g2d, int x, int y) {
        this.px = x;
        this.py = y;

        Rectangle r;

        if (shape != null) {
            r = this.shape.getBounds();
            EAnchor eSelectedAnchor = this.shape.getESelectedAnchor();
            this.eResizerAnchor = getOppositeAnchor(eSelectedAnchor);
            setResizeCenter(r, eSelectedAnchor);
        } else if (groupShapes != null && !groupShapes.isEmpty()) {
            Rectangle gb = getGroupBounds();

            if (this.shape != null) {
                EAnchor anchor = this.shape.getESelectedAnchor();
                if (anchor != null) {
                    this.eResizerAnchor = getOppositeAnchor(anchor);
                    setResizeCenter(gb, anchor);
                }
            }
        }

    }

    public void drag(Graphics2D g2d, int x, int y) {
        double dx = 0, dy = 0;
        switch (eResizerAnchor) {
            case eNW: dx = (x - px);  dy = (y - py);   break;
            case eWW: dx = (x - px);  dy = 0;          break;
            case eSW: dx = (x - px);  dy = -(y - py);  break;
            case eSS: dx = 0;         dy = -(y - py);  break;
            case eSE: dx = -(x - px); dy = -(y - py);  break;
            case eEE: dx = -(x - px); dy = 0;          break;
            case eNE: dx = -(x - px); dy = (y - py);   break;
            case eNN: dx = 0;         dy = (y - py);   break;
            default: break;
        }

        if (shape != null) {
            resizeShape(shape, dx, dy);
        } 
        if (groupShapes != null) {
            for (GShape s : groupShapes) {
                resizeShape(s, dx, dy);
            }
        }

        this.px = x;
        this.py = y;
    }

    private void resizeShape(GShape s, double dx, double dy) {
        Shape transformedShape = s.getTransformedShape();
        double w1 = transformedShape.getBounds().getWidth();
        double h1 = transformedShape.getBounds().getHeight();
        double w2 = w1 + dx;
        double h2 = h1 + dy;

        if (w2 < 1) w2 = 1;
        if (h2 < 1) h2 = 1;

        double xScale = w2 / w1;
        double yScale = h2 / h1;

        s.getAffineTransform().translate(cx, cy);
        s.getAffineTransform().scale(xScale, yScale);
        s.getAffineTransform().translate(-cx, -cy);
    }

    private void setResizeCenter(Rectangle r, EAnchor selectedAnchor) {
        switch (selectedAnchor) {
            case eEE:  cx = r.x;             cy = r.y + r.height/2; break;
            case eWW:  cx = r.x + r.width;   cy = r.y + r.height/2; break;
            case eNN:  cx = r.x + r.width/2; cy = r.y + r.height;   break;
            case eSS:  cx = r.x + r.width/2; cy = r.y;              break;
            case eNE:  cx = r.x;             cy = r.y + r.height;   break;
            case eNW:  cx = r.x + r.width;   cy = r.y + r.height;   break;
            case eSE:  cx = r.x;             cy = r.y;              break;
            case eSW:  cx = r.x + r.width;   cy = r.y;              break;
            default: break;
        }
    }

    private EAnchor getOppositeAnchor(EAnchor anchor) {
        switch (anchor) {
            case eEE: return EAnchor.eWW;
            case eWW: return EAnchor.eEE;
            case eNN: return EAnchor.eSS;
            case eSS: return EAnchor.eNN;
            case eNE: return EAnchor.eSW;
            case eNW: return EAnchor.eSE;
            case eSE: return EAnchor.eNW;
            case eSW: return EAnchor.eNE;
            default: return null;
        }
    }

    private Rectangle getGroupBounds() {
        Rectangle bounds = null;
        for (GShape s : groupShapes) {
            if (bounds == null) {
                bounds = s.getBounds();
            } else {
                bounds = bounds.union(s.getBounds());
            }
        }
        return bounds;
    }

    public void finish(Graphics2D g2d, int x, int y) {
    }

    public void addPoint(Graphics2D g2d, int x, int y) {
    }
}
