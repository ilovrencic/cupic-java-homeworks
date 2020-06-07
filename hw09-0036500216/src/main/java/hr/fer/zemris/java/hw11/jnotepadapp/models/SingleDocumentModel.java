package hr.fer.zemris.java.hw11.jnotepadapp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;

/**
 * Interface that represents a single document state. This is the mode when
 * {@link JNotepadPP} is working with only one text document.
 * 
 * @author ilovrencic
 *
 */
public interface SingleDocumentModel {

	/**
	 * Method that returns {@link JTextArea} component.
	 * 
	 * @return - instance of the {@link JTextArea}
	 */
	JTextArea getTextComponent();

	/**
	 * Method that returns the file path.
	 * 
	 * @return - instance of the {@link Path}
	 */
	Path getFilePath();

	/**
	 * Method that sets new file {@link Path} for the document.
	 * 
	 * @param path - path of the document.
	 */
	void setFilePath(Path path);

	/**
	 * Method that returns whether the document is modified.
	 * 
	 * @return - true if document is modified, otherwise false
	 */
	boolean isModified();

	/**
	 * Method that sets whether the document is modified.
	 * 
	 * @param modified - boolean that sets whether the document is modified
	 */
	void setModified(boolean modified);

	/**
	 * Method that adds {@link SingleDocumentListener} into
	 * {@link SingleDocumentModel}
	 * 
	 * @param l - instance of the {@link SingleDocumentListener}
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Method that removes {@link SingleDocumentListener} from
	 * {@link SingleDocumentModel}.
	 * 
	 * @param l - instance of the {@link SingleDocumentListener}
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
