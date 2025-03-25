package main;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GToolBar extends JToolBar {

    private static final long serialVersionUID = 1L;

    private JRadioButton rectangleButton;
    private JRadioButton triangleButton;
    private JRadioButton ovalButton;
    private JRadioButton polygonButton;
    private JRadioButton textBoxButton;
    private ButtonGroup buttonGroup;

    public GToolBar() {
        buttonGroup = new ButtonGroup();

        this.rectangleButton = new JRadioButton("Rectangle");
        this.triangleButton = new JRadioButton("Triangle");
        this.ovalButton = new JRadioButton("Oval");
        this.polygonButton = new JRadioButton("Polygon");
        this.textBoxButton = new JRadioButton("TextBox");

        buttonGroup.add(this.rectangleButton);
        buttonGroup.add(this.triangleButton);
        buttonGroup.add(this.ovalButton);
        buttonGroup.add(this.polygonButton);
        buttonGroup.add(this.textBoxButton);

        this.add(this.rectangleButton);
        this.add(this.triangleButton);
        this.add(this.ovalButton);
        this.add(this.polygonButton);
        this.add(this.textBoxButton);
    }

    public void initialize() {
    }
}
