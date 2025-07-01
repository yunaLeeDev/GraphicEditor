package frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.GConstants.EShapeTool;

public class GShapeToolBar extends JToolBar{
	private static final long serialVersionUID = 1L;

	//association
	private GDrawingPanel drawingPanel;
	
	public GShapeToolBar(GDrawingPanel gDrawingPanel) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addToolbarButton();
	}

	private void addToolbarButton() {
		ButtonGroup group = new ButtonGroup();

		for (EShapeTool eShapeType : EShapeTool.values()) {
			JRadioButton button = new JRadioButton(eShapeType.getName());
			ActionListener actionListener = new ActionHandler();
			button.addActionListener(actionListener);
			button.setActionCommand(eShapeType.toString()); 
			group.add(button);
			this.add(button); 
		}
	}
	
	public void updateText() {
		int count = this.getComponentCount();
		for (int i = 0; i < count; i++) {
			if (this.getComponent(i) instanceof JRadioButton) {
				JRadioButton button = (JRadioButton) this.getComponent(i);
				EShapeTool eShapeTool = EShapeTool.valueOf(button.getActionCommand());
				button.setText(eShapeTool.getName());
			}
		}
	}

	
	public void initialize() {
		JRadioButton button = (JRadioButton) this.getComponent(EShapeTool.eSelect.ordinal());
		button.doClick();
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sShapeTool = e.getActionCommand();
			EShapeTool eShapeTool = EShapeTool.valueOf(sShapeTool);
			//누른 버튼별로 값 찾아오기
			drawingPanel.setEShapeTool(eShapeTool);
		}
		
	}
}
