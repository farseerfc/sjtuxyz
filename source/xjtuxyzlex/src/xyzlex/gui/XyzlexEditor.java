package xyzlex.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import xyzlex.counter.Counter;
import xyzlex.node.Token;

public class XyzlexEditor extends JFrame {
	private JTextArea txtResult;
	private JTextArea txtText;
	private String text;
	private XyzlexEditor mainFrame;
	private String filePath;
	private boolean changed;
	
	public XyzlexEditor(){
		filePath="";
		mainFrame=this;
		setTitle("Xyz lexer Editor");
		setVisible(true);
		setBounds(100,100,600,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		buildToolbar();
		buildTextArea();
	}
	
	
	private void buildTextArea() {
		txtResult=new JTextArea();
		txtResult.setAutoscrolls(true);
		txtResult.setEditable(false);
		
		txtText=new JTextArea();
		txtText.setAutoscrolls(true);
		txtText.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				changed=true;
				text=txtText.getText();
				formatText();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changed=true;
				text=txtText.getText();
				formatText();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changed=true;
				text=txtText.getText();
				formatText();
			}
			
		});
		
		
		JSplitPane sp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,
				new JScrollPane(txtResult),new JScrollPane(txtText));
		sp.setDividerLocation(0.3);
		sp.resetToPreferredSizes();
		this.add(sp,BorderLayout.CENTER);
		
		
	}
	
	private boolean askChanged() {
		int returnValue=	JOptionPane.showConfirmDialog(mainFrame, "File has been changed, Save?");
		if(returnValue==JOptionPane.OK_OPTION){
			saveOrSaveAs();
		}else if(returnValue==JOptionPane.CANCEL_OPTION){
			return false;
		}
		return true;
			
	}	

	private void saveOrSaveAs() {
		if(filePath==null||filePath.equals("")){
			saveAs();
		}else{
			saveFile();
		}
	}
	
	private void saveAs(){
		JFileChooser chooser = new JFileChooser();
	    int returnVal = chooser.showSaveDialog(mainFrame);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	filePath=chooser.getSelectedFile().getPath();
	    	saveFile();
	    }
	}


	private void buildToolbar() {
		JToolBar toolbar=new JToolBar();
		this.add(toolbar,BorderLayout.NORTH);
		toolbar.add(new AbstractAction("New"){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(changed){
					if(!askChanged())return;
				}	
				text="";
				txtResult.setText(text);
				txtText.setText(text);
				changed=false;
				filePath="";
			}

		
		});
		
		toolbar.add(new AbstractAction("Open..."){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(changed){
					if(!askChanged())return;
				}
				
				JFileChooser chooser = new JFileChooser();
			    int returnVal = chooser.showOpenDialog(mainFrame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	filePath=chooser.getSelectedFile().getPath();
			    	openFile();
			    }

			}
		});
		
		toolbar.add(new AbstractAction("Save"){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveOrSaveAs();
			}
			
		});
		
		toolbar.add(new AbstractAction("Save as..."){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}

		});
		
	}
	

	private void openFile() {
		try {
			FileReader reader=new FileReader(filePath);
			StringBuilder sb=new StringBuilder();
			while(reader.ready()){
				int c=reader.read();
				if(c==-1)break;
				sb.append((char)c);
			}
			reader.close();
			txtText.setText(sb.toString());
			changed=false;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(mainFrame,
					"File "+filePath+" does not exist",
					"File does not exist", 0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mainFrame,
					"File "+filePath+" can not read",
					"File can not read", 0);
		}
	}


	private void saveFile() {
		try {
			FileWriter fw=new FileWriter(filePath);
			fw.write(text);
			fw.close();
			changed=false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mainFrame,
					"File "+filePath+" can not write",
					"File can not write", 0);
		}
	}
	


	private void formatText() {
		//txtText.setText(text);
		Counter counter=new Counter(text);
		StringBuilder cresult=new StringBuilder();
		for(Class<? extends Token> tokenClass: counter.getTokenKinds()){
			cresult.append(tokenClass.getSimpleName());
			cresult.append("\t:");
			cresult.append(counter.getCount(tokenClass));
			cresult.append("\r\n");
		}
		if(counter.getException()!=null){
			cresult.append(counter.getException().getMessage());
		}
		txtResult.setText(cresult.toString());
	}


	public static void main(String[] args) {
		XyzlexEditor editor=new XyzlexEditor();		
	}

}
