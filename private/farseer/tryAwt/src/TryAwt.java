import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TryAwt extends JFrame {
	class ToolBarAction extends AbstractAction{

	      public ToolBarAction(String name,Icon icon){
	        super(name,icon);
	      }
	      //对于JToolBar上按钮的事件处理是将组件的ActionCommand返回值字符串加入JTextArea中.
	      public void actionPerformed(ActionEvent e){
	    
	        try{
	          //theArea.append(e.getActionCommand()+"\n");
	        }catch(Exception ex){}
	      }
	    }//end of inner class ToolBarAction

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TryAwt mainF=new TryAwt();
		mainF.setLayout(new BorderLayout());
		JToolBar toolBar=new JToolBar();
		ToolBarAction tbNew=mainF.new ToolBarAction("new",null);
		
		toolBar.add(tbNew);
		mainF.add(toolBar,BorderLayout.NORTH);
		JTextArea text=new JTextArea();
		text.enableInputMethods(true);
		text.setAutoscrolls(true);
		JTextArea result=new JTextArea();
		result.setEditable(false);
		JSplitPane  sp= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false);
		sp.add(result);
		sp.add(text);
		sp.setDividerLocation(100);
		mainF.add(sp,BorderLayout.CENTER);
		mainF.setBounds(100, 100, 500, 400);
		mainF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainF.setVisible(true);		
	}

}
