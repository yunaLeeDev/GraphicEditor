package main;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GFileMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem quitItem;
	
	public GFileMenu() {
		super("File");
		this.newItem = new JMenuItem("New");
		this.add(newItem);
		this.openItem = new JMenuItem("Open");
		this.add(openItem);
		this.saveItem = new JMenuItem("Save");
		this.add(saveItem);
		this.saveAsItem = new JMenuItem("Save As");
		this.add(saveAsItem);
		this.quitItem = new JMenuItem("Quit");
		this.add(quitItem);
	}
	public void initialize() {
	}
}
