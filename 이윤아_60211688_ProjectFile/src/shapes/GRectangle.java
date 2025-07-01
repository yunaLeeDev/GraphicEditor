package shapes;

import java.awt.geom.Rectangle2D;

public class GRectangle extends GShape{
	private Rectangle2D rectangle;

	
	public GRectangle() {
		//부모에서 만들어서 저장하고 여기다가도 저장함
		super(new Rectangle2D.Float(0,0,0,0));
		this.rectangle = (Rectangle2D) this.getShape();
	}
	
	public void setPoint(int x, int y) {
		this.rectangle.setFrame(x,y,0,0);
	}
	
	public void dragPoint(int x, int y) {
		double ox = rectangle.getX();
		double oy = rectangle.getY();
		double w = x-ox;
		double h = y-oy;

		this.rectangle.setFrame(ox, oy,w,h);
	}
	
	public void addPoint(int x, int y) {
	}
	
	@Override
    public GShape clone() {
        GRectangle cloned = new GRectangle();
        Rectangle2D newRect = new Rectangle2D.Double(
            this.rectangle.getX(),
            this.rectangle.getY(),
            this.rectangle.getWidth(),
            this.rectangle.getHeight()
        );
        cloned.setShape(newRect); 
        cloned.rectangle = newRect;
        return cloned;
    }

    @Override
    public void moveBy(int dx, int dy) {
        double newX = rectangle.getX() + dx;
        double newY = rectangle.getY() + dy;
        rectangle.setFrame(newX, newY, rectangle.getWidth(), rectangle.getHeight());
    }
	
}
