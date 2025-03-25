package main;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GGraphicMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private JMenuItem lineThicknessItem;
	private JMenuItem lineStyleItem;
	private JMenuItem fontStyleItem;
	private JMenuItem fontSizeItem;
	
	public GGraphicMenu() {
		super("Graphic");

		this.lineThicknessItem = new JMenuItem("Line thickness");
		this.add(lineThicknessItem);
		this.lineStyleItem = new JMenuItem("Line Style");
		this.add(lineStyleItem);
		this.fontStyleItem = new JMenuItem("Font Style");
		this.add(fontStyleItem);
		this.fontSizeItem = new JMenuItem("Font Size");
		this.add(fontSizeItem);
	}

	
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
