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
    private JTextField textField; // 텍스트 입력을 위한 텍스트 필드

    public GDrawingPanel() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDrawing(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                finishDrawing(); // MouseEvent를 받지 않고 직접 텍스트 입력 후 도형 추가
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updatePreview(e);
            }
        });
    }

    public void setToolBar(GToolBar toolBar) {
        this.gToolBar = toolBar;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 기존 도형 유지
        for (GShape shape : shapes) {
            shape.draw(g);
        }

        // 미리보기 도형
        if (previewShape != null) {
            previewShape.draw(g);
        }
    }

    private void startDrawing(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
        isDragging = true;

        if (gToolBar != null && gToolBar.getSelectedShape().equals("TextBox")) {
            showTextField(e);
        }
    }

    private void updatePreview(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        previewShape = createShape();
        repaint(); // 실시간 미리보기
    }

    private void finishDrawing() {
        if (gToolBar != null && gToolBar.getSelectedShape().equals("TextBox") && textField != null) {
            // 텍스트 박스를 다 그린 후, 텍스트를 텍스트 박스 객체에 저장
            GTextBox newTextBox = new GTextBox(x1, y1, x2, y2);
            newTextBox.setText(textField.getText());
            shapes.add(newTextBox);
            textField = null; // 텍스트 필드 초기화
        } else {
            GShape newShape = createShape();
            if (newShape != null) {
                shapes.add(newShape);
            }
        }

        previewShape = null; // 미리보기 삭제
        repaint();
    }

    private void showTextField(MouseEvent e) {
        if (textField != null) {
            remove(textField); // 이전 텍스트 필드 삭제
        }

        textField = new JTextField();
        textField.setBounds(e.getX(), e.getY(), 150, 30);
        textField.setBackground(Color.WHITE);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishDrawing();
            }
        });

        add(textField);
        textField.requestFocusInWindow();
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
