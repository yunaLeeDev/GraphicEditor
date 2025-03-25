package main;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem propertyItem;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	
	public GEditMenu() {
		super("Edit");
		this.propertyItem = new JMenuItem("New");
		this.add(propertyItem);
		this.undoItem = new JMenuItem("Undo");
		this.add(undoItem);
		this.redoItem = new JMenuItem("Redo");
		this.add(redoItem);
	}

	public void initialize() {
	}

}
