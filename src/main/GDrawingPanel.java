package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shapes.*;

import java.util.ArrayList;

public class GDrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private GToolBar gToolBar;
    private int x1, y1, x2, y2;
    private boolean isDragging = false;
    private ArrayList<GShape> shapes = new ArrayList<>();
    private GShape previewShape = null;
    private GShape selectedShape = null;
    private int prevX, prevY; // 이전 마우스 위치 저장

    public GDrawingPanel() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectOrStartDrawing(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                finishDrawingOrMoving();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                moveOrUpdatePreview(e);
            }
        });
    }

    public void setToolBar(GToolBar toolBar) {
        this.gToolBar = toolBar;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (GShape shape : shapes) {
            shape.draw(g);
        }

        if (previewShape != null) {
            previewShape.draw(g);
        }
    }

    private void selectOrStartDrawing(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();

        for (GShape shape : shapes) {
            if (shape.contains(prevX, prevY)) {
                selectedShape = shape;  // 클릭한 도형 선택
                return;
            }
        }

        selectedShape = null;
        isDragging = true;
        x1 = prevX;
        y1 = prevY;
    }

    private void moveOrUpdatePreview(MouseEvent e) {
        if (selectedShape != null) {
            int dx = e.getX() - prevX;
            int dy = e.getY() - prevY;
            selectedShape.moveTo(dx, dy);
            prevX = e.getX();
            prevY = e.getY();
            repaint();
        } else if (isDragging) {
            x2 = e.getX();
            y2 = e.getY();
            previewShape = createShape();
            repaint();
        }
    }

    private void finishDrawingOrMoving() {
        if (selectedShape != null) {
            selectedShape = null;
        } else if (isDragging) {
            GShape newShape = createShape();
            if (newShape != null) {
                shapes.add(newShape);
            }
        }

        previewShape = null;
        isDragging = false;
        repaint();
    }

    private GShape createShape() {
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
		// TODO Auto-generated method stub
		
	}
}
