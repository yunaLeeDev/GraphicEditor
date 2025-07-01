package shapes;

import java.awt.Shape;
import java.awt.geom.Line2D;

public class GLine extends GShape {
    private static final long serialVersionUID = 1L;

    public GLine() {
        // 처음엔 빈 선으로 초기화
        super(new Line2D.Double(0, 0, 0, 0));
    }

    @Override
    public void setPoint(int x, int y) {
        Line2D line = (Line2D) this.getShape();
        line.setLine(x, y, x, y);  // 시작점과 끝점을 동일하게 설정
    }

    @Override
    public void dragPoint(int x, int y) {
        // 실시간 끝점 업데이트
        Line2D line = (Line2D) this.getShape();
        line.setLine(line.getX1(), line.getY1(), x, y);
    }

    @Override
    public void addPoint(int x, int y) {
    }

    @Override
    public GShape clone() {
        GLine cloned = new GLine();
        Line2D original = (Line2D) this.getShape();
        Line2D newLine = new Line2D.Double(
            original.getX1(), original.getY1(),
            original.getX2(), original.getY2()
        );
        // shape 필드에 접근할 수 없으므로 setShape()을 사용
        cloned.setShape(newLine);
        return cloned;
    }

    @Override
    public void moveBy(int dx, int dy) {
        Line2D line = (Line2D) this.getShape();
        double x1 = line.getX1() + dx;
        double y1 = line.getY1() + dy;
        double x2 = line.getX2() + dx;
        double y2 = line.getY2() + dy;
        line.setLine(x1, y1, x2, y2);
    }

}
