package main;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public GDrawingPanel() {
	}
	
	//JPanel에 있는 메소든데, 대체시켜버리는 것임
	@Override
    protected void paintComponent(Graphics g) {
		//대체를 했으니까 부모할일을 먼저 하게 해줌(공식임)
		super.paintComponent(g);
        this.draw(g);
    }

	public void draw(Graphics g) {
		g.drawRect(20, 20, 100, 100);
	}
	
    public void initialize() {
    }
    
    // 이 구조에다가 그대로 집어넣기 (event handler 여기 overide해와서 하면 됌)
}
