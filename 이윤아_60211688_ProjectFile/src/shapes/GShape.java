package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import global.GConstants.EAnchor;


public abstract class GShape implements Serializable{
	private static final long serialVersionUID = 1L;
	// 메소드 내부에 숫자 집어넣지 말것
	private final static int ANCHOR_W = 10;
	private final static int ANCHOR_H = 10;
	
    public enum EPoints {
        e2P,
        eNP
    }
    
    private Shape shape;
    private Ellipse2D anchors[];
    private boolean bSelected;
    private boolean bGrouped;
    private Color color;
    private EAnchor eSelectedAnchor;
	private AffineTransform affineTransform;
    
	public AffineTransform getAffineTransform () {
		return this.affineTransform;
	}
	
    public GShape(Shape shape) {
        this.shape = shape;      
        // (무브는 동그라미 안만들어도 됌)
        this.anchors = new Ellipse2D[EAnchor.values().length-1];
        for(int i=0;i<this.anchors.length;i++) {
        	// 동그라미 9개를 만듦
        	this.anchors[i] = new Ellipse2D.Double();
        }
		this.affineTransform = new AffineTransform();
        this.bSelected = false;
        this.eSelectedAnchor = null;
        this.bGrouped = false;
        this.color = null;
    }

    // getter and Setter
    public Shape getShape() {
        return this.shape;
    }
    
    public Color getColor() {
    	return this.color;
    }

    protected void setShape(Shape shape) {
        this.shape = shape;
    }
    
	public Rectangle getBounds() {
		return this.shape.getBounds();
	}
    
    public EAnchor getESelectedAnchor() {
    	return this.eSelectedAnchor;
    }
    
    public void setESelectedAnchor(EAnchor eSelectedAnchor) {
    	this.eSelectedAnchor = eSelectedAnchor;
    }
    public boolean isSelected() {
    	return this.bSelected;
    }
    public void setSelected(boolean bSelected) {
    	this.bSelected = bSelected;
    }
    
    public void setGrouped(boolean bGrouped) {
    	this.bGrouped = bGrouped;
    }

    public void setColor(Color color) {
    	this.color = color;
    }
    public boolean isGrouped() {
    	return this.bGrouped;
    }
    public void draw(Graphics2D graphics2D) {
        Shape transformedShape = this.affineTransform.createTransformedShape(shape);

        if (this.color != null) {
            Color penColor = graphics2D.getColor();
            graphics2D.setColor(this.color);
            graphics2D.fill(transformedShape);
            graphics2D.setColor(penColor);
        }

        graphics2D.draw(transformedShape);

        if (bSelected) {
            this.setAnchors();

            for (int i = 0; i < this.anchors.length; i++) {
                // 앵커 원의 중심 좌표
                double centerX = anchors[i].getCenterX();
                double centerY = anchors[i].getCenterY();

                // 중심 좌표만 affineTransform으로 변환
                double[] pt = new double[] { centerX, centerY };
                this.affineTransform.transform(pt, 0, pt, 0, 1);

                double w = ANCHOR_W;
                double h = ANCHOR_H;

                Color penColor = graphics2D.getColor();
                graphics2D.setColor(graphics2D.getBackground());
                graphics2D.fillOval((int)(pt[0] - w / 2), (int)(pt[1] - h / 2), (int)w, (int)h);

                graphics2D.setColor(penColor);
                graphics2D.drawOval((int)(pt[0] - w / 2), (int)(pt[1] - h / 2), (int)w, (int)h);
            }
        }
    }


    private void setAnchors() {
		// 먼저 바운딩 rectangle을 그려야 함
    	Rectangle bounds = this.shape.getBounds();
    	int bx = bounds.x;
    	int by = bounds.y;
    	int bw = bounds.width;
    	int bh = bounds.height;
    	int cx=0;
    	int cy=0;
    	for(int i=0;i<this.anchors.length;i++) {
    		switch(EAnchor.values()[i]) {
    		case eSS: cx=bx+bw/2; cy=by+bh; break;
    		case eSE: cx=bx+bw;   cy=by+bh; break;
    		case eSW: cx=bx; 	  cy=by+bh; break;
    		case eNN: cx=bx+bw/2; cy=by; break;
    		case eNE: cx=bx+bw;   cy=by; break;
    		case eNW: cx=bx; 	  cy=by; break;
    		case eEE: cx=bx+bw;   cy=by+bh/2; break;
    		case eWW: cx=bx; 	  cy=by+bh/2; break;
    		case eRR: cx=bx+bw/2; cy=by-30; break;
    		default: break;
    		}
    		anchors[i].setFrame(cx-ANCHOR_W/2, cy-ANCHOR_H/2, ANCHOR_W, ANCHOR_H);
    	}
    	
	}

	public boolean contains(int x, int y) {
		if(bSelected) {
			for(int i=0;i<this.anchors.length;i++) {
				// 바뀐 앵커에 대해서 contain하냐고 물어봐야함
				Shape transfromedAnchor = this.affineTransform.createTransformedShape(anchors[i]);
        		if(transfromedAnchor.contains(x, y)) {
        			// 어떤 앵커가 선택되었는지 저장해두는 것
        			this.eSelectedAnchor = EAnchor.values()[i];
        			return true;
        		}
        	}
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(shape);
		if(transformedShape.contains(x, y)) {
			this.eSelectedAnchor = EAnchor.eMM;
			return true;
		}
        return false;
    }
	
	
	public boolean contains(GShape shape) {
		return this.shape.contains(shape.getShape().getBounds());
	}
	
    public abstract void setPoint(int x, int y);
    public abstract void addPoint(int x, int y);
    public abstract void dragPoint(int x, int y);
    public abstract GShape clone();
    public abstract void moveBy(int dx, int dy);

	public Shape getTransformedShape() {
		return this.affineTransform.createTransformedShape(this.shape);
	}

	public void setAffineTransform(AffineTransform newTransform) {
		this.affineTransform = newTransform;
		
	}

}

