package shapes;

import java.awt.Polygon;

public class GPolygon extends GShape{
	private Polygon polygon;
	
	public GPolygon() {
		super(new Polygon());
		this.polygon = (Polygon) this.getShape();
	}
	
	public void setPoint(int x, int y) {
		// 폴리곤은 시작할 때 2개의 점을 추가함
		// 폴리곤은 점의 배열을 저장하고 있기에, 2개의 점을 추가한 다음에 마지막 점을 움직이는 방식으로 만듦
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
	}
	
	public void dragPoint(int x, int y) {
		// 마지막 점을 가지고 와서 drag함
		this.polygon.xpoints[this.polygon.npoints-1] = x;
		this.polygon.ypoints[this.polygon.npoints-1] = y; 
	}
	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);
	}
	
	@Override
	public GShape clone() {
	    GPolygon cloned = new GPolygon();
	    Polygon newPolygon = new Polygon();

	    for (int i = 0; i < this.polygon.npoints; i++) {
	        newPolygon.addPoint(this.polygon.xpoints[i], this.polygon.ypoints[i]);
	    }

	    cloned.setShape(newPolygon);
	    cloned.polygon = newPolygon;

	    return cloned;
	}

	@Override
	public void moveBy(int dx, int dy) {
	    for (int i = 0; i < this.polygon.npoints; i++) {
	        this.polygon.xpoints[i] += dx;
	        this.polygon.ypoints[i] += dy;
	    }
	    // Polygon의 내부 bounding box도 갱신 필요
	    this.polygon.invalidate();
	}

}
