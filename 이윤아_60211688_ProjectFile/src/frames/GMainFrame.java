package frames;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import global.GConstants;

public class GMainFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	private GMenuBar menuBar; 
	private GShapeToolBar toolBar; 
	private GDrawingPanel drawingPanel; 
	private GConstants gConstants;
	

	public GMainFrame() {
		
		this.gConstants = new GConstants();
		this.gConstants.readFromFile("./configKorea.xml");
		
		//attributes
		this.setLocation(GConstants.EMainFrame.eX.getValue(), GConstants.EMainFrame.eY.getValue());
		this.setSize(GConstants.EMainFrame.eW.getValue(), GConstants.EMainFrame.eH.getValue());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//components
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout); 
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);
		
		this.drawingPanel = new GDrawingPanel();
		this.toolBar = new GShapeToolBar(drawingPanel);
		this.add(drawingPanel, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.NORTH);
		
	}

	public void initialize() {
		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
		this.menuBar.associate(this.gConstants);
		this.menuBar.associate(this.toolBar);
		this.setVisible(true);
		
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
		
	}
}
