package main;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class GToolBar extends JToolBar {

    private static final long serialVersionUID = 1L;
    private JRadioButton rectangleButton;
    private JRadioButton triangleButton;
    private JRadioButton ovalButton;
    private JRadioButton polygonButton;
    private JRadioButton textBoxButton;
    private ButtonGroup buttonGroup;

    private String selectedShape;

    public GToolBar() {
        buttonGroup = new ButtonGroup();
        selectedShape = "Rectangle"; // 기본값은 Rectangle

        rectangleButton = new JRadioButton("Rectangle");
        triangleButton = new JRadioButton("Triangle");
        ovalButton = new JRadioButton("Oval");
        polygonButton = new JRadioButton("Polygon");
        textBoxButton = new JRadioButton("TextBox");

        buttonGroup.add(rectangleButton);
        buttonGroup.add(triangleButton);
        buttonGroup.add(ovalButton);
        buttonGroup.add(polygonButton);
        buttonGroup.add(textBoxButton);

        this.add(rectangleButton);
        this.add(triangleButton);
        this.add(ovalButton);
        this.add(polygonButton);
        this.add(textBoxButton);

        rectangleButton.addActionListener(e -> selectedShape = "Rectangle");
        triangleButton.addActionListener(e -> selectedShape = "Triangle");
        ovalButton.addActionListener(e -> selectedShape = "Oval");
        polygonButton.addActionListener(e -> selectedShape = "Polygon");
        textBoxButton.addActionListener(e -> selectedShape = "TextBox");
    }

    public String getSelectedShape() {
        return selectedShape;
    }

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
