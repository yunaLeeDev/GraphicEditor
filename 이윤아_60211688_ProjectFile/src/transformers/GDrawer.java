package transformers;

import java.awt.Graphics2D;
import shapes.GShape;

public class GDrawer extends GTransformer {
	
	private GShape shape;
	
	//드로윙패널에서 만든 rectangle을 여기다 저장해둠
	public GDrawer(GShape shape) {
		super(shape);		
		this.shape = shape;			
	}

	public void start(Graphics2D g2d, int x, int y) {
		shape.setPoint(x, y);
	}

	public void drag(Graphics2D g2d, int x, int y) {
		shape.dragPoint(x, y);
	}

	public void finish(Graphics2D g2d, int x, int y) {
	}
	//폴리곤일때만 필요함
	public void addPoint(Graphics2D g2d, int x, int y) {
	    shape.addPoint(x, y);
	}
}
