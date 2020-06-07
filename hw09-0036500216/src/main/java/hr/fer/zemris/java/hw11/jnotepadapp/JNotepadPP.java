package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadapp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadapp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadapp.models.SingleDocumentModel;

/**
 * Class that represents a main point of entry for {@link JNotepadPP}. This is
 * where the entire GUI and logic behind it is combined. Start this class to
 * start the {@link JNotepadPP}.
 * 
 * @author ilovrencic
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Generated serial UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of the {@link JTextArea} that user will write on.
	 */
	private JTextArea editor;

	/**
	 * The model which manages the files.
	 */
	private MultipleDocumentModel model;

	/**
	 * This is the listener which tracks the modified status of the current
	 * document.
	 */
	private SingleDocumentListener activeDocumentListener;

	/**
	 * Default constructor
	 */
	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);

		model = new DefaultMultipleDocumentModel();

		initGUI();
		initListeners();
	}

	/**
	 * Method that initializes GUI
	 */
	private void initGUI() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel(new BorderLayout());
		panel.add((JTabbedPane) model, BorderLayout.CENTER);
		getContentPane().add(panel);

		createActions();
		createMenus();
		createToolbars();

	}

	/**
	 * Method that initializes listeners
	 */
	private void initListeners() {
		activeDocumentListener = new SingleDocumentListener() {

			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				refreshActions(model.isModified(), saveDocumentAction);
			}

			public void documentFilePathUpdated(SingleDocumentModel model) {
				// no action for now
			}
		};

		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			public void documentRemoved(SingleDocumentModel model) {
				refreshGUI();
			}

			public void documentAdded(SingleDocumentModel model) {
				setFrameTitle();
			}

			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				handleDocumentChange(previousModel, currentModel);
			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				checkIfThereIsUnsavedFiles();
			}

		});
	}

	/**
	 * Method that checks whether there is some file that needs to be saved before
	 * exit.
	 */
	private void checkIfThereIsUnsavedFiles() {
		// todo
	}

	/**
	 * Method that handles document change. Method changes frame title.
	 * 
	 * @param previous - previous {@link SingleDocumentModel}
	 * @param current  - current instance of the {@link SingleDocumentModel}
	 */
	private void handleDocumentChange(SingleDocumentModel previous, SingleDocumentModel current) {
		setFrameTitle();

		if (previous != null) {
			previous.removeSingleDocumentListener(activeDocumentListener);
		}

		current.addSingleDocumentListener(activeDocumentListener);
	}

	/**
	 * Method that sets frame title of the {@link JNotepadPP}
	 */
	private void setFrameTitle() {
		if (model.getCurrentDocument() == null) {
			setTitle("JNotepad++");
		} else if (model.getCurrentDocument().getFilePath() == null) {
			setTitle("(unnamed)" + "- JNotepad++");
		} else {
			setTitle(model.getCurrentDocument().getFilePath().toAbsolutePath().toString() + "- JNotepad++");
		}
	}

	/**
	 * Method that refreshes GUI when the document is removed.
	 */
	private void refreshGUI() {
		setFrameTitle();
		refreshActions(model.getNumberOfDocuments() != 0);

		if (model.getNumberOfDocuments() != 0)
			return;
		refreshActions(model.getCurrentDocument().isModified(), saveDocumentAction);
	}

	/**
	 * Method that refreshes action. Some action might need to be disabled.
	 * 
	 * @param isEnabled - whether is action enabled
	 * @param actions   - actions we want to enable/disable
	 */
	private void refreshActions(boolean isEnabled, Action... actions) {
		for (Action action : actions) {
			action.setEnabled(isEnabled);
		}
	}

	/**
	 * Method that saves current active document. If there is no path to save
	 * document, we open file chooser.
	 * 
	 * @param document - instance of the {@link SingleDocumentModel}
	 */
	private void saveDocument(SingleDocumentModel document) {
		if (document.getFilePath() != null) {
			model.saveDocument(document, null);
		} else {
			saveDocumentToNewLocation(document);
		}
	}

	/**
	 * This method is called when there is no path to where to save the document.
	 * Here we are opening {@link JFileChooser} to find path.
	 * 
	 * @param document
	 */
	private void saveDocumentToNewLocation(SingleDocumentModel document) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "Nothing was saved!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		Path path = jfc.getSelectedFile().toPath();
		model.saveDocument(document, path);
	}

	/**
	 * Action when trigger creates new document
	 */
	private Action newDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	/**
	 * Action when trigger opens a document from {@link JFileChooser}
	 */
	private Action openDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						"Datoteka " + fileName.getAbsolutePath() + " ne postoji!", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			model.loadDocument(filePath);
		}
	};

	/**
	 * Action that closes the active document
	 */
	private Action closeDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getCurrentDocument().isModified()) {
				int decision = JOptionPane.showConfirmDialog(JNotepadPP.this, "Spremiti datoteku?",
						"Datoteka nije spremljena. Želite ju spremiti?", JOptionPane.INFORMATION_MESSAGE);
				if (decision == JOptionPane.YES_OPTION) {
					saveDocument(model.getCurrentDocument());
				}
			}

			model.closeDocument(model.getCurrentDocument());
		}
	};

	/**
	 * Action that opens a {@link JFileChooser} to save a file.
	 */
	private Action saveAsDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = model.getCurrentDocument();
			saveDocumentToNewLocation(document);
			setFrameTitle();
		}
	};

	/**
	 * Action that saves document to already familiar path. If there is no path,
	 * then we open {@link JFileChooser}.
	 */
	private Action saveDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = model.getCurrentDocument();
			saveDocument(document);
		}

	};

	private Action deleteSelectedPartAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};

	private Action toggleCaseAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};

	/**
	 * Action that exits the application.
	 */
	private Action exitAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	/**
	 * Method that initializes actions.
	 */
	private void createActions() {
		newDocumentAction.putValue(Action.NAME, "New");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create file.");

		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

		closeDocumentAction.putValue(Action.NAME, "Close");
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to close the tab.");

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file.");

		saveAsDocumentAction.putValue(Action.NAME, "Save As");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

		deleteSelectedPartAction.putValue(Action.NAME, "Delete selected text");
		deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPartAction.putValue(Action.SHORT_DESCRIPTION, "Used to delete the selected part of text.");

		toggleCaseAction.putValue(Action.NAME, "Toggle case");
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to toggle character case in selected part of text or in entire document.");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");
	}

	/**
	 * Method that creates menus with acitons
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Method that creates toolbar and then adds buttons with actions
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Main function that starts the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
