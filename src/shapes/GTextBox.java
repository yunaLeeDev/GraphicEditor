package shapes;

import java.awt.Graphics;

public class GTextBox extends GShape {
    private String text;

    public GTextBox(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        this.text = ""; // 초기 텍스트는 빈 문자열
    }

    @Override
    public void draw(Graphics g) {
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);
        
        // 사각형을 그려서 텍스트 박스를 표시
        g.drawRect(startX, startY, width, height);
        g.drawString(text, startX + 10, startY + 20); // 텍스트 그리기
    }

    // 텍스트를 설정하는 메소드
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
