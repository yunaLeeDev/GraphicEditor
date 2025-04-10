package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class GDrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Vector<GRectangle> rectangles;

    public GDrawingPanel() {
    	MouseHandler mouseHandler = new MouseHandler();
    	this.addMouseListener(mouseHandler);
    	this.addMouseMotionListener(mouseHandler);
    	this.rectangles = new Vector<GRectangle>();
    }
    
    protected void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics);
    	for(GRectangle rectangle: rectangles) {
    		rectangle.draw((Graphics2D)graphics);
    	}
    }
    
    private class MouseHandler implements MouseListener, MouseMotionListener {
    	
		@Override
	    public void mouseClicked(MouseEvent e) {
			System.out.println("mouse click");
	        Graphics2D graphics2d = (Graphics2D) getGraphics();
	        graphics2d.setXORMode(getBackground());

	        if (!isDrawing) {
	            transformer = new GTransformer();
	            transformer.start(graphics2d, e.getX(), e.getY());
	            isDrawing = true;
	        } else {
	            GRectangle rectangle = transformer.finish(graphics2d, e.getX(), e.getY());
	            rectangles.add(rectangle);
	            repaint();
	            isDrawing = false;
	        }
	    }
		 
		@Override
	    public void mouseMoved(MouseEvent e) {
	        if (isDrawing && transformer != null) {
	            Graphics2D graphics2d = (Graphics2D) getGraphics();
	            graphics2d.setXORMode(getBackground());
	            transformer.drag(graphics2d, e.getX(), e.getY());
	        }
	    }
		
		private int x1, y1;
		private GTransformer transformer;
		private boolean isDrawing = false;
		
		@Override
		public void mousePressed(MouseEvent e) {
//			transformer = new GTransformer();
//			Graphics2D graphics2d = (Graphics2D) getGraphics();
//			graphics2d.setXORMode(getBackground());
//			transformer.start(graphics2d, e.getX(), e.getY());
		}
//		
		@Override
		public void mouseDragged(MouseEvent e) {
//			if (isDrawing && transformer != null) {
//			Graphics2D graphics2d = (Graphics2D) getGraphics();
//			graphics2d.setXORMode(getBackground());
//			transformer.drag(graphics2d, e.getX(), e.getY());
//			}
		}
//
		@Override
		public void mouseReleased(MouseEvent e) {
//			Graphics2D graphics2d = (Graphics2D) getGraphics();
//			graphics2d.setXORMode(getBackground());
//			GRectangle rectangle = transformer.finish(graphics2d, e.getX(), e.getY());
//			rectangles.add(rectangle);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited");
		}
		
    }
    
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
