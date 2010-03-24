package xyzlex.gui;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

public class XyzlexEditor extends JFrame {
	private JTextArea txtResult;
	private JEditorPane txtText;
	private String text;
	private XyzlexEditor mainFrame;
	
	public XyzlexEditor(){
		mainFrame=this;
		setVisible(true);
		setBounds(100,100,600,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		buildToolbar();
		buildTextArea();
	}
	
	
	private void buildTextArea() {
		txtResult=new JTextArea();
		txtText=new JEditorPane();
		JSplitPane sp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false, txtResult,txtText);
		sp.setDividerLocation(150);
		this.add(sp,BorderLayout.CENTER);
		
	}


	private void buildToolbar() {
		JToolBar toolbar=new JToolBar();
		this.add(toolbar,BorderLayout.NORTH);
		toolbar.add(new AbstractAction("New"){
			@Override
			public void actionPerformed(ActionEvent e) {
				text="";
				txtResult.setText(text);
				txtText.setText(text);
			}			
		});
		
		toolbar.add(new AbstractAction("Open"){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    int returnVal = chooser.showOpenDialog(mainFrame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       openFile(chooser.getSelectedFile().getName());
			    }

			}
		});
		
	}
	

	private void openFile(String name) {
		
	}



	public static void main(String[] args) {
		XyzlexEditor editor=new XyzlexEditor();
		
	}

}
