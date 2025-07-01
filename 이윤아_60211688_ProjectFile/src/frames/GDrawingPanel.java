package frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import global.GConstants;
import global.GConstants.EAnchor;
import global.GConstants.EShapeTool;
import shapes.GShape;
import shapes.GShape.EPoints;
import transformers.GDrawer;
import transformers.GMover;
import transformers.GResizer;
import transformers.GRotater;
import transformers.GTransformer;

public class GDrawingPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public enum EDrawingState{
		eIdle,
		e2P,
		eNP
	}
	
	private Vector<GShape> shapes;
	private GTransformer transformer;
	private GShape currentShape;
	private Color selectedColor;
	private GShape selectedShape;
	private Color backGroundColor;
	private boolean bUpdated;
	private EShapeTool eShapeTool; 
	private EDrawingState eDrawingState;
	private JPopupMenu contextMenu;
	private JPopupMenu pasteOnlyMenu;
	private GShapeStatusPanel statusPanel;
	private GShape clipboardShape; // 복사된 도형 보관
	
	private Vector<GShape> groupedShapes;
	private Rectangle groupBounds;
	
	
	//Constructor
	public GDrawingPanel() {		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.currentShape = null;
		this.selectedShape = null;
		this.shapes = new Vector<GShape>();
		this.eShapeTool = null;
		this.eDrawingState = EDrawingState.eIdle;
		this.bUpdated = false;
		this.selectedColor = null;
		this.backGroundColor = this.getBackground();
		this.groupedShapes = new Vector<GShape>(); // 그룹화된 도형들
		this.groupBounds = null; // 그룹 경계
		
		initContextMenu();
		this.setFocusable(true); // 키 입력 가능하도록
		this.requestFocusInWindow();
		initKeyBindings();
	}

	public void initialize() {
		this.shapes.clear();
		this.repaint();
	}
	
	// getter and setter	
	public boolean isUpdated() {
		return this.bUpdated;
	}
	
	public void setColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
    
    public void setShapes(Object shapes) {
    	this.shapes = (Vector<GShape>) shapes;
    	this.repaint();
    }
	
	public Vector<GShape> getShapes() {
		return this.shapes;
	}

	public void setEShapeTool(EShapeTool eShapeTool) {
		this.eShapeTool = eShapeTool;
	}
	
	public void setPanelColor(Color color) {
	    this.setBackground(color);
	    this.repaint();
	}
	
	public void setBUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}
	
	// methods
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		for(GShape shape: shapes) {
			shape.draw(g2d);
		}
		drawGroupBounds(g2d);
	}

	private void startTransform(int x, int y) {
	    this.currentShape = eShapeTool.newShape();
	    this.shapes.add(this.currentShape);

	    if (this.eShapeTool != EShapeTool.eSelect) {
	        this.transformer = new GDrawer(this.currentShape);
	        transformer.start((Graphics2D) getGraphics(), x, y);
	        return;
	    }

	    this.selectedShape = onShape(x, y);
	    selectShape(this.selectedShape);

	    if (this.selectedShape == null) {
	        this.transformer = new GDrawer(this.currentShape);
	    } else {
	        EAnchor anchor = this.selectedShape.getESelectedAnchor();
	        boolean isGrouped = groupedShapes.contains(this.selectedShape);

	        switch (anchor) {
	            case eMM:
	                this.transformer = isGrouped ? new GMover(groupedShapes) : new GMover(this.selectedShape);
	                break;
	            case eRR:
	                this.transformer = isGrouped ? new GRotater(groupedShapes) : new GRotater(this.selectedShape);
	                break;
	            default:
	                this.transformer = isGrouped ? new GResizer(groupedShapes, this.selectedShape) : new GResizer(this.selectedShape);
	                break;
	        }
	    }
	    transformer.start((Graphics2D) getGraphics(), x, y);
	}

	
	private void keepTransform(int x, int y) {
	    transformer.drag((Graphics2D) getGraphics(), x, y);
	    this.repaint();
	}

	private GShape onShape(int x, int y){
		for (GShape shape: this.shapes) {
			if(shape.contains(x,y)){
				return shape;
			}
		}
		return null;
	}

	public void clearShapes() {
		this.shapes.clear();
		ungroupShapes();
		this.setPanelColor(backGroundColor);
		repaint();
	}
	
	private void finishTransform(int x, int y) {
		boolean found = false;	
		int count = 0;
		Vector<GShape> selectedShapes = new Vector<GShape>();
		
		transformer.finish((Graphics2D) getGraphics(), x, y);
		if(this.eShapeTool == EShapeTool.eSelect){
			this.shapes.remove(this.shapes.size()-1);
			if(this.shapes.size()>0) {
			for(GShape shape: this.shapes) {
				if(this.currentShape.contains(shape)) {
					shape.setSelected(true);
					selectedShapes.add(shape);
					found = true;
					count++;
				} else {
					shape.setSelected(false);
				}
			}
			}
			if(found == false) {
				this.selectShape(this.selectedShape);
			}
			
			// 여러 도형이 선택된 경우 그룹화 확인
			if (count > 1) {
	            int response = JOptionPane.showConfirmDialog(
	                this, 
	                "선택한 도형들을 그룹화하시겠습니까?", 
	                "그룹화 확인", 
	                JOptionPane.YES_NO_OPTION
	            );
	            if (response == JOptionPane.YES_OPTION) {
	            	            	
	        		for (GShape shape : selectedShapes) {
	        			shape.setGrouped(true);
	        			this.groupedShapes.add(shape);
	        		}	
	        		this.groupBounds = calculateGroupBounds(this.groupedShapes);
	            }
	        }
			
			if (this.selectedShape != null && this.groupedShapes.contains(this.selectedShape)) {
				this.groupBounds = null; // 그룹 경계 숨김
			}
			
		} else {
			// 도형 그리기 모드인경우 최근에 선택된 도형이 selectShape이도록
			this.selectShape(this.currentShape);
		}
 		this.bUpdated = true;
		this.repaint();
	}
	
	// 그룹 경계 박스
	private Rectangle calculateGroupBounds(Vector<GShape> groupShapes) {
		if (groupShapes.isEmpty()) {
			return null;
		}
		
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for (GShape shape : groupShapes) {
			Rectangle bounds = shape.getTransformedShape().getBounds();
			minX = Math.min(minX, bounds.x);
			minY = Math.min(minY, bounds.y);
			maxX = Math.max(maxX, bounds.x + bounds.width);
			maxY = Math.max(maxY, bounds.y + bounds.height);
		}
		
		int padding = 5;
		return new Rectangle(minX - padding, minY - padding, 
				maxX - minX + 2 * padding, maxY - minY + 2 * padding);
	}
	
	// 그룹 해제 
	public void ungroupShapes() {
		for (GShape shape : this.groupedShapes) {
			shape.setGrouped(false);
		}
		this.groupedShapes.clear();
		this.groupBounds = null;
	}
	
	private void drawGroupBounds(Graphics2D g2d) {
		if (groupBounds != null && !groupedShapes.isEmpty()) {

			Stroke originalStroke = g2d.getStroke();
			float[] dashPattern = {5.0f, 5.0f}; 
			BasicStroke dashedStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, 
					BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f);
			g2d.setStroke(dashedStroke);			

			g2d.setColor(Color.BLACK);
			g2d.drawRect(groupBounds.x, groupBounds.y, groupBounds.width, groupBounds.height);
			
			g2d.setStroke(originalStroke);
		}
	}
	
	
	
	private void selectShape(GShape shape) {
		for(GShape otherShape: this.shapes) {
			otherShape.setSelected(false);
		}
		if(shape != null) {
		shape.setSelected(true);
		}
		this.selectedShape = shape;
		
		if (shape != null && this.groupedShapes.contains(shape)) {
			this.groupBounds = null;
		}
	}
	
	private void addPoint(int x, int y) {
		this.transformer.addPoint((Graphics2D) getGraphics(), x, y);
	}
	
	private void changeCursor(int x, int y) {
	    if(this.eShapeTool == EShapeTool.eSelect) {
	        GShape hoverShape = onShape(x, y);  
	        
	        if(hoverShape == null) {
	            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	        } else {
	            if(this.selectedShape != null && this.selectedShape == hoverShape) {
	                EAnchor eAnchor = this.selectedShape.getESelectedAnchor();
	                this.setCursor(eAnchor.getCursor());
	            } else {
	                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	            }
	        }
	    }
	}
	
	public void changeColor(int x, int y, Color color) {
		currentShape = onShape(x, y);
		if(currentShape != null) {
			currentShape.setColor(color);
			this.bUpdated = true;
		}
	}
	
	public void bringToFront() {
	    if (this.selectedShape != null) {
	        this.shapes.remove(this.selectedShape);  
	        this.shapes.add(this.selectedShape);   
	        // 선택 상태 유지
	        selectShape(this.selectedShape);
	        this.repaint();
	        this.bUpdated = true;
	    }
	}

	public void sendToBack() {
	    if (this.selectedShape != null) {
	        this.shapes.remove(this.selectedShape);   
	        this.shapes.insertElementAt(this.selectedShape, 0); 
	        // 선택 상태 유지
	        selectShape(this.selectedShape);
	        this.repaint();
	        this.bUpdated = true;
	    }
	}


	private void deleteShape() {
		if (this.selectedShape != null) {	
			this.shapes.remove(this.selectedShape);
			this.selectedShape = null;
			this.repaint();
			this.bUpdated = true;		
		} 
	}
	
	private void showShapeStatus() {
	    if (this.selectedShape != null) {
	        // 상태창이 없거나 닫혀있으면 새로 생성
	        if (statusPanel == null || !statusPanel.isDisplayable()) {
	            statusPanel = new GShapeStatusPanel();
	            // 메인 Frame 옆에 새로운 창 열기
	            JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
	            if (parentFrame != null) {
	                statusPanel.setLocation(
	                    parentFrame.getX() + parentFrame.getWidth() + 10,
	                    parentFrame.getY()
	                );
	            }
	        }
	        statusPanel.updateShapeInfo(this.selectedShape);
	        statusPanel.setVisible(true);
	    }
	}
	
	// 우클릭 처리
	private void handleRightClick(MouseEvent e) {
	    GShape clickedShape = onShape(e.getX(), e.getY());
	    if (clickedShape != null) {
	        selectShape(clickedShape);
	        selectedShape = clickedShape;
	        repaint();
	        contextMenu.show(e.getComponent(), e.getX(), e.getY());
	    } else {
	        // 빈 공간에서 우클릭한 경우
	        selectShape(null);
	        repaint();
	        pasteOnlyMenu.show(e.getComponent(), e.getX(), e.getY());
	    }
	}

	
	// Context메뉴 설정
	public void initContextMenu() {
			
		//xml로 변경
		JMenuItem bringToFrontItem = new JMenuItem(GConstants.EContextMenuButton.eBringToFront.getName());
		bringToFrontItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			bringToFront();
			}
		});

		JMenuItem sendToBackItem = new JMenuItem(GConstants.EContextMenuButton.eSendToBack.getName());
		sendToBackItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			sendToBack();
			}
		});

		JMenuItem showStatusItem = new JMenuItem(GConstants.EContextMenuButton.eShowStatus.getName());
		showStatusItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			showShapeStatus();
			}
		});
		
		JMenuItem deleteItem = new JMenuItem(GConstants.EContextMenuButton.eDelete.getName());
		deleteItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			deleteShape();
			}
		});
		
		JMenuItem copyItem = new JMenuItem(GConstants.EContextMenuButton.eCopy.getName());
		copyItem.addActionListener(e -> copyShape());

		JMenuItem pasteItem1 = new JMenuItem(GConstants.EContextMenuButton.ePaste.getName());
		pasteItem1.addActionListener(e -> pasteShape());
		
		JMenuItem pasteItem2 = new JMenuItem(GConstants.EContextMenuButton.ePaste.getName());
		pasteItem2.addActionListener(e -> pasteShape());
		
		JMenuItem cutItem = new JMenuItem(GConstants.EContextMenuButton.eCut.getName());
		cutItem.addActionListener(e -> cutShape());

		contextMenu = new JPopupMenu();
		
		contextMenu.add(copyItem);
		contextMenu.add(pasteItem1);
		contextMenu.add(cutItem);
		contextMenu.add(bringToFrontItem);
		contextMenu.add(sendToBackItem);
		contextMenu.add(deleteItem);
		contextMenu.addSeparator();
		contextMenu.add(showStatusItem);
		
		pasteOnlyMenu = new JPopupMenu();
		
		pasteOnlyMenu.add(pasteItem2);
	}
	
	
	
	private class MouseHandler implements MouseListener, MouseMotionListener {
	
		@Override
		public void mousePressed(MouseEvent e) {
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// 우클릭시 
			if (e.isPopupTrigger()) {
		        handleRightClick(e);
		    }
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 1){
				this.mouse1Clicked(e);
			} else if(e.getClickCount()==2){
				this.mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {
			
			requestFocusInWindow();
			
			if(selectedColor != null) {
				changeColor(e.getX(),e.getY(), selectedColor);
				selectedColor = null;
			}

			if(eDrawingState == EDrawingState.eIdle) {
				if(eShapeTool.getEPoints() == EPoints.e2P) {
					startTransform(e.getX(),e.getY());
					eDrawingState = EDrawingState.e2P;
				} else if(eShapeTool.getEPoints() == EPoints.eNP) {
					startTransform(e.getX(),e.getY());
					eDrawingState = EDrawingState.eNP;
				}				
			} else if(eDrawingState == EDrawingState.e2P) {
				// 두번째 클릭시 점 2개 도형 마무리
				finishTransform(e.getX(),e.getY());
				eDrawingState = EDrawingState.eIdle;
			} else if(eDrawingState == EDrawingState.eNP) {
				// 두번째 클릭시 폴리곤 점 추가
				addPoint(e.getX(),e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(eDrawingState == EDrawingState.e2P) {
				keepTransform(e.getX(),e.getY());
			} else if(eDrawingState == EDrawingState.eNP) {
				keepTransform(e.getX(),e.getY());
			} else if(eDrawingState == EDrawingState.eIdle){
				changeCursor(e.getX(),e.getY());
			}
		}

		private void mouse2Clicked(MouseEvent e) {
			// 폴리곤 마무리
			if(eDrawingState == EDrawingState.eNP) {
				finishTransform(e.getX(),e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
		
		
	}
	
	private void copyShape() {
	    SwingUtilities.invokeLater(() -> {
	        if (this.selectedShape != null) {
	            this.clipboardShape = this.selectedShape.clone();
	            pasteOffset = 0;
	        }
	    });
	}


	private int pasteOffset = 0;

	private void pasteShape() {
	    if (this.clipboardShape != null) {
	        GShape newShape = this.clipboardShape.clone();
	        pasteOffset += 20;  // 계속 이동되도록
	        newShape.moveBy(pasteOffset, pasteOffset);
	        this.shapes.add(newShape);
	        this.selectShape(newShape);
	        this.repaint();
	        this.bUpdated = true;
	    }
	}

	private void cutShape() {
	    if (this.selectedShape != null) {
	        this.clipboardShape = this.selectedShape.clone();
	        this.shapes.remove(this.selectedShape);
	        this.selectedShape = null;
	        this.repaint();
	        this.bUpdated = true;
	    }
	}
	
	private void initKeyBindings() {
		this.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control C"), "copy");
	    this.getActionMap().put("copy", new AbstractAction() {
	        public void actionPerformed(ActionEvent e) {
	            if (selectedShape != null) {
	                copyShape();
	            } 
	        }
	    });


		this.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control V"), "paste");
		this.getActionMap().put("paste", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pasteShape();
			}
		});

		this.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke("control X"), "cut");
		this.getActionMap().put("cut", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cutShape();
			}
		});
	}


}