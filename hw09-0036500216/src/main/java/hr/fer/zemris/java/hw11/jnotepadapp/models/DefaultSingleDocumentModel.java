package hr.fer.zemris.java.hw11.jnotepadapp.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;

/**
 * Class implementation of {@link SingleDocumentModel}. This represents a model
 * that will handle the state of the single document in {@link JNotepadPP}.
 * 
 * @author ilovrencic
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Instance of {@link JTextArea}.
	 */
	private JTextArea editor;

	/**
	 * File {@link Path} for this specific document.s
	 */
	private Path filePath;

	/**
	 * Flag that signals whether is document modified.
	 */
	private boolean isFileModifed;

	/**
	 * List of {@link SingleDocumentListener}
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Default constructor
	 * 
	 * @param documentText - text that is in document
	 * @param file         - path to the file
	 */
	public DefaultSingleDocumentModel(String documentText, Path file) {
		this.filePath = file;
		this.editor = new JTextArea(documentText);
		this.listeners = new ArrayList<SingleDocumentListener>();

		// here we are adding document listener to check when user changes something to
		// the file
		editor.getDocument().addDocumentListener(new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	public JTextArea getTextComponent() {
		return editor;
	}

	public Path getFilePath() {
		return filePath;
	}

	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.filePath = path;
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

	public boolean isModified() {
		return isFileModifed;
	}

	public void setModified(boolean modified) {
		this.isFileModifed = modified;
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}

	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l);
		this.listeners.add(l);
	}

	public void removeSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l);
		this.listeners.remove(l);
	}
}
