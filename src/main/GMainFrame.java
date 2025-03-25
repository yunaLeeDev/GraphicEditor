package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GMainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private GMenuBar menuBar;
    private GDrawingPanel drawingPanel;
    private GToolBar toolBar;

    public GMainFrame() {
        this.setLocation(100, 200);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
        this.setLayout(new BorderLayout());

        this.menuBar = new GMenuBar();
        this.setJMenuBar(menuBar);

        this.toolBar = new GToolBar();
        this.add(toolBar, BorderLayout.NORTH);

        this.drawingPanel = new GDrawingPanel();
        this.add(drawingPanel, BorderLayout.CENTER);
    }

    public void initialize() {
    	// associated Attributes (자식과 연결이 다 끝난 다음에 해야 자식도, 자식의 자식도 보여짐)
        // 위에다가 쓰면 자기만 보이고 자식들은 안보이는 경우 발생함
    	this.setVisible(true);
        this.menuBar.initialize();
        this.toolBar.initialize();
        this.drawingPanel.initialize();
    }
}
