package shapes;

import java.awt.Graphics;

public class GTextBox extends GShape {
    private String text; // 텍스트 필드
    private boolean isEditing; // 텍스트 입력 중인지 여부

    public GTextBox(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        this.text = "";
        this.isEditing = true;
    }

    @Override
    public void draw(Graphics g) {
        // 도형의 테두리 그리기
        g.drawRect(x1, y1, x2 - x1, y2 - y1);

        // 텍스트를 그리기
        g.drawString(text, x1 + 5, y1 + 15);  // 텍스트가 사각형 안에 표시되도록
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
