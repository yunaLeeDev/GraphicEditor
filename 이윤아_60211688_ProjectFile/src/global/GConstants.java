package global;

import java.awt.Cursor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import shapes.GEllipse;
import shapes.GLine;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;
import shapes.GShape.EPoints;
import shapes.GTriangle;

public final class GConstants {
	
	private String launguageName = null;
	
	public String getLaunguageName() {
		return launguageName;
	}
	
	public void readFromFile(String fileName) {
		launguageName = fileName;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File file = new File(fileName);
			Document document = builder.parse(file);
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for(int i=0;i<nodeList.getLength();i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					if(node.getNodeName().equals(EMainFrame.class.getSimpleName())) {
						EMainFrame.setValues(node);
					} else if (node.getNodeName().equals(EFileMenuItem.class.getSimpleName())) {
						EFileMenuItem.setValue(node);
					} else if (node.getNodeName().equals(EShapeTool.class.getSimpleName())) {
						EShapeTool.setValue(node);
					} else if (node.getNodeName().equals(EMenuBarButton.class.getSimpleName())) {
						EMenuBarButton.setValue(node);
					} else if (node.getNodeName().equals(EContextMenuButton.class.getSimpleName())) {
						EContextMenuButton.setValue(node);
					}
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public enum EMainFrame {
		eX(0),
		eY(0),
		eW(0),
		eH(0);
		
		private int value;
		private EMainFrame(int value) {
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
		public static void setValues(Node node) {
			for(EMainFrame eMainFrame: EMainFrame.values()) {
				Node attribute = node.getAttributes().getNamedItem(eMainFrame.name());
				eMainFrame.value = Integer.parseInt(attribute.getNodeValue());
			}
		}

	}

	public enum EFileMenuItem{
	    eNew("", "newPanel", ""),
	    eOpen("", "open", ""),
	    eSave("", "save", ""),
	    eSaveAs("", "saveAs", ""),
	    ePrint("", "print", ""),
	    eQuit("", "quit", ""),
	    eClose("", "close", "");
	    
	    private String name; 
	    private String methodName; 
	    private String toolTipText; 
	    
	    private EFileMenuItem(String name, String methodName, String toolTipText) {
	        this.name = name;
	        this.methodName = methodName; 
	        this.toolTipText = toolTipText;
	    } 
	    
	    public String getName() {
	        return this.name;
	    }
	    
	    public String getMethodName() {
	        return this.methodName;
	    }

	    public String getToolTipText() {
	        return this.toolTipText;
	    }
	    
	    public static void setValue(Node node) {
	        NodeList menuItems = node.getChildNodes();
	        for (int i = 0; i < menuItems.getLength(); i++) {
	            Node menuItemNode = menuItems.item(i);
	            if (menuItemNode.getNodeType() == Node.ELEMENT_NODE) {
	                try {
	                    String enumName = menuItemNode.getNodeName(); 
	                    EFileMenuItem menuItem = EFileMenuItem.valueOf(enumName); 
	                    
	                    Node labelAttr = menuItemNode.getAttributes().getNamedItem("label");
	                    if (labelAttr != null) {
	                        menuItem.name = labelAttr.getNodeValue(); 
	                    }

	                    Node toolTipAttr = menuItemNode.getAttributes().getNamedItem("toolTipText");
	                    if (toolTipAttr != null) {
	                        menuItem.toolTipText = toolTipAttr.getNodeValue(); 
	                    }

	                } catch (IllegalArgumentException e) {
	                    System.err.println("Warning: No EFileMenuItem enum constant found for: " + menuItemNode.getNodeName());
	                }
	            }
	        }
	    }
	}
	
	public enum EMenuBarButton {
		eFileMenu(""),
		eShapeColorButton(""), 
		ePanelColorButton(""), 
		eResetButton(""),
		eUnGroupButton(""),
		eLanguageButton("");

		private String name;

		private EMenuBarButton(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static void setValue(Node node) {
			NodeList buttonItems = node.getChildNodes();
			for (int i = 0; i < buttonItems.getLength(); i++) {
				Node item = buttonItems.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					try {
						String enumName = item.getNodeName();
						EMenuBarButton button = EMenuBarButton.valueOf(enumName);
						Node labelAttr = item.getAttributes().getNamedItem("label");
						if (labelAttr != null) {
							button.name = labelAttr.getNodeValue();
						}
					} catch (IllegalArgumentException e) {
						System.err.println("Warning: Invalid EMenuBarButton item: " + item.getNodeName());
					}
				}
			}
		}
	}
	
	public enum EContextMenuButton {	
		eCopy(""),
	    ePaste(""),
	    eCut(""),
	    eBringToFront(""),
	    eSendToBack(""),
	    eDelete(""),
	    eShowStatus("");

		private String name;

		private EContextMenuButton(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static void setValue(Node node) {
			NodeList buttonItems = node.getChildNodes();
			for (int i = 0; i < buttonItems.getLength(); i++) {
				Node item = buttonItems.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					try {
						String enumName = item.getNodeName();
						EContextMenuButton button = EContextMenuButton.valueOf(enumName);
						Node labelAttr = item.getAttributes().getNamedItem("label");
						if (labelAttr != null) {
							button.name = labelAttr.getNodeValue();
						}
					} catch (IllegalArgumentException e) {
						System.err.println("Warning: Invalid EMenuBarButton item: " + item.getNodeName());
					}
				}
			}
		}
	}

	
	public enum EShapeTool {
		eSelect("", EPoints.e2P, GRectangle.class), 
		eRectangle("", EPoints.e2P, GRectangle.class),
		eTriangle("", EPoints.e2P, GTriangle.class),
		eEllipse("", EPoints.e2P, GEllipse.class),
		eLine("", EPoints.e2P, GLine.class),
		ePolygon("", EPoints.eNP, GPolygon.class);
		
		private String name;
		private EPoints ePoints;
		private Class<?> classShape;
		private EShapeTool(String name, EPoints ePoints, Class<?> classShape) {
			this.name = name;
			this.ePoints = ePoints;
			this.classShape = classShape;
		}
		public String getName() {
			return this.name;
		}
		
		public EPoints getEPoints() {
			return this.ePoints;
		}
		
		public GShape newShape(){
			try {
				GShape shape = (GShape) classShape.getConstructor().newInstance();
				return shape;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		 public static void setValue(Node node) {
	            NodeList shapeTools = node.getChildNodes();
	            for (int i = 0; i < shapeTools.getLength(); i++) {
	                Node shapeToolNode = shapeTools.item(i);
	                if (shapeToolNode.getNodeType() == Node.ELEMENT_NODE) {
	                    try {
	                        String enumName = shapeToolNode.getNodeName(); 
	                        EShapeTool shapeTool = EShapeTool.valueOf(enumName); 

	                        // XML에서 'name' 속성만 읽어와 설정
	                        Node nameAttr = shapeToolNode.getAttributes().getNamedItem("name");
	                        if (nameAttr != null) {
	                            shapeTool.name = nameAttr.getNodeValue(); 
	                        }

	                        // ePoints와 classShape는 XML에서 읽지 않음 (Java 코드에서 고정)

	                    } catch (IllegalArgumentException e) {
	                        System.err.println("Warning: Error processing EShapeTool enum constant: " + shapeToolNode.getNodeName() + " - " + e.getMessage());
	                    }
	                }
	            }
	        }
	}
	
	public enum EAnchor {
    	eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
    	eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
    	eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
    	eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
    	eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
    	eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
    	eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
    	eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
    	eRR(new Cursor(Cursor.HAND_CURSOR)),//앵커는 아닌데 도형 위에 있는 것 // 뺑뺑 도는거 붙여와
    	eMM(new Cursor(Cursor.MOVE_CURSOR)); 
    	
    	private Cursor cursor;
    	private EAnchor(Cursor cursor) {
    		this.cursor = cursor;
    	}
    	public Cursor getCursor( ) {
    		return this.cursor;
    	}
    }
}
