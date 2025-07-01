package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import global.GConstants;
import menus.GFileMenu;

public class GMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;

    private GFileMenu fileMenu;
    private GDrawingPanel drawingPanel;
    private GConstants gConstants;
    private GShapeToolBar gShapeToolBar;

    public JRadioButton colorButton;
    public JPanel chooseColorPanel;
    public Color currentColor;
    public boolean isColorSelected;

    // 버튼들을 멤버 변수로 선언
    private JButton shapeColorButton;
    private JButton panelColorButton;
    private JButton resetButton;
    private JButton ungroupButton;
    private JButton languageButton;

    // 오른쪽 버튼들을 담을 패널
    private JPanel rightPanel;

    public GMenuBar() {

        isColorSelected = false;

        this.fileMenu = new GFileMenu(GConstants.EMenuBarButton.eFileMenu.getName(),
                "\");
        this.add(this.fileMenu, BorderLayout.WEST);  // 왼쪽 고정

        // 오른쪽 버튼들을 담을 패널 생성 (FlowLayout 오른쪽 정렬)
        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        this.shapeColorButton = new JButton(GConstants.EMenuBarButton.eShapeColorButton.getName());
        this.panelColorButton = new JButton(GConstants.EMenuBarButton.ePanelColorButton.getName());
        this.resetButton = new JButton(GConstants.EMenuBarButton.eResetButton.getName());
        this.ungroupButton = new JButton(GConstants.EMenuBarButton.eUnGroupButton.getName());
        this.languageButton = new JButton(GConstants.EMenuBarButton.eLanguageButton.getName());

        // 오른쪽 패널에 버튼 추가
        rightPanel.add(shapeColorButton);
        rightPanel.add(panelColorButton);
        rightPanel.add(resetButton);
        rightPanel.add(ungroupButton);
        rightPanel.add(languageButton);

        this.add(rightPanel, BorderLayout.EAST);

        // chooseColorPanel은 필요하다면 유지
        this.chooseColorPanel = new JPanel();

        // Action Listeners
        this.shapeColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(chooseColorPanel, "색상 선택", chooseColorPanel.getBackground());
                if (selectedColor != null) {
                    drawingPanel.setColor(selectedColor);
                }
            }
        });

        this.panelColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(chooseColorPanel, "색상 선택", chooseColorPanel.getBackground());
                if (selectedColor != null) {
                    drawingPanel.setPanelColor(selectedColor);
                }
            }
        });

        this.ungroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawingPanel != null) {
                    drawingPanel.ungroupShapes();
                    drawingPanel.repaint();
                }
            }
        });

        this.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawingPanel != null) {
                    drawingPanel.clearShapes();
                    drawingPanel.repaint();
                }
            }
        });

        this.languageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gConstants != null) {
                    if (gConstants.getLaunguageName().equals("./configEnglish.xml")) {
                        gConstants.readFromFile("./configKorea.xml");
                    } else {
                        gConstants.readFromFile("./configEnglish.xml");
                    }
                    fileMenu.updateText();
                    gShapeToolBar.updateText();
                    drawingPanel.initContextMenu();
                    updateText(); // 메뉴바 버튼 텍스트 업데이트
                }
            }
        });
    }

    // 텍스트를 현재 언어로 갱신하는 메서드
    public void updateText() {
        this.fileMenu.setText(GConstants.EMenuBarButton.eFileMenu.getName());
        this.shapeColorButton.setText(GConstants.EMenuBarButton.eShapeColorButton.getName());
        this.panelColorButton.setText(GConstants.EMenuBarButton.ePanelColorButton.getName());
        this.resetButton.setText(GConstants.EMenuBarButton.eResetButton.getName());
        this.ungroupButton.setText(GConstants.EMenuBarButton.eUnGroupButton.getName());
        this.languageButton.setText(GConstants.EMenuBarButton.eLanguageButton.getName());
    }

    // 연관 객체 설정
    public void initialize() {
        this.fileMenu.initialize();
        this.fileMenu.associate(drawingPanel);
    }

    public void associate(GDrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    public void associate(GConstants gConstants) {
        this.gConstants = gConstants;
    }

    public void associate(GShapeToolBar toolBar) {
        this.gShapeToolBar = toolBar;
    }
}
