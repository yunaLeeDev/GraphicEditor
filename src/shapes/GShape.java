package shapes;

import java.awt.Graphics;

public abstract class GShape {
    protected int x1, y1, x2, y2;
    private String color;
    private String lineColor;
    private int lineThickness;
    private Boolean isSelect;

    public GShape(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = "#000000";		
        this.lineColor = "#000000";
        this.lineThickness = 1;
        this.isSelect = false;
    }

    public abstract void draw(Graphics g);

    // Getters and setters
    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void setSelected(Boolean selected) {
        this.isSelect = selected;
    }

    public Boolean isSelected() {
        return isSelect;
    }

    public String getColor() {
        return color;
    }
}
