package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shapes.*;

import java.util.ArrayList;

public class GDrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private GToolBar gToolBar;
    private ArrayList<GShape> shapes = new ArrayList<>();
    private GShape selectedShape = null;
    private int prevX, prevY; // 이전 마우스 좌표 저장

    private class MouseHandler implements MouseListener, MouseMotionListener {
        private int startX, startY;
        private GShape previewShape = null;

        @Override
        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
            prevX = startX;
            prevY = startY;

            selectedShape = findShape(startX, startY);
            if (selectedShape == null) {
                previewShape = createShape(startX, startY, startX, startY);
                draw(previewShape);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int currX = e.getX();
            int currY = e.getY();

            // select된 도형이 있는 경우 (위치 변경)
            if (selectedShape != null) {
                draw(selectedShape); // 기존 위치 지우기 (XOR)
                selectedShape.moveTo(currX - prevX, currY - prevY);
                prevX = currX;
                prevY = currY;
                draw(selectedShape); // 새로운 위치에 그림
            } else if (previewShape != null) {
                // 새로운 도형 그리기 (미리보기)
                draw(previewShape);
                previewShape.updateSize(currX, currY);
                draw(previewShape); 
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (previewShape != null) {
                // 최종 도형 추가
                shapes.add(previewShape);
                previewShape = null;
            }
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}        
		@Override
		public void mouseMoved(MouseEvent e) {}
		
		// 해당 위치에 도형이 존재하는지 확인
		private GShape findShape(int x, int y) {
            for (GShape shape : shapes) {
                if (shape.contains(x, y)) {
                    return shape;
                }
            }
            return null;
        }

    }

    public GDrawingPanel() {
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    public void setToolBar(GToolBar toolBar) {
        this.gToolBar = toolBar;
    }

    private void draw(GShape shape) {
        Graphics graphics = this.getGraphics();
        graphics.setXORMode(getBackground());
        shape.draw(graphics);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GShape shape : shapes) {
            shape.draw(g);
        }
    }

    private GShape createShape(int x1, int y1, int x2, int y2) {
        if (gToolBar == null) return null;

        String selectedShape = gToolBar.getSelectedShape();
        if (selectedShape == null) return null;

        switch (selectedShape) {
            case "Rectangle": return new GRectangle(x1, y1, x2, y2);
            case "Oval": return new GOval(x1, y1, x2, y2);
            case "Triangle": return new GTriangle(x1, y1, x2, y2);
            case "Polygon": return new GPolygon(x1, y1, x2, y2);
            case "TextBox": return new GTextBox(x1, y1, x2, y2);
            default: return null;
        }
    }

    public void initialize() {
    }
}
