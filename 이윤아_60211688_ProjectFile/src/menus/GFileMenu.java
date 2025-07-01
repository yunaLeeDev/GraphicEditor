package menus;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import frames.GDrawingPanel;
import global.GConstants.EFileMenuItem;


public class GFileMenu extends JMenu{
   private static final long serialVersionUID = 1L;
   
   private File dir;
   private File file;
   private String fileAddress;
   private GDrawingPanel drawingPanel;
   
   public GFileMenu(String name, String fileAddress) {
      super(name);
      this.fileAddress = fileAddress;
      
      ActionHandler actionHandler = new ActionHandler();
      for(EFileMenuItem eMenuItem : EFileMenuItem.values() ) {
         JMenuItem menuItem = new JMenuItem(eMenuItem.getName());
         menuItem.addActionListener(actionHandler);
         menuItem.setActionCommand(eMenuItem.name());
         menuItem.setToolTipText(eMenuItem.getToolTipText());
         this.add(menuItem);
      }
      
   }
   
   public void updateText() {
	    int itemCount = this.getItemCount(); 
	    for (int i = 0; i < itemCount; i++) {
	        JMenuItem item = this.getItem(i);
	        if (item != null) {
	            // enum에서 새 텍스트 가져오기
	            EFileMenuItem eItem = EFileMenuItem.values()[i];
	            item.setText(eItem.getName());
	            item.setToolTipText(eItem.getToolTipText());
	        }
	    }
	}



   public void initialize() {
      // xml 파일 만들기
      this.dir = new File(this.fileAddress);
      this.file = null;
   }
   
   
   public void associate(GDrawingPanel drawingPanel) {
      this.drawingPanel = drawingPanel;
   }
   
   public void newPanel() {
      System.out.println("newPanel");
      if(this.close()) {
         // cancel이 아나면(yes or no 중에 하나를 선택 했으면)
         //   new 해라 (새로운 패널 만들어라)
         this.drawingPanel.initialize();
         // 만들고나면 초기화 (새 패널을 만든거니까)
         this.file = null;
      }
   }
   
   public void open() {
      System.out.println("open"); 	     
    	 JFileChooser chooser = new JFileChooser(this.dir);
    	 FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "gvs");
         chooser.setFileFilter(filter);
         int returnVal = chooser.showOpenDialog(this.drawingPanel);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
             this.file = chooser.getSelectedFile(); 
             this.dir = chooser.getCurrentDirectory();
             try {
	            FileInputStream fileinputStream = new FileInputStream(this.file);
	 	        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileinputStream);
	 	        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
	 	        this.drawingPanel.setShapes(objectInputStream.readObject());
	 	        objectInputStream.close();
              } catch (IOException e) {
    	         // TODO Auto-generated catch block
    	         e.printStackTrace();
    	      } catch (ClassNotFoundException e) {
    	         // TODO Auto-generated catch block
    	         e.printStackTrace();
    	      }
         } else {
        	 
	    }   
   }
   
    public boolean save() {
         System.out.println("save");
         if(this.file == null) {
            // 캔슬이 아니면
            if(!this.saveAs()) {
               try {
                   // byte array를 파일에 써줌
                   FileOutputStream fileOutputStream = new FileOutputStream(this.file);
                   // data segment를 byte stream으로 바꿔줌
                   BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                   ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
                   // 3개를 연결해서 output channel을 만듦 vector을 여기다가 씀
                   objectOutputStream.writeObject(this.drawingPanel.getShapes());
                   objectOutputStream.close();
                   // 저장하면 dirty bit 초기화해주기
                   this.drawingPanel.setBUpdated(false);
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
         } else {
        	 try {
	             FileOutputStream fileOutputStream = new FileOutputStream(this.file);
	             // data segment를 byte stream으로 바꿔줌
	             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
	             ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
	             // 3개를 연결해서 output channel을 만듦 vector을 여기다가 씀
	             objectOutputStream.writeObject(this.drawingPanel.getShapes());
	             objectOutputStream.close();
	             // 저장하면 dirty bit 초기화해주기
	             this.drawingPanel.setBUpdated(false); 
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return false;
      }
   
   public boolean saveAs() {
      System.out.println("saveAs");
      boolean bCancel = false;
      JFileChooser chooser = new JFileChooser(this.dir);
      chooser.setSelectedFile(this.file);
      
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "gvs");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showSaveDialog(this.drawingPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
         this.dir = chooser.getCurrentDirectory();
         File selectedFile = chooser.getSelectedFile();
         if (!selectedFile.getName().toLowerCase().endsWith(".gvs")) {
	            selectedFile = new File(selectedFile.getAbsolutePath() + ".gvs");
	        }
	     this.file = selectedFile;
         this.save();
      } else {
         bCancel = true;
      }
      return bCancel;
   }
   
   public void print() {
      System.out.println("print");

   }
   
   public void quit() {
      System.out.println("quit");
      if(this.close()) {
         System.exit(0);
      }
   }
   
   public boolean close() {
      boolean bCancel = false;
      if(this.drawingPanel.isUpdated()) {
         int reply = JOptionPane.NO_OPTION;
         reply  = JOptionPane.showConfirmDialog(this.drawingPanel, "변경내용을 저장 할까요?");
         if(reply == JOptionPane.CANCEL_OPTION) {
            bCancel = true;
         } else if(reply == JOptionPane.OK_OPTION){
            // save로 가서 file name chooser로 가고, file name이 null이냐 아니냐로 가름 거기서 cancel하면 다 취소해야하니까 (save 영역에서도 cancel이 일어나니까)
            bCancel = this.save();
         }
      }
      return !bCancel;
   }
   
   private void invokeMethod(String methodName) {
      try {
         //객체의 메모리를 만들어서 메모리 주소를 호출하는 것임. 만들어진 메모리 주소를 던져주는것. invoke(this)부분 얘기임
         this.getClass().getMethod(methodName).invoke(this);
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException exception) {
         exception.printStackTrace();
      } 
   }
   private class ActionHandler implements ActionListener{

      //구현 필요
      @Override
      public void actionPerformed(ActionEvent event) {
         EFileMenuItem eFileMenuItem = EFileMenuItem.valueOf(event.getActionCommand());
         invokeMethod(eFileMenuItem.getMethodName());
      }
   }

}