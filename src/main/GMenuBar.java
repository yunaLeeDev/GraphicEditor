package main;

import javax.swing.JMenuBar;

public class GMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private GGraphicMenu gGraphicMenu;
	private GFileMenu gFileMenu;
	private GEditMenu gEditMenu;
	
	public GMenuBar() {
		this.gFileMenu = new GFileMenu();
		this.add(gFileMenu);
		this.gEditMenu = new GEditMenu();
		this.add(gEditMenu);
		this.gGraphicMenu = new GGraphicMenu();
		this.add(gGraphicMenu);
	}
	
	public void initialize() {
		this.gEditMenu.initialize();
		this.gFileMenu.initialize();
		this.gGraphicMenu.initialize();
	}
}
