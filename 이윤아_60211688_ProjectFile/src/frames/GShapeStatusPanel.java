package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shapes.GShape;

public class GShapeStatusPanel extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JLabel widthValueLabel;
    private JLabel heightValueLabel;
    private JPanel colorPanel;
    private JLabel shapeTypeLabel;
    
    public GShapeStatusPanel() {
        super("상태 정보");
        widthValueLabel = new JLabel("0");
        heightValueLabel = new JLabel("0");
        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(40, 20));
        colorPanel.setBackground(this.getBackground());
        shapeTypeLabel = new JLabel("없음");
        setupLayout();  
    }
    
    private void setupLayout() {
    	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(250, 150);
        setResizable(false);
        setAlwaysOnTop(true);
    	
    	
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // 절대 위치 사용
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 도형 타입
        JLabel typeLabel = new JLabel("도형 타입 :");
        typeLabel.setBounds(10, 10, 80, 20);
        shapeTypeLabel.setBounds(100, 10, 100, 20);
        mainPanel.add(typeLabel);
        mainPanel.add(shapeTypeLabel);
        
        // 길이
        JLabel widthLabel = new JLabel("길이 :");
        widthLabel.setBounds(10, 35, 80, 20);
        widthValueLabel.setBounds(100, 35, 100, 20);
        mainPanel.add(widthLabel);
        mainPanel.add(widthValueLabel);
        
        // 높이
        JLabel heightLabel = new JLabel("높이 :");
        heightLabel.setBounds(10, 60, 80, 20);
        heightValueLabel.setBounds(100, 60, 100, 20);
        mainPanel.add(heightLabel);
        mainPanel.add(heightValueLabel);
        
        // 색상
        JLabel colorLabel = new JLabel("색상 :");
        colorLabel.setBounds(10, 85, 80, 20);
        colorPanel.setBounds(100, 85, 40, 20);
        mainPanel.add(colorLabel);
        mainPanel.add(colorPanel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    public void updateShapeInfo(GShape shape) {       
        // 도형 타입 설정
        String shapeType = getShapeTypeName(shape);
        shapeTypeLabel.setText(shapeType);
        
        // 도형의 경계 사각형 가져오기
        Rectangle bounds = shape.getBounds();
        widthValueLabel.setText(String.valueOf(bounds.width));
        heightValueLabel.setText(String.valueOf(bounds.height));
        
        // 색상 설정
        Color shapeColor = shape.getColor();
        if (shapeColor != null) {
            colorPanel.setBackground(shapeColor);
        } else {
            colorPanel.setBackground(this.getBackground());
        }
        
        repaint();
    }
    
    private String getShapeTypeName(GShape shape) {
        String className = shape.getClass().getSimpleName();
        
        // 클래스명에서 도형 타입 추출
        if (className.contains("Rectangle") || className.contains("Rect")) {
            return "사각형";
        } else if (className.contains("Ellipse") || className.contains("Circle")) {
            return "타원";
        } else if (className.contains("Line")) {
            return "선";
        } else if (className.contains("Triangle")) {
            return "삼각형";
        } else if (className.contains("Polygon")) {
            return "다각형";
        } else {
            return className;
        }
    }
}