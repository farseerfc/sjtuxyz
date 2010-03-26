package xyzlex.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.*;

import xyzlex.counter.Counter;
import xyzlex.node.Token;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import javax.swing.ImageIcon;

public class XyzlexEditor extends JFrame {
	private JTextPane txtResult;
	private JTextPane txtText;
	private String text;
	private XyzlexEditor mainFrame;
	private String filePath;
	private boolean changed;
	private boolean needFormat;
	private JPanel panel;
	private String labelText;
	private JLabel statusLabel;

	public XyzlexEditor() {
		getContentPane().setForeground(new Color(139, 0, 0));

		changed = false;
		needFormat = false;
		filePath = "";
		mainFrame = this;
		labelText = "";
		setTitle("Xyz lexer Editor");
		setVisible(true);
		setBounds(100, 100, 600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		buildMenuBar();
		buildToolbar();
		buildTextArea();
		buildStatusLabel();
		startFormatTimer();
	}

	private void startFormatTimer() {
		new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (needFormat)
					formatText();
			}
		}).start();
	}

	private void buildTextArea() {
		txtResult = new JTextPane();
		txtResult.setAutoscrolls(true);
		txtResult.setEditable(false);

		txtText = new JTextPane();
		txtText.setAutoscrolls(true);
		txtText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				changed = true;
				needFormat = true;
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changed = true;
				needFormat = true;
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changed = true;
				needFormat = true;
			}

		});

		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				new JScrollPane(txtResult), new JScrollPane(txtText));
		sp.setDividerLocation(220);
		sp.resetToPreferredSizes();
		panel.add(sp, BorderLayout.CENTER);
	}

	private boolean askChanged() {
		int returnValue = JOptionPane.showConfirmDialog(mainFrame,
				"File has been changed, Save?");
		if (returnValue == JOptionPane.OK_OPTION) {
			saveOrSaveAs();
		} else if (returnValue == JOptionPane.CANCEL_OPTION) {
			return false;
		}
		return true;

	}

	private void saveOrSaveAs() {
		if (filePath == null || filePath.equals("")) {
			saveAs();
		} else {
			saveFile();
		}
	}

	private void saveAs() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filePath = chooser.getSelectedFile().getPath();
			saveFile();
		}
	}

	private void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		this.add(menuBar, BorderLayout.PAGE_START);
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("New");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (changed) {
					if (!askChanged())
						return;
				}
				text = "";
				txtResult.setText(text);
				txtText.setText(text);
				changed = false;
				filePath = "";
				needFormat = true;
			}
		});
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("Open...");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (changed) {
					if (!askChanged())
						return;
				}

				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(mainFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filePath = chooser.getSelectedFile().getPath();
					openFile();
				}
			}
		});
		menu.add(menuItem_1);

		JMenuItem menuItem_2 = new JMenuItem("Save");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveOrSaveAs();
			}
		});
		menu.add(menuItem_2);

		JMenuItem menuItem_3 = new JMenuItem("Save as...");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});
		menu.add(menuItem_3);

		JMenuItem menuItem_4 = new JMenuItem("Exit");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (changed) {
					if (!askChanged())
						return;
				}
				System.exit(0);
			}
		});
		menu.add(menuItem_4);
	}

	private void buildToolbar() {
		JToolBar toolbar = new JToolBar();
		panel = new JPanel();
		this.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout());
		panel.add(toolbar, BorderLayout.NORTH);
		toolbar.add(new AbstractAction("New") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (changed) {
					if (!askChanged())
						return;
				}
				text = "";
				txtResult.setText(text);
				txtText.setText(text);
				changed = false;
				filePath = "";
				needFormat = true;
			}

		});

		toolbar.add(new AbstractAction("Open...") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (changed) {
					if (!askChanged())
						return;
				}

				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(mainFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filePath = chooser.getSelectedFile().getPath();
					openFile();
				}

			}
		});

		toolbar.add(new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveOrSaveAs();
			}

		});

		toolbar.add(new AbstractAction("Save as...") {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}

		});
	}

	private void buildStatusLabel() {
		statusLabel = new JLabel();
		panel.add(statusLabel, BorderLayout.SOUTH);
		statusLabel.setText(labelText);
	}

	private void openFile() {
		try {
			FileReader reader = new FileReader(filePath);
			StringBuilder sb = new StringBuilder();
			while (reader.ready()) {
				int c = reader.read();
				if (c == -1)
					break;
				sb.append((char) c);
			}
			reader.close();
			txtText.setText(sb.toString());
			changed = false;
			needFormat = true;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(mainFrame, "File " + filePath
					+ " does not exist", "File does not exist", 0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mainFrame, "File " + filePath
					+ " can not read", "File can not read", 0);
		}
	}

	private void saveFile() {
		try {
			FileWriter fw = new FileWriter(filePath);
			fw.write(text);
			fw.close();
			changed = false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mainFrame, "File " + filePath
					+ " can not write", "File can not write", 0);
		}
	}

	private void formatText() {
		if (!needFormat)
			return;
		if (txtText.getText().equals(text))
			return;
		text = txtText.getText();
		TokenRegister tr = new TokenRegister();
		Counter counter = new Counter(text);
		try {
			Document doc;
			if (counter.getException() == null) {
				int lastPos = txtText.getCaretPosition();
				// format txtText
				doc = txtText.getDocument();
				doc.remove(0, doc.getLength());
				for (Token t : counter.getTokenStream()) {
					doc.insertString(doc.getLength(), t.getText(), tr.get(
							t.getClass()).getColor());
				}
				int count = 0;
				for (int i = 0; i < Math.min(text.length(), lastPos); ++i) {
					if (text.charAt(i) == '\n')
						count++;
				}
				txtText.setCaretPosition(lastPos + count);
			}

			// format txtResult
			doc = txtResult.getDocument();

			doc.remove(0, doc.getLength());
			for (Class<? extends Token> tokenClass : counter.getTokenKinds()) {
				String line = "";
				line += tr.get(tokenClass).getName() + ": ";
				line += counter.getCount(tokenClass) + "\r\n";

				doc.insertString(doc.getLength(), line, tr.get(tokenClass)
						.getColor());

			}

			if (counter.getException() != null) {
				doc.insertString(doc.getLength(), counter.getException()
						.getMessage(), tr.get(null).getColor());

			}
			needFormat = false;
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e2) {
			e2.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
		XyzlexEditor editor = new XyzlexEditor();
	}

}
