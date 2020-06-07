package hr.fer.zemris.java.hw11.jnotepadapp.models;

import java.nio.file.Path;

import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;

/**
 * Interface that models the behavior of the {@link MultipleDocumentModel}. We
 * want to enable for {@link JNotepadPP} to have multiple opened documents.
 * 
 * @author ilovrencic
 *
 */
public interface MultipleDocumentModel {

	/**
	 * Method that's called for creation of new {@link SingleDocumentModel}
	 * 
	 * @return - instance of the {@link SingleDocumentModel}
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Method that's called for getting the current active
	 * {@link SingleDocumentModel}
	 * 
	 * @return - instance of the {@link SingleDocumentModel}
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Method that loads document from a given {@link Path}
	 * 
	 * @param path - path to the document we want to open.
	 * @return - instance of the {@link SingleDocumentModel}
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Method that saves document to some location on the computer.
	 * 
	 * @param model   - instance of the model we want to save.
	 * @param newPath - path to which we want to save it.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Method that closes the instance of the {@link SingleDocumentModel}
	 * 
	 * @param model - instance of the {@link SingleDocumentModel}
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Method that adds {@link MultipleDocumentListener}.
	 * 
	 * @param l - instance of the {@link MultipleDocumentListener}
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Method that removes {@link MultipleDocumentListener}.
	 * 
	 * @param l - instance of the {@link MultipleDocumentListener}
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Method that returns number of active {@link SingleDocumentModel}
	 * 
	 * @return - integer value of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Method that returns {@link SingleDocumentModel} with specific index.
	 * 
	 * @param index - of the document we want to retrieve
	 * @return - instance of the {@link SingleDocumentModel}
	 */
	SingleDocumentModel getDocument(int index);

}
